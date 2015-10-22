package com.letv.portal.service.openstack.local.service;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.VolumeResource;
import org.jclouds.openstack.cinder.v1.domain.Volume;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public interface LocalVolumeService {
    VolumeResource get(long tenantId, String region, String volumeId) throws OpenStackException;

    Page list(long tenantId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

    CloudvmVolume create(long userId,long tenantId, String region, Volume volume);

    void update(long userId, long tenantId, String region, Volume volume);

    void updateNameAndDesc(long userId, long tenantId, String region, String volumeId, String name, String description);

    void delete(long tenantId, String region, String volumeId);
}
