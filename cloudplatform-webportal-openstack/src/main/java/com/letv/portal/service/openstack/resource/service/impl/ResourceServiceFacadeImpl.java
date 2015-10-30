package com.letv.portal.service.openstack.resource.service.impl;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.service.ResourceService;
import com.letv.portal.service.openstack.resource.service.ResourceServiceFacade;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
@Service
public class ResourceServiceFacadeImpl implements ResourceServiceFacade {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ResourceService resourceService;

    private NovaApi getNovaApi() {
        return apiService.getNovaApi();
    }

    private NeutronApi getNeutronApi() {
        return apiService.getNeutronApi();
    }

    private CinderApi getCinderApi() {
        return apiService.getCinderApi();
    }

    private GlanceApi getGlanceApi() {
        return apiService.getGlanceApi();
    }

    @Override
    public void attachVmToSubnet(String region, String vmId, String subnetId) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        resourceService.attachVmToSubnet(novaApi, neutronApi, region, vmId, subnetId);
    }

    @Override
    public void detachVmFromSubnet(String region, String vmId, String subnetId) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        resourceService.detachVmFromSubnet(novaApi, neutronApi, region, vmId, subnetId);
    }

    @Override
    public List<VMResource> listVmNotInAnyNetwork(String region) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        return resourceService.listVmNotInAnyNetwork(novaApi, neutronApi, region);
    }

    @Override
    public List<VMResource> listVmAttachedSubnet(String region, String subnetId) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        return resourceService.listVmAttachedSubnet(novaApi, neutronApi, region, subnetId);
    }

}
