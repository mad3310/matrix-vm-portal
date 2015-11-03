package com.letv.portal.service.openstack.resource.service.impl;

import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackSessionImpl;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.service.ResourceService;
import com.letv.portal.service.openstack.resource.service.ResourceServiceFacade;
import com.letv.portal.service.openstack.util.Util;

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

    @Autowired
    private SessionServiceImpl sessionService;

    private NovaApi getNovaApi() {
        return apiService.getNovaApi();
    }

    private NeutronApi getNeutronApi() {
        return apiService.getNeutronApi();
    }

    @SuppressWarnings("unused")
	private CinderApi getCinderApi() {
        return apiService.getCinderApi();
    }

    @SuppressWarnings("unused")
	private GlanceApi getGlanceApi() {
        return apiService.getGlanceApi();
    }

    private OpenStackUser getOpenStackUser() {
        OpenStackSessionImpl openStackSession = Util.session(sessionService);
        OpenStackUser openStackUser = openStackSession.getOpenStackUser();
        return openStackUser;
    }

    @Override
    public void attachVmsToSubnet(String region, String vmIds, String subnetId) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        resourceService.attachVmsToSubnet(novaApi, neutronApi, region, vmIds, subnetId);
    }

    @Override
    public void detachVmsFromSubnet(String region, String vmIds, String subnetId) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        resourceService.detachVmsFromSubnet(novaApi, neutronApi, region, vmIds, subnetId);
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

    @Override
    public String createKeyPair(String region, String name) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getUserVoUserId();
        String tenantId = openStackUser.getTenantId();

        NovaApi novaApi = getNovaApi();
        return resourceService.createKeyPair(novaApi, userVoUserId, tenantId, region, name);
    }

    @Override
    public void checkCreateKeyPair(String region, String name) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getUserVoUserId();
        String tenantId = openStackUser.getTenantId();

        NovaApi novaApi = getNovaApi();
        resourceService.checkCreateKeyPair(novaApi, userVoUserId, tenantId, region, name);
    }

    @Override
    public void deleteKeyPair(String region, String name) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getUserVoUserId();
        String tenantId = openStackUser.getTenantId();

        NovaApi novaApi = getNovaApi();
        resourceService.deleteKeyPair(novaApi, userVoUserId, tenantId, region, name);
    }

}
