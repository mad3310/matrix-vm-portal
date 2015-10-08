package com.letv.portal.service.openstack.jclouds.service.impl;

import com.google.common.cache.*;
import com.letv.common.exception.MatrixException;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.erroremail.ErrorEmailService;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.impl.OpenStackSessionImpl;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.password.PasswordService;
import com.letv.portal.service.openstack.util.Contants;
import org.jclouds.ContextBuilder;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
@Service
public class ApiServiceImpl implements ApiService, ServletContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private ErrorEmailService errorEmailService;

    private Cache<ApiCacheKey, Closeable> apiCache;

    private void init(ServletContext servletContext) {
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
        }, Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))).expireAfterAccess(600, TimeUnit.MINUTES).build();
    }

    @PreDestroy
    public void close() {
        apiCache.invalidateAll();
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        init(servletContext);
    }

    private <T extends Closeable> T getApi(final Class<T> apiType, final String provider) {
        try {
            Session session = sessionService.getSession();
            final long userId = session.getUserId();

            OpenStackSessionImpl openStackSession = (OpenStackSessionImpl) session.getOpenStackSession();
            OpenStackUser openStackUser = openStackSession.getOpenStackUser();
            final String email = openStackUser.getEmail();
            final String password = openStackUser.getPassword();

            final String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

            return (T) apiCache.get(new ApiCacheKey(userId, sessionId, apiType), new Callable<Closeable>() {
                @Override
                public Closeable call() throws Exception {
                    return ContextBuilder
                            .newBuilder(provider)
                            .endpoint(OpenStackServiceImpl.getOpenStackConf().getPublicEndpoint())
                            .credentials(
                                    OpenStackServiceImpl.createCredentialsIdentity(email),
                                    password).modules(Contants.jcloudsContextBuilderModules)
                            .buildApi(apiType);
                }
            });
        } catch (ExecutionException e) {
            throw new MatrixException("后台错误", e);
        }
    }

    @Override
    public NovaApi getNovaApi() {
        return getApi(NovaApi.class, "openstack-nova");
    }

    @Override
    public NeutronApi getNeutronApi() {
        return getApi(NeutronApi.class, "openstack-neutron");
    }

    @Override
    public CinderApi getCinderApi() {
        return getApi(CinderApi.class, "openstack-cinder");
    }

    @Override
    public GlanceApi getGlanceApi() {
        return getApi(GlanceApi.class, "openstack-glance");
    }

}
