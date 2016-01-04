package com.letv.lcp.openstack.service.resource;

import java.util.List;

import com.letv.common.paging.impl.Page;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.compute.VMResource;
import com.letv.lcp.openstack.model.storage.VolumeSnapshotResource;
import com.letv.lcp.openstack.util.Ref;
import com.letv.lcp.openstack.util.tuple.Tuple2;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public interface IResourceServiceFacade {
    void attachVmsToSubnet(String region, String vmIds, String subnetId, Ref<Tuple2<List<String>, String>> vmNamesAndSubnetName) throws OpenStackException;

    void detachVmsFromSubnet(String region, String vmIds, String subnetId, Ref<Tuple2<List<String>, String>> vmNamesAndSubnetName) throws OpenStackException;

    List<VMResource> listVmNotInAnyNetwork(String region) throws OpenStackException;

    List<VMResource> listVmCouldAttachSubnet(String region, String subnetId) throws OpenStackException;

    List<VMResource> listVmAttachedSubnet(String region, String subnetId) throws OpenStackException;

    String createKeyPair(String region, String name) throws OpenStackException;

    void checkCreateKeyPair(String region, String name) throws OpenStackException;

    void deleteKeyPair(String region, String name) throws OpenStackException;

    Page listVm(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

    void bindFloatingIp(String region, String vmId, String floatingIpId) throws OpenStackException;

    void renameVm(String region, String vmId, String name) throws OpenStackException;

    void deleteVolume(String region, String volumeId) throws OpenStackException;

    void editRouter(String region, String routerId, String name, boolean enablePublicNetworkGateway,
                    String publicNetworkId) throws OpenStackException;

    void separateSubnetFromRouter(String region, String routerId,
                                  String subnetId) throws OpenStackException;

    VMResource getVm(String region, String vmId) throws OpenStackException;

    Page listPrivateSubnet(String region, String name, Integer currentPage,
                           Integer recordsPerPage) throws OpenStackException;

    VolumeSnapshotResource getVolumeSnapshot(String region, String volumeSnapshotId) throws OpenStackException;

    Page listVolume(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;
}
