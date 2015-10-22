package com.letv.portal.service.openstack.local.resource;

import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.service.openstack.resource.VolumeAttachmentResource;
import com.letv.portal.service.openstack.resource.VolumeResource;

import java.util.Set;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public class LocalVolumeResource implements VolumeResource {

    private CloudvmVolume cloudvmVolume;

    public LocalVolumeResource(CloudvmVolume cloudvmVolume) {
        this.cloudvmVolume = cloudvmVolume;
    }

    @Override
    public String getName() {
        return cloudvmVolume.getName();
    }

    @Override
    public String getStatus() {
        return cloudvmVolume.getStatus().toString();
    }

    @Override
    public int getSize() {
        return cloudvmVolume.getSize();
    }

    @Override
    public String getZone() {
        return null;
    }

    @Override
    public Long getCreated() {
        return cloudvmVolume.getCreateTime().getTime();
    }

    @Override
    public String getVolumeType() {
        return cloudvmVolume.getVolumeType().toString();
    }

    @Override
    public String getSnapshotId() {
        return cloudvmVolume.getSnapshotId();
    }

    @Override
    public String getDescription() {
        return cloudvmVolume.getDescription();
    }

//    @Override
//    public String getTenantId() {
//        return null;
//    }

    @Override
    public Set<VolumeAttachmentResource> getAttachments() {
        return null;
    }

    @Override
    public String getRegionDisplayName() {
        return null;
    }

    @Override
    public String getRegion() {
        return cloudvmVolume.getRegion();
    }

    @Override
    public String getId() {
        return cloudvmVolume.getVolumeId();
    }
}
