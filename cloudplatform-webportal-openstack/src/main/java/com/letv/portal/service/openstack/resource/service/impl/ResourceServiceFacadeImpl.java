package com.letv.portal.service.openstack.resource.service.impl;

import com.letv.common.paging.impl.Page;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackSessionImpl;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.service.ResourceService;
import com.letv.portal.service.openstack.resource.service.ResourceServiceFacade;
import com.letv.portal.service.openstack.util.Util;

import com.letv.portal.service.openstack.util.tuple.Tuple2;
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
    public void attachVmsToSubnet(String region, String vmIds, String subnetId, Tuple2<List<String>, String> vmNamesAndSubnetName) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        resourceService.attachVmsToSubnet(novaApi, neutronApi, region, vmIds, subnetId, vmNamesAndSubnetName);
    }

    @Override
    public void detachVmsFromSubnet(String region, String vmIds, String subnetId, Tuple2<List<String>, String> vmNamesAndSubnetName) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        resourceService.detachVmsFromSubnet(novaApi, neutronApi, region, vmIds, subnetId, vmNamesAndSubnetName);
    }

    @Override
    public List<VMResource> listVmNotInAnyNetwork(String region) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        return resourceService.listVmNotInAnyNetwork(novaApi, neutronApi, region);
    }

    @Override
    public List<VMResource> listVmCouldAttachSubnet(String region, String subnetId) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        return resourceService.listVmCouldAttachSubnet(novaApi, neutronApi, region, subnetId);
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

    @Override
    public Page listVm(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getUserVoUserId();

        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        CinderApi cinderApi = getCinderApi();
        return resourceService.listVm(novaApi, neutronApi, cinderApi, userVoUserId, region, name, currentPage, recordsPerPage);
    }

    @Override
    public void bindFloatingIp(String region, String vmId, String floatingIpId) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();

        resourceService.bindFloatingIp(novaApi, neutronApi, region, vmId, floatingIpId, openStackUser.getEmail(), openStackUser.getUserName());
    }

    @Override
    public void renameVm(String region, String vmId, String name) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getUserVoUserId();

        NovaApi novaApi = getNovaApi();

        resourceService.renameVm(novaApi, userVoUserId, region, vmId, name);
    }
}
