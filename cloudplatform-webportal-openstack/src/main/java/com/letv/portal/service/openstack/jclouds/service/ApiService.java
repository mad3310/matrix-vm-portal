package com.letv.portal.service.openstack.jclouds.service;

import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface ApiService {
    NovaApi getNovaApi();

    NeutronApi getNeutronApi();

    CinderApi getCinderApi();

    GlanceApi getGlanceApi();
}
