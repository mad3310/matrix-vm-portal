package com.letv.portal.service.openstack.local.service;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmImageStatus;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.service.openstack.exception.OpenStackException;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;
import org.jclouds.openstack.nova.v2_0.domain.Server;

/**
 * Created by zhouxianguang on 2015/10/23.
 */
public interface LocalImageService {
    Page listImage(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

    Page listVmSnapshot(long tenantId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

    CloudvmImage createVmSnapshot(long userId, long tenantId, String region, ImageDetails image, Server server);

    void updateVmSnapshotStatus(long userId,long tenantId,String region,String imageId, CloudvmImageStatus status);

    void deleteVmSnapshot(long tenantId, String region, String imageId);
}
