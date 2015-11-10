package com.letv.portal.service.openstack.resource.service;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.VMResource;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public interface ResourceServiceFacade {
    void attachVmsToSubnet(String region, String vmIds, String subnetId) throws OpenStackException;

    void detachVmsFromSubnet(String region, String vmIds, String subnetId) throws OpenStackException;

    List<VMResource> listVmNotInAnyNetwork(String region) throws OpenStackException;

    List<VMResource> listVmCouldAttachSubnet(String region, String subnetId) throws OpenStackException;

    List<VMResource> listVmAttachedSubnet(String region, String subnetId) throws OpenStackException;

    String createKeyPair(String region, String name) throws OpenStackException;

    void checkCreateKeyPair(String region, String name) throws OpenStackException;

    void deleteKeyPair(String region, String name) throws OpenStackException;
}
