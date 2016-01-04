package com.letv.lcp.openstack.service.local;

import java.util.List;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.storage.VolumeTypeResource;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public interface ILocalVolumeTypeService {
    List<VolumeTypeResource> list(String region) throws OpenStackException;

    VolumeTypeResource get(String region, String volumeTypeId) throws OpenStackException;
}
