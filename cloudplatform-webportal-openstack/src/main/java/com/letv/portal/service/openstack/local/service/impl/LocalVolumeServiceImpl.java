package com.letv.portal.service.openstack.local.service.impl;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmServer;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.model.cloudvm.CloudvmVolumeType;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;
import com.letv.portal.service.openstack.cronjobs.VolumeSyncService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.local.resource.LocalVmResource;
import com.letv.portal.service.openstack.local.resource.LocalVolumeResource;
import com.letv.portal.service.openstack.local.service.LocalRegionService;
import com.letv.portal.service.openstack.local.service.LocalVolumeService;
import com.letv.portal.service.openstack.resource.Region;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.VolumeResource;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
@Service
public class LocalVolumeServiceImpl implements LocalVolumeService {

    @Autowired
    private ICloudvmVolumeService cloudvmVolumeService;
    @Autowired
    private LocalRegionService localRegionService;

    @Override
    public VolumeResource get(long tenantId, String region, String volumeId) throws OpenStackException {
        localRegionService.get(region);
        VolumeResource volumeResource = new LocalVolumeResource(cloudvmVolumeService.selectByVolumeId(tenantId, region, volumeId));
        if (volumeResource == null) {
            throw new ResourceNotFoundException("Volume", "云硬盘", volumeId);
        }
        return volumeResource;
    }

    @Override
    public Page list(long tenantId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException {
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
        List<CloudvmVolume> cloudvmVolumes = cloudvmVolumeService.selectByName(tenantId, region, name, page);
        List<VolumeResource> volumeResources = new LinkedList<VolumeResource>();
        for (CloudvmVolume cloudvmVolume : cloudvmVolumes) {
            volumeResources.add(new LocalVolumeResource(cloudvmVolume));
        }

        if (page == null) {
            page = new Page();
        }
        page.setTotalRecords(cloudvmVolumeService.countByName(tenantId, region, name));
        page.setData(volumeResources);
        return page;
    }

    private void copyProperties(Volume volume, CloudvmVolume cloudvmVolume) {
        if (volume.getAttachments() != null && volume.getAttachments().size() > 0) {
            cloudvmVolume.setServerId(volume.getAttachments().iterator().next().getServerId());
        } else {
            cloudvmVolume.setServerId(null);
        }
        cloudvmVolume.setSize(volume.getSize());
        cloudvmVolume.setStatus(CloudvmVolumeStatus.valueOf(volume.getStatus().name()));
    }

    @Override
    public CloudvmVolume create(long userId, long tenantId, String region, Volume volume) {
        return create(userId, tenantId, region, volume, null);
    }

    @Override
    public CloudvmVolume create(long userId, long tenantId, String region, Volume volume, CloudvmVolumeStatus initStatus) {
        CloudvmVolume cloudvmVolume = new CloudvmVolume();
        copyProperties(volume, cloudvmVolume);
        cloudvmVolume.setCreateUser(userId);
        cloudvmVolume.setTenantId(tenantId);
        cloudvmVolume.setRegion(region);
        cloudvmVolume.setVolumeType(CloudvmVolumeType.valueOf(volume.getVolumeType()));
        cloudvmVolume.setSnapshotId(cloudvmVolume.getSnapshotId());
        cloudvmVolume.setVolumeId(volume.getId());
        cloudvmVolume.setName(volume.getName());
        cloudvmVolume.setDescription(volume.getDescription());
        if (initStatus != null) {
            cloudvmVolume.setStatus(initStatus);
        }
        cloudvmVolumeService.insert(cloudvmVolume);
        return cloudvmVolume;
    }

    @Override
    public CloudvmVolume createIfNotExists(long userId, long tenantId, String region, Volume volume, CloudvmVolumeStatus initStatus) {
        CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(tenantId, region, volume.getId());
        if (cloudvmVolume == null) {
            cloudvmVolume = create(userId, tenantId, region, volume, initStatus);
        }
        return cloudvmVolume;
    }

    @Override
    public void update(long userId, long tenantId, String region, Volume volume) {
        CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(tenantId, region, volume.getId());
        if (cloudvmVolume != null) {
            copyProperties(volume, cloudvmVolume);
            cloudvmVolume.setUpdateUser(userId);
            cloudvmVolumeService.update(cloudvmVolume);
        }
    }

    @Override
    public void updateNameAndDesc(long userId, long tenantId, String region, String volumeId, String name, String description) {
        CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(tenantId, region, volumeId);
        if (cloudvmVolume != null) {
            cloudvmVolume.setName(name);
            cloudvmVolume.setDescription(description);
            cloudvmVolume.setUpdateUser(userId);
            cloudvmVolumeService.update(cloudvmVolume);
        }
    }

    @Override
    public void delete(long tenantId, String region, String volumeId) {
        CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(tenantId, region, volumeId);
        if (cloudvmVolume != null) {
            cloudvmVolumeService.delete(cloudvmVolume);
        }
    }
}
