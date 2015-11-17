package com.letv.portal.service.openstack.resource.service;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.util.tuple.Tuple2;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public interface ResourceServiceFacade {
    void attachVmsToSubnet(String region, String vmIds, String subnetId, Tuple2<List<String>, String> vmNamesAndSubnetName) throws OpenStackException;

    void detachVmsFromSubnet(String region, String vmIds, String subnetId, Tuple2<List<String>, String> vmNamesAndSubnetName) throws OpenStackException;

    List<VMResource> listVmNotInAnyNetwork(String region) throws OpenStackException;

    List<VMResource> listVmCouldAttachSubnet(String region, String subnetId) throws OpenStackException;

    List<VMResource> listVmAttachedSubnet(String region, String subnetId) throws OpenStackException;

    String createKeyPair(String region, String name) throws OpenStackException;

    void checkCreateKeyPair(String region, String name) throws OpenStackException;

    void deleteKeyPair(String region, String name) throws OpenStackException;

    Page listVm(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;
}
