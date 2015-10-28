package com.letv.portal.service.openstack.local.service.impl;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmImageShareType;
import com.letv.portal.model.cloudvm.CloudvmImageStatus;
import com.letv.portal.model.cloudvm.CloudvmImageType;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.local.resource.LocalImageResource;
import com.letv.portal.service.openstack.local.service.LocalImageService;
import com.letv.portal.service.openstack.local.service.LocalRegionService;
import com.letv.portal.service.openstack.resource.ImageResource;
import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/23.
 */
@Service
public class LocalImageServiceImpl implements LocalImageService {

    @Autowired
    private ICloudvmImageService cloudvmImageService;

    @Autowired
    private LocalRegionService localRegionService;

    @Override
    public Page listImage(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException {
        localRegionService.get(region);

        Page page = null;
        if (currentPage != null && recordsPerPage != null) {
            if (currentPage <= 0) {
                throw new ValidateException("当前页数不能小于或等于0");
            }
            if (recordsPerPage <= 0) {
                throw new ValidateException("每页记录数不能小于或等于0");
            }
            page = new Page(currentPage, recordsPerPage);
        }
        List<CloudvmImage> cloudvmImages = cloudvmImageService.selectImageByName(region, name, page);
        List<ImageResource> imageResources = new LinkedList<ImageResource>();
        for (CloudvmImage cloudvmImage : cloudvmImages) {
            imageResources.add(new LocalImageResource(cloudvmImage));
        }

        if (page == null) {
            page = new Page();
        }
        page.setTotalRecords(cloudvmImageService.countImageByName(region, name));
        page.setData(imageResources);
        return page;
    }

    @Override
    public Page listVmSnapshot(long tenantId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException {
        localRegionService.get(region);

        Page page = null;
        if (currentPage != null && recordsPerPage != null) {
            if (currentPage <= 0) {
                throw new ValidateException("当前页数不能小于或等于0");
            }
            if (recordsPerPage <= 0) {
                throw new ValidateException("每页记录数不能小于或等于0");
            }
            page = new Page(currentPage, recordsPerPage);
        }
        List<CloudvmImage> cloudvmImages = cloudvmImageService.selectVmSnapshotByName(tenantId, region, name, page);
        List<ImageResource> imageResources = new LinkedList<ImageResource>();
        for (CloudvmImage cloudvmImage : cloudvmImages) {
            imageResources.add(new LocalImageResource(cloudvmImage));
        }

        if (page == null) {
            page = new Page();
        }
        page.setTotalRecords(cloudvmImageService.countVmSnapshotByName(tenantId, region, name));
        page.setData(imageResources);
        return page;
    }

    @Override
    public CloudvmImage createVmSnapshot(long userId, long tenantId, String region, ImageDetails image, Server server) {
        CloudvmImage cloudvmImage = new CloudvmImage();
        cloudvmImage.setType(CloudvmImageShareType.PRIVATE);
        cloudvmImage.setCreateUser(userId);
        cloudvmImage.setTenantId(tenantId);
        cloudvmImage.setRegion(region);
        cloudvmImage.setImageId(image.getId());
        cloudvmImage.setMinDisk(image.getMinDisk());
        cloudvmImage.setMinRam(image.getMinRam());
        cloudvmImage.setName(image.getName());
        cloudvmImage.setSize(image.getSize().get());
        cloudvmImage.setStatus(CloudvmImageStatus.valueOf(image.getStatus().name()));
        cloudvmImage.setImageType(CloudvmImageType.SNAPSHOT);
        cloudvmImage.setServerId(server.getId());
        cloudvmImage.setServerName(server.getName());
        cloudvmImageService.insert(cloudvmImage);
        return cloudvmImage;
    }

    @Override
    public void updateVmSnapshotStatus(long userId, long tenantId, String region, String vmSnapshotId, CloudvmImageStatus status) {
        CloudvmImage cloudvmImage = cloudvmImageService.selectVmSnapshotByVmSnapshotId(tenantId, region, vmSnapshotId);
        if (cloudvmImage != null) {
            cloudvmImage.setUpdateUser(userId);
            cloudvmImage.setStatus(status);
            cloudvmImageService.update(cloudvmImage);
        }
    }

    @Override
    public void deleteVmSnapshot(long tenantId, String region, String vmSnapshotId) {
        CloudvmImage cloudvmImage = cloudvmImageService.selectVmSnapshotByVmSnapshotId(tenantId, region, vmSnapshotId);
        if (cloudvmImage != null) {
            cloudvmImageService.delete(cloudvmImage);
        }
    }
}
