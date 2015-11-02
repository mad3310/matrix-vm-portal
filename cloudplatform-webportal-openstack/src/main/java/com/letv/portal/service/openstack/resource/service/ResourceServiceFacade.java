package com.letv.portal.service.openstack.resource.service;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.VMResource;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public interface ResourceServiceFacade {
    void attachVmToSubnet(String region, String vmId, String subnetId) throws OpenStackException;

    void detachVmFromSubnet(String region, String vmId, String subnetId) throws OpenStackException;

    List<VMResource> listVmNotInAnyNetwork(String region) throws OpenStackException;

    List<VMResource> listVmAttachedSubnet(String region, String subnetId) throws OpenStackException;

    String createKeyPair(String region, String name) throws OpenStackException;

    void checkCreateKeyPair(String region, String name) throws OpenStackException;
}
