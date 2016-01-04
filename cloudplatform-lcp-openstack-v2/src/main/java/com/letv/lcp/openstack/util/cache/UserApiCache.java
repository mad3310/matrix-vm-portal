package com.letv.lcp.openstack.util.cache;

import java.io.Closeable;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.jclouds.ContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.letv.common.exception.MatrixException;
import com.letv.lcp.openstack.constants.Constants;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.user.OpenStackTenant;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.jclouds.impl.ApiServiceImpl;
import com.letv.lcp.openstack.util.ThreadUtil;
import com.letv.lcp.openstack.util.function.Function0;
import com.letv.portal.model.common.UserVo;

/**
 * Created by zhouxianguang on 2015/10/9.
 */
public class UserApiCache implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(UserApiCache.class);

    private Cache<Class<? extends Closeable>, Closeable> apiCache;

    private OpenStackTenant tenant;

    public UserApiCache(OpenStackTenant tenant) {
        this.tenant = tenant;
        init();
    }

    public UserApiCache(long userId) throws NoSuchAlgorithmException {
        UserVo userVo = OpenStackServiceImpl.getOpenStackServiceGroup().getUserService().getUcUserById(userId);
        String email = userVo.getEmail();
        this.tenant = new OpenStackTenant(userId, email);
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
                                    tenant.jcloudsCredentialsIdentity,
                                    tenant.password).modules(Constants.jcloudsContextBuilderModules)
                            .buildApi(apiType);
                }
            });
        } catch (ExecutionException e) {
            throw new OpenStackException("后台错误", e);
        }
    }

    public void loadApis(Class<? extends Closeable>... apiTypes) {
        for (final Class<? extends Closeable> apiType : apiTypes) {
            ThreadUtil.asyncExec(new Function0<Void>() {
                @Override
                public Void apply() throws Exception {
                    UserApiCache.this.getApi(apiType);
                    return null;
                }
            });
        }
    }

}
