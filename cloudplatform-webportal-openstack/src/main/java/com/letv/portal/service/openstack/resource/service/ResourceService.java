package com.letv.portal.service.openstack.resource.service;

import com.letv.portal.service.openstack.exception.OpenStackException;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public interface ResourceService {

    void attachVmToSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmId, String subnetId) throws OpenStackException;

    void detachVmFromSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmId, String subnetId) throws OpenStackException;

}
