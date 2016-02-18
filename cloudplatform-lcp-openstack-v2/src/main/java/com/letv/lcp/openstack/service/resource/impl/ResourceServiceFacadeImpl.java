package com.letv.lcp.openstack.service.resource.impl;

import java.util.List;

import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.paging.impl.Page;
import com.letv.common.session.SessionServiceImpl;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.compute.VMResource;
import com.letv.lcp.openstack.model.storage.VolumeSnapshotResource;
import com.letv.lcp.openstack.model.user.OpenStackUser;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.resource.IResourceService;
import com.letv.lcp.openstack.service.resource.IResourceServiceFacade;
import com.letv.lcp.openstack.service.session.impl.OpenStackSessionImpl;
import com.letv.lcp.openstack.util.Ref;
import com.letv.lcp.openstack.util.Util;
import com.letv.lcp.openstack.util.tuple.Tuple2;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
@Service
public class ResourceServiceFacadeImpl implements IResourceServiceFacade {

    @Autowired
    private IApiService apiService;

    @Autowired
    private IResourceService resourceService;

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
    public void attachVmsToSubnet(String region, String vmIds, String subnetId, Ref<Tuple2<List<String>, String>> vmNamesAndSubnetName) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
        resourceService.attachVmsToSubnet(novaApi, neutronApi, region, vmIds, subnetId, vmNamesAndSubnetName);
    }

    @Override
    public void detachVmsFromSubnet(String region, String vmIds, String subnetId, Ref<Tuple2<List<String>, String>> vmNamesAndSubnetName) throws OpenStackException {
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
        long userVoUserId = openStackUser.getTenantUserId();
        String tenantId = openStackUser.getOpenStackTenantId();

        NovaApi novaApi = getNovaApi();
        return resourceService.createKeyPair(novaApi, userVoUserId, tenantId, region, name);
    }

    @Override
    public void checkCreateKeyPair(String region, String name) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getTenantUserId();
        String tenantId = openStackUser.getOpenStackTenantId();

        NovaApi novaApi = getNovaApi();
        resourceService.checkCreateKeyPair(novaApi, userVoUserId, tenantId, region, name);
    }

    @Override
    public void deleteKeyPair(String region, String name) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getTenantUserId();
        String tenantId = openStackUser.getOpenStackTenantId();

        NovaApi novaApi = getNovaApi();
        resourceService.deleteKeyPair(novaApi, userVoUserId, tenantId, region, name);
    }

    @Override
    public Page listVm(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getTenantUserId();

        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();
//        CinderApi cinderApi = getCinderApi();
        return resourceService.listVm(novaApi, neutronApi, userVoUserId, region, name, currentPage, recordsPerPage);
    }

    @Override
    public void bindFloatingIp(String region, String vmId, String floatingIpId) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();

        resourceService.bindFloatingIp(novaApi, neutronApi, region, vmId, floatingIpId, openStackUser.getTenantEmail(), openStackUser.getUserName());
    }

    @Override
    public void renameVm(String region, String vmId, String name) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getTenantUserId();

        NovaApi novaApi = getNovaApi();

        resourceService.renameVm(novaApi, userVoUserId, region, vmId, name);
    }

    @Override
    public void deleteVolume(String region, String volumeId) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getTenantUserId();

        CinderApi cinderApi = getCinderApi();

        resourceService.deleteVolume(cinderApi, userVoUserId, region, volumeId);
    }

    @Override
    public void editRouter(String region, String routerId, String name, boolean enablePublicNetworkGateway, String publicNetworkId) throws OpenStackException {
        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();

        resourceService.editRouter(novaApi, neutronApi, region, routerId, name, enablePublicNetworkGateway, publicNetworkId);
    }

    @Override
    public void separateSubnetFromRouter(String region, String routerId, String subnetId) throws OpenStackException {
        NeutronApi neutronApi = getNeutronApi();

        resourceService.separateSubnetFromRouter(neutronApi, region, routerId, subnetId);
    }

    @Override
    public VMResource getVm(String region, String vmId) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getTenantUserId();

        NovaApi novaApi = getNovaApi();
        NeutronApi neutronApi = getNeutronApi();

        return resourceService.getVm(novaApi, neutronApi, userVoUserId, region, vmId);
    }

    @Override
    public Page listPrivateSubnet(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException {
        NeutronApi neutronApi = getNeutronApi();

        return resourceService.listPrivateSubnet(neutronApi, region, name, currentPage, recordsPerPage);
    }

    @Override
    public VolumeSnapshotResource getVolumeSnapshot(String region, String volumeSnapshotId) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getTenantUserId();

        CinderApi cinderApi = getCinderApi();

        return resourceService.getVolumeSnapshot(cinderApi, userVoUserId, region, volumeSnapshotId);
    }

    @Override
    public Page listVolume(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException {
        OpenStackUser openStackUser = getOpenStackUser();
        long userVoUserId = openStackUser.getTenantUserId();

        CinderApi cinderApi = getCinderApi();

        return resourceService.listVolume(cinderApi, userVoUserId, region, name, currentPage, recordsPerPage);
    }
}
