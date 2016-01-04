package com.letv.lcp.openstack.service.jclouds.impl;

import java.io.Closeable;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jclouds.ContextBuilder;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableMap;
import com.letv.common.exception.MatrixException;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.lcp.openstack.constants.Constants;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.cache.ApiCacheKey;
import com.letv.lcp.openstack.model.user.OpenStackTenant;
import com.letv.lcp.openstack.model.user.OpenStackUser;
import com.letv.lcp.openstack.model.user.OpenStackUserInfo;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.erroremail.IErrorEmailService;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.password.IPasswordService;
import com.letv.lcp.openstack.service.session.impl.OpenStackSessionImpl;
import com.letv.lcp.openstack.util.ThreadUtil;
import com.letv.lcp.openstack.util.function.Function0;
import com.letv.portal.model.common.UserVo;
import com.letv.portal.service.common.IUserService;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
@Service
public class ApiServiceImpl implements IApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPasswordService passwordService;

    @Autowired
    private IErrorEmailService errorEmailService;

    @Autowired
    private IOpenStackService openStackService;

    private Cache<ApiCacheKey, Closeable> apiCache;

    public static final Map<Class<? extends Closeable>, String> apiToProvider = ImmutableMap.<Class<? extends Closeable>, String>builder()
            .put(NovaApi.class, "openstack-nova")
            .put(NeutronApi.class, "openstack-neutron")
            .put(CinderApi.class, "openstack-cinder")
            .put(GlanceApi.class, "openstack-glance").build();

    @PostConstruct
    private void init() {
        apiCache = CacheBuilder.newBuilder().removalListener(RemovalListeners.asynchronous(new RemovalListener<ApiCacheKey, Closeable>() {
            @Override
            public void onRemoval(RemovalNotification<ApiCacheKey, Closeable> notification) {
                try {
                    notification.getValue().close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    errorEmailService.sendExceptionEmail(e, "close api", null, e.getMessage());
                    throw new MatrixException("后台错误", e);
                }
            }
        }, Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))).expireAfterAccess(600, TimeUnit.MINUTES).maximumSize(50).build();
    }

    @PreDestroy
    public void close() {
        apiCache.invalidateAll();
    }

    private OpenStackUserInfo getUserInfo(Long userId, String sessionId) throws NoSuchAlgorithmException {
        final OpenStackTenant openStackTenant;

        Session session = sessionService.getSession();
        OpenStackSessionImpl openStackSession = null;
        if (session != null) {
            openStackSession = (OpenStackSessionImpl) session.getOpenStackSession();
        }

        if (openStackSession != null) {
            userId = session.getUserId();

            OpenStackUser openStackUser = openStackSession.getOpenStackUser();
            openStackTenant = openStackUser.tenant;

            sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        } else {
            UserVo user = userService.getUcUserById(userId);

            openStackTenant=new OpenStackTenant(userId, user.getEmail());

            if (sessionId == null) {
                sessionId = "";
            }
        }

        return new OpenStackUserInfo(openStackTenant, sessionId);
    }

    @SuppressWarnings("unchecked")
	private <T extends Closeable> T getApi(final Class<T> apiType, final OpenStackUserInfo userInfo) throws ExecutionException {
        return (T) apiCache.get(new ApiCacheKey(userInfo.tenant.userId, userInfo.sessionId, apiType), new Callable<Closeable>() {
            @Override
            public Closeable call() throws Exception {
                return ContextBuilder
                        .newBuilder(apiToProvider.get(apiType))
                        .endpoint(OpenStackServiceImpl.getOpenStackConf().getPublicEndpoint())
                        .credentials(
                                userInfo.tenant.jcloudsCredentialsIdentity,
                                userInfo.tenant.password).modules(Constants.jcloudsContextBuilderModules)
                        .buildApi(apiType);
            }
        });
    }

    private <T extends Closeable> T getApi(final Class<T> apiType, Long userId, String sessionId) {
        try {
            final OpenStackUserInfo userInfo = getUserInfo(userId, sessionId);

            return getApi(apiType, userInfo);
        } catch (ExecutionException e) {
            throw new MatrixException("后台错误", e);
        } catch (NoSuchAlgorithmException e) {
            throw new MatrixException("后台错误", e);
        }
    }

    private <T extends Closeable> T getApi(final Class<T> apiType) {
        return getApi(apiType, null, null);
    }

    @Override
    public NovaApi getNovaApi() {
        return getApi(NovaApi.class);
    }

    @Override
    public NovaApi getNovaApi(Long userId, String sessionId) {
        return getApi(NovaApi.class, userId, sessionId);
    }

    @Override
    public NeutronApi getNeutronApi() {
        return getApi(NeutronApi.class);
    }

    @Override
    public NeutronApi getNeutronApi(Long userId, String sessionId) {
        return getApi(NeutronApi.class, userId, sessionId);
    }

    @Override
    public CinderApi getCinderApi() {
        return getApi(CinderApi.class);
    }

    @Override
    public CinderApi getCinderApi(Long userId, String sessionId) {
        return getApi(CinderApi.class, userId, sessionId);
    }

    @Override
    public GlanceApi getGlanceApi() {
        return getApi(GlanceApi.class);
    }

    @Override
    public GlanceApi getGlanceApi(Long userId, String sessionId) {
        return getApi(GlanceApi.class, userId, sessionId);
    }

    @Override
    public void clearCache() {
        clearCache(null, null);
    }

    @Override
    public void clearCache(Long userId, String sessionId) {
        try {
            OpenStackUserInfo userInfo = getUserInfo(userId, sessionId);
            List<ApiCacheKey> keys = new LinkedList<ApiCacheKey>();
            for (Class<? extends Closeable> apiType : apiToProvider.keySet()) {
                keys.add(new ApiCacheKey(userInfo.tenant.userId, userInfo.sessionId, apiType));
            }
            apiCache.invalidateAll(keys);
        } catch (NoSuchAlgorithmException e) {
            throw new MatrixException("后台错误", e);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public void loadAllApiForCurrentSession(final OpenStackUserInfo userInfo) {
        ThreadUtil.concurrentRun(new Function0<Void>() {
            @Override
            public Void apply() {
                try {
                    getApi(CinderApi.class, userInfo);
                } catch (ExecutionException e) {
                    throw new MatrixException("后台错误", e);
                }
                return null;
            }
        }, new Function0<Void>() {
            @Override
            public Void apply() {
                try {
                    getApi(NovaApi.class, userInfo);
                } catch (ExecutionException e) {
                    throw new MatrixException("后台错误", e);
                }
                return null;
            }
        }, new Function0<Void>() {
            @Override
            public Void apply() {
                try {
                    getApi(GlanceApi.class, userInfo);
                } catch (ExecutionException e) {
                    throw new MatrixException("后台错误", e);
                }
                return null;
            }
        }, new Function0<Void>() {
            @Override
            public Void apply() {
                try {
                    getApi(NeutronApi.class, userInfo);
                } catch (ExecutionException e) {
                    throw new MatrixException("后台错误", e);
                }
                return null;
            }
        });
    }

    @Override
    public void loadAllApiForRandomSessionFromBackend(long userId, String randomSessionId) throws NoSuchAlgorithmException, OpenStackException {
        UserVo userVo = userService.getUcUserById(userId);
        final String email = userVo.getEmail();

//        openStackService.registerAndInitUserIfNotExists(userId, userVo.getUsername(), email, openStackUserPassword);

        loadAllApiForCurrentSession(new OpenStackUserInfo(new OpenStackTenant(userId,email),randomSessionId));
    }

}
