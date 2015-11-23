package com.letv.portal.service.openstack.local.service;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.VolumeTypeResource;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public interface LocalVolumeTypeService {
    List<VolumeTypeResource> list(String region) throws OpenStackException;

    VolumeTypeResource get(String region, String volumeTypeId) throws OpenStackException;
}
