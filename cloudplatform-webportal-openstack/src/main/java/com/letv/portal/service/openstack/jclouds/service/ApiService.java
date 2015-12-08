package com.letv.portal.service.openstack.jclouds.service;

import com.letv.portal.service.openstack.exception.OpenStackException;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import java.security.NoSuchAlgorithmException;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface ApiService {
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
