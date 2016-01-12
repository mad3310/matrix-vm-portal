package com.letv.lcp.openstack.service.local.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.ResourceNotFoundException;
import com.letv.lcp.openstack.model.storage.LocalVolumeResource;
import com.letv.lcp.openstack.model.storage.VolumeResource;
import com.letv.lcp.openstack.service.local.ILocalRegionService;
import com.letv.lcp.openstack.service.local.ILocalVolumeService;
import com.letv.lcp.openstack.util.cache.SyncLocalApiCache;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.model.cloudvm.CloudvmVolumeType;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
@Service
public class LocalVolumeServiceImpl implements ILocalVolumeService {

    @Autowired
    private ICloudvmVolumeService cloudvmVolumeService;
    @Autowired
    private ILocalRegionService localRegionService;

    @Override
    public VolumeResource get(long tenantId, String region, String volumeId) throws OpenStackException {
        localRegionService.get(region);
        CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(tenantId, region, volumeId);
        if (cloudvmVolume == null) {
            throw new ResourceNotFoundException("Volume", "云硬盘", volumeId);
        }
        return new LocalVolumeResource(cloudvmVolume);
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
        page.setTotalRecords(cloudvmVolumeService.selectCountByName(tenantId, region, name));
        page.setData(volumeResources);
        return page;
    }

    private void copyProperties(Volume volume, CloudvmVolume cloudvmVolume) throws OpenStackException {
        if (volume.getAttachments() != null && volume.getAttachments().size() > 0) {
            String newServerId = volume.getAttachments().iterator().next().getServerId();
            if (!StringUtils.equals(newServerId, cloudvmVolume.getServerId())) {
                cloudvmVolume.setServerId(newServerId);
                SyncLocalApiCache syncLocalApiCache = new SyncLocalApiCache();
                try {
                    Server server = syncLocalApiCache.getApi(cloudvmVolume.getTenantId(), NovaApi.class).getServerApi(cloudvmVolume.getRegion()).get(newServerId);
                    cloudvmVolume.setServerName(server.getName());
                } finally {
                    syncLocalApiCache.close();
                }
            }
        } else {
            cloudvmVolume.setServerId(null);
            cloudvmVolume.setServerName(null);
        }
        cloudvmVolume.setSize(volume.getSize());
        cloudvmVolume.setStatus(CloudvmVolumeStatus.valueOf(volume.getStatus().name()));
    }

    @Override
    public CloudvmVolume create(long userId, long tenantId, String region, Volume volume) throws OpenStackException {
        return create(userId, tenantId, region, volume, null);
    }

    @Override
    public CloudvmVolume create(long userId, long tenantId, String region, Volume volume, CloudvmVolumeStatus initStatus) throws OpenStackException {
        return create(userId, tenantId, region, volume, initStatus, null);
    }

    @Override
    public CloudvmVolume create(long userId, long tenantId, String region, Volume volume, CloudvmVolumeStatus initStatus, String vmId) throws OpenStackException {
        CloudvmVolume cloudvmVolume = new CloudvmVolume();
        cloudvmVolume.setCreateUser(userId);
        cloudvmVolume.setTenantId(tenantId);
        cloudvmVolume.setRegion(region);
        cloudvmVolume.setVolumeType(CloudvmVolumeType.valueOf(volume.getVolumeType()));
        cloudvmVolume.setSnapshotId(cloudvmVolume.getSnapshotId());
        cloudvmVolume.setVolumeId(volume.getId());
        cloudvmVolume.setName(volume.getName());
        cloudvmVolume.setDescription(volume.getDescription());
        copyProperties(volume, cloudvmVolume);
        if (initStatus != null) {
            cloudvmVolume.setStatus(initStatus);
        }
        if (vmId != null) {
            cloudvmVolume.setServerId(vmId);
        }
        cloudvmVolumeService.insert(cloudvmVolume);
        return cloudvmVolume;
    }

    @Override
    public CloudvmVolume createIfNotExists(long userId, long tenantId, String region, Volume volume, CloudvmVolumeStatus initStatus) throws OpenStackException {
        CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(tenantId, region, volume.getId());
        if (cloudvmVolume == null) {
            cloudvmVolume = create(userId, tenantId, region, volume, initStatus);
        }
        return cloudvmVolume;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(long userId, long tenantId, String region, Volume volume) throws OpenStackException {
        if (volume != null) {
            CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(tenantId, region, volume.getId());
            if (cloudvmVolume != null) {
                copyProperties(volume, cloudvmVolume);
                cloudvmVolume.setUpdateUser(userId);
                cloudvmVolumeService.update(cloudvmVolume);
            }
        }
    }

    @Override
    public void updateVmIdAndVmName(long userId, long tenantId, String region, String volumeId, String vmId, String vmName) throws OpenStackException {
        CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(tenantId, region, volumeId);
        if (cloudvmVolume != null) {
            cloudvmVolume.setServerId(vmId);
            cloudvmVolume.setServerName(vmName);
            cloudvmVolume.setUpdateUser(userId);
            cloudvmVolumeService.update(cloudvmVolume);
        }
    }

    @Override
    public void updateStatus(long userId, long tenantId, String region, String volumeId, CloudvmVolumeStatus status) throws OpenStackException {
        CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(tenantId, region, volumeId);
        if (cloudvmVolume != null) {
            cloudvmVolume.setStatus(status);
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
    public boolean delete(long tenantId, String region, String volumeId) {
        CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(tenantId, region, volumeId);
        if (cloudvmVolume != null) {
            cloudvmVolumeService.delete(cloudvmVolume);
            return true;
        }
        return false;
    }

    @Override
    public long count(long tenantId, String region) {
        return cloudvmVolumeService.selectCountByName(tenantId, region, null);
    }

    @Override
    public long countSize(long tenantId, String region) {
        return cloudvmVolumeService.selectCountSizeByRegion(tenantId, region);
    }
}
