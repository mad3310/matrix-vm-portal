package com.letv.portal.service.openstack.cronjobs.impl.cache;

import com.google.common.cache.*;
import com.letv.common.exception.MatrixException;
import com.letv.portal.model.UserVo;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.jclouds.service.impl.ApiServiceImpl;
import com.letv.portal.service.openstack.util.Contants;

import org.jclouds.ContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhouxianguang on 2015/10/9.
 */
public class SyncLocalApiCache implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(SyncLocalApiCache.class);

    private Cache<SyncLocalApiCacheKey, Closeable> apiCache;

    private LoadingCache<Long, OpenStackUserInfo> userIdToInfo;

    public SyncLocalApiCache() {
        this.userIdToInfo = CacheBuilder.newBuilder().build(new CacheLoader<Long, OpenStackUserInfo>() {
            @Override
            public OpenStackUserInfo load(Long userId) throws Exception {
                UserVo userVo = OpenStackServiceImpl.getOpenStackServiceGroup().getUserService().getUcUserById(userId);
                String email = userVo.getEmail();
                String openStackUserId = OpenStackServiceImpl.createOpenStackUserId(email);
                String password = OpenStackServiceImpl.getOpenStackServiceGroup().getPasswordService().userIdToPassword(openStackUserId);
                return new OpenStackUserInfo(openStackUserId, password);
            }
        });
        this.apiCache = CacheBuilder.newBuilder().removalListener(new RemovalListener<SyncLocalApiCacheKey, Closeable>() {
            @Override
            public void onRemoval(RemovalNotification<SyncLocalApiCacheKey, Closeable> notification) {
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
        userIdToInfo.invalidateAll();
    }

    @SuppressWarnings("unchecked")
	public <T extends Closeable> T getApi(final long userId, final Class<T> apiType) throws OpenStackException {
        try {
            return (T) apiCache.get(new SyncLocalApiCacheKey(userId, apiType), new Callable<Closeable>() {
                @Override
                public Closeable call() throws Exception {
                    OpenStackUserInfo userInfo = userIdToInfo.get(userId);
                    return ContextBuilder
                            .newBuilder(ApiServiceImpl.apiToProvider.get(apiType))
                            .endpoint(OpenStackServiceImpl.getOpenStackConf().getPublicEndpoint())
                            .credentials(
                                    OpenStackServiceImpl.createCredentialsIdentity(userInfo.getUserId()),
                                    userInfo.getPassword()).modules(Contants.jcloudsContextBuilderModules)
                            .buildApi(apiType);
                }
            });
        } catch (ExecutionException e) {
            throw new OpenStackException("后台错误", e);
        }
    }

    private static class OpenStackUserInfo {
        private String userId;
        private String password;

        public OpenStackUserInfo(String userId, String password) {
            this.userId = userId;
            this.password = password;
        }

        public String getUserId() {
            return userId;
        }

        public String getPassword() {
            return password;
        }
    }
}
