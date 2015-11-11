package com.letv.portal.service.openstack.local.service;

import com.letv.portal.service.openstack.exception.OpenStackException;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;

/**
 * Created by zhouxianguang on 2015/11/11.
 */
public interface LocalCommonQuotaSerivce {
    void updateLocalCommonQuotaService(final long userVoUserId, final String osTenantId, NovaApi novaApi, NeutronApi neutronApi, CinderApi cinderApi) throws OpenStackException;
}
