package com.letv.lcp.openstack.service.resource;

import java.io.Closeable;
import java.util.List;

import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import com.letv.common.paging.impl.Page;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.RegionNotFoundException;
import com.letv.lcp.openstack.model.compute.VMResource;
import com.letv.lcp.openstack.model.storage.VolumeSnapshotResource;
import com.letv.lcp.openstack.util.Ref;
import com.letv.lcp.openstack.util.tuple.Tuple2;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public interface IResourceService {

    void checkRegion(String region, Closeable... apis) throws RegionNotFoundException;

    void attachVmsToSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmIds, String subnetId, Ref<Tuple2<List<String>, String>> vmNamesAndSubnetName) throws OpenStackException;

    void detachVmsFromSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmIds, String subnetId, Ref<Tuple2<List<String>, String>> vmNamesAndSubnetName) throws OpenStackException;

    List<VMResource> listVmNotInAnyNetwork(NovaApi novaApi, NeutronApi neutronApi, String region) throws OpenStackException;

    List<VMResource> listVmCouldAttachSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String subnetId) throws OpenStackException;

    List<VMResource> listVmAttachedSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String subnetId) throws OpenStackException;

    String createKeyPair(NovaApi novaApi, long userVoUserId, String tenantId, String region, String name) throws OpenStackException;

    void checkCreateKeyPair(NovaApi novaApi, long userVoUserId, String tenantId, String region, String name) throws OpenStackException;

    void deleteKeyPair(NovaApi novaApi, long userVoUserId, String tenantId, String region, String name) throws OpenStackException;

    Page listVm(NovaApi novaApi, NeutronApi neutronApi, long userVoUserId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

    VMResource getVm(NovaApi novaApi, NeutronApi neutronApi, long userVoUserId, String region, String vmId) throws OpenStackException;

    void bindFloatingIp(NovaApi novaApi, NeutronApi neutronApi, String region, String vmId, String floatingIpId, String email, String userName) throws OpenStackException;

    void renameVm(NovaApi novaApi, long userVoUserId, String region, String vmId, String name) throws OpenStackException;

    void deleteVolume(CinderApi cinderApi, long tenantId, String region, String volumeId) throws OpenStackException;

    void checkVolumeOperational(long tenantId, String region, String volumeId) throws OpenStackException;

    void editRouter(NovaApi novaApi, NeutronApi neutronApi, String region, String routerId, String name, boolean enablePublicNetworkGateway, String publicNetworkId) throws OpenStackException;

    void separateSubnetFromRouter(NeutronApi neutronApi, String region, String routerId, String subnetId) throws OpenStackException;

    void createDefaultSecurityGroupAndRule(NeutronApi neutronApi) throws OpenStackException;

    Page listPrivateSubnet(NeutronApi neutronApi, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

    VolumeSnapshotResource getVolumeSnapshot(CinderApi cinderApi, long userVoUserId, String region, String volumeSnapshotId) throws OpenStackException;

    Page listVolume(CinderApi cinderApi, long userVoUserId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;
}
