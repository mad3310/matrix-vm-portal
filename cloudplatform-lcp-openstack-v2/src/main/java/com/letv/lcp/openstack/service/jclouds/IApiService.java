package com.letv.lcp.openstack.service.jclouds;

import java.security.NoSuchAlgorithmException;

import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.user.OpenStackUserInfo;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface IApiService {
    NovaApi getNovaApi();

    NovaApi getNovaApi(Long userId, String sessionId);

    NeutronApi getNeutronApi();

    NeutronApi getNeutronApi(Long userId, String sessionId);

    CinderApi getCinderApi();

    CinderApi getCinderApi(Long userId, String sessionId);

    GlanceApi getGlanceApi();

    GlanceApi getGlanceApi(Long userId, String sessionId);

    void clearCache();

    void clearCache(Long userId, String sessionId);

    void loadAllApiForCurrentSession(OpenStackUserInfo userInfo);

    void loadAllApiForRandomSessionFromBackend(long userId, String randomSessionId) throws NoSuchAlgorithmException, OpenStackException;
}
