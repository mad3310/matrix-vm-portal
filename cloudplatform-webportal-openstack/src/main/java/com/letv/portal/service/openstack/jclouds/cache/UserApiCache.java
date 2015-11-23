package com.letv.portal.service.openstack.jclouds.cache;

import com.google.common.cache.*;
import com.letv.common.exception.MatrixException;
import com.letv.portal.model.UserVo;
import com.letv.portal.service.openstack.cronjobs.impl.cache.SyncLocalApiCacheKey;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.jclouds.service.impl.ApiServiceImpl;
import com.letv.portal.service.openstack.util.ThreadUtil;
import com.letv.portal.service.openstack.util.constants.Constants;
import com.letv.portal.service.openstack.util.function.Function;
import org.jclouds.ContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhouxianguang on 2015/10/9.
 */
public class UserApiCache implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(UserApiCache.class);

    private Cache<Class<? extends Closeable>, Closeable> apiCache;

    private OpenStackUserInfo openStackUserInfo;

    public UserApiCache(OpenStackUserInfo openStackUserInfo) {
        this.openStackUserInfo = openStackUserInfo;
        init();
    }

    public UserApiCache(String email) throws NoSuchAlgorithmException {
        String openStackUserId = OpenStackServiceImpl.createOpenStackUserId(email);
        String password = OpenStackServiceImpl.getOpenStackServiceGroup().getPasswordService().userIdToPassword(openStackUserId);
        this.openStackUserInfo = new OpenStackUserInfo(openStackUserId, password);
        init();
    }

    public UserApiCache(long userId) throws NoSuchAlgorithmException {
        UserVo userVo = OpenStackServiceImpl.getOpenStackServiceGroup().getUserService().getUcUserById(userId);
        String email = userVo.getEmail();
        String openStackUserId = OpenStackServiceImpl.createOpenStackUserId(email);
        String password = OpenStackServiceImpl.getOpenStackServiceGroup().getPasswordService().userIdToPassword(openStackUserId);
        this.openStackUserInfo = new OpenStackUserInfo(openStackUserId, password);
        init();
    }

    private void init() {
        this.apiCache = CacheBuilder.newBuilder().removalListener(new RemovalListener<Class<? extends Closeable>, Closeable>() {
            @Override
            public void onRemoval(RemovalNotification<Class<? extends Closeable>, Closeable> notification) {
                try {
                    notification.getValue().close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    OpenStackServiceImpl.getOpenStackServiceGroup().getErrorEmailService().sendExceptionEmail(e, "close api", null, e.getMessage());
                    throw new MatrixException("后台错误", e);
                }
            }
        }).build();
    }

    public void close() {
        apiCache.invalidateAll();
    }

    @SuppressWarnings("unchecked")
    public <T extends Closeable> T getApi(final Class<T> apiType) throws OpenStackException {
        try {
            return (T) apiCache.get(apiType, new Callable<Closeable>() {
                @Override
                public Closeable call() throws Exception {
                    return ContextBuilder
                            .newBuilder(ApiServiceImpl.apiToProvider.get(apiType))
                            .endpoint(OpenStackServiceImpl.getOpenStackConf().getPublicEndpoint())
                            .credentials(
                                    OpenStackServiceImpl.createCredentialsIdentity(openStackUserInfo.getUserId()),
                                    openStackUserInfo.getPassword()).modules(Constants.jcloudsContextBuilderModules)
                            .buildApi(apiType);
                }
            });
        } catch (ExecutionException e) {
            throw new OpenStackException("后台错误", e);
        }
    }

    public void loadApis(Class<? extends Closeable>... apiTypes) {
        for (final Class<? extends Closeable> apiType : apiTypes) {
            ThreadUtil.asyncExec(new Function<Void>() {
                @Override
                public Void apply() throws Exception {
                    UserApiCache.this.getApi(apiType);
                    return null;
                }
            });
        }
    }

}
