package com.letv.portal.service.openstack.local.service.impl;

import com.letv.portal.model.cloudvm.CloudvmVolumeType;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.local.resource.LocalVolumeTypeResource;
import com.letv.portal.service.openstack.local.service.LocalRegionService;
import com.letv.portal.service.openstack.local.service.LocalVolumeTypeService;
import com.letv.portal.service.openstack.resource.VolumeTypeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
@Service
public class LocalVolumeTypeServiceImpl implements LocalVolumeTypeService {
    @Autowired
    private LocalRegionService localRegionService;

    @Override
    public List<VolumeTypeResource> list(String region) throws OpenStackException {
        localRegionService.get(region);

        List<VolumeTypeResource> volumeTypeResources = new LinkedList<VolumeTypeResource>();
        for (CloudvmVolumeType cloudvmVolumeType : CloudvmVolumeType.values()) {
            volumeTypeResources.add(new LocalVolumeTypeResource(region, cloudvmVolumeType));
        }
        return volumeTypeResources;
    }
}
