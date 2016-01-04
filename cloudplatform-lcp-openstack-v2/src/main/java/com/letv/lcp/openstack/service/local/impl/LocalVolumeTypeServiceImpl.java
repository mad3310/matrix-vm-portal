package com.letv.lcp.openstack.service.local.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.ResourceNotFoundException;
import com.letv.lcp.openstack.model.storage.LocalVolumeTypeResource;
import com.letv.lcp.openstack.model.storage.VolumeTypeResource;
import com.letv.lcp.openstack.service.local.ILocalRegionService;
import com.letv.lcp.openstack.service.local.ILocalVolumeTypeService;
import com.letv.portal.model.cloudvm.CloudvmVolumeType;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
@Service
public class LocalVolumeTypeServiceImpl implements ILocalVolumeTypeService {
    @Autowired
    private ILocalRegionService localRegionService;

    @Override
    public List<VolumeTypeResource> list(String region) throws OpenStackException {
        localRegionService.get(region);

        List<VolumeTypeResource> volumeTypeResources = new LinkedList<VolumeTypeResource>();
        for (CloudvmVolumeType cloudvmVolumeType : CloudvmVolumeType.values()) {
            volumeTypeResources.add(new LocalVolumeTypeResource(region, cloudvmVolumeType));
        }
        return volumeTypeResources;
    }

    @Override
    public VolumeTypeResource get(String region, String volumeTypeId) throws OpenStackException {
        localRegionService.get(region);

        CloudvmVolumeType[] cloudvmVolumeTypes = CloudvmVolumeType.values();
        for (CloudvmVolumeType cloudvmVolumeType : cloudvmVolumeTypes) {
            if (StringUtils.equals(cloudvmVolumeType.getVolumeTypeId(), volumeTypeId)) {
                return new LocalVolumeTypeResource(region, cloudvmVolumeType);
            }
        }
        throw new ResourceNotFoundException("VolumeType", "云硬盘类型", volumeTypeId);
    }
}
