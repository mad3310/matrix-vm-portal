package com.letv.portal.service.openstack.resource.service;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.util.tuple.Tuple2;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import java.io.Closeable;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public interface ResourceService {

    void checkRegion(String region, Closeable... apis) throws RegionNotFoundException;

    void attachVmsToSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmIds, String subnetId, Tuple2<List<String>, String> vmNamesAndSubnetName) throws OpenStackException;

    void detachVmsFromSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmIds, String subnetId, Tuple2<List<String>, String> vmNamesAndSubnetName) throws OpenStackException;

    List<VMResource> listVmNotInAnyNetwork(NovaApi novaApi, NeutronApi neutronApi, String region) throws OpenStackException;

    List<VMResource> listVmCouldAttachSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String subnetId) throws OpenStackException;

    List<VMResource> listVmAttachedSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String subnetId) throws OpenStackException;

    String createKeyPair(NovaApi novaApi, long userVoUserId, String tenantId, String region, String name) throws OpenStackException;

    void checkCreateKeyPair(NovaApi novaApi, long userVoUserId, String tenantId, String region, String name) throws OpenStackException;

    void deleteKeyPair(NovaApi novaApi, long userVoUserId, String tenantId, String region, String name) throws OpenStackException;

    Page listVm(NovaApi novaApi, NeutronApi neutronApi, CinderApi cinderApi, long userVoUserId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

    void bindFloatingIp(NovaApi novaApi, NeutronApi neutronApi, String region, String vmId, String floatingIpId, String email, String userName) throws OpenStackException;

    void renameVm(NovaApi novaApi, String region, String vmId, String name) throws OpenStackException;
}
