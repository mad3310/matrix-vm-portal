package com.letv.lcp.openstack.model.image.impl;


import java.util.Date;

import com.letv.lcp.openstack.model.image.ImageResource;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmImageStatus;

/**
 * Created by zhouxianguang on 2015/10/23.
 */
public class LocalImageResource implements ImageResource {

    private CloudvmImage cloudvmImage;

    public LocalImageResource(CloudvmImage cloudvmImage) {
        this.cloudvmImage = cloudvmImage;
    }

    @Override
    public String getName() {
        return cloudvmImage.getName();
    }

    @Override
    public String getChecksum() {
        return null;
    }

    @Override
    public String getContainerFormat() {
        return null;
    }

    @Override
    public Long getCreatedAt() {
        Date date = cloudvmImage.getCreateTime();
        if (date != null) {
            return date.getTime();
        } else {
            return null;
        }
    }

    @Override
    public Long getUpdatedAt() {
        Date date = cloudvmImage.getUpdateTime();
        if (date != null) {
            return date.getTime();
        } else {
            return null;
        }
    }

    @Override
    public Long getDeletedAt() {
        return null;
    }

    @Override
    public String getDiskFormat() {
        return null;
    }

    @Override
    public String getLocation() {
        return null;
    }

    @Override
    public Long getMinDisk() {
        return cloudvmImage.getMinDisk();
    }

    @Override
    public Long getMinRam() {
        return cloudvmImage.getMinRam();
    }

    @Override
    public String getOwner() {
        return null;
    }

    @Override
    public Long getSize() {
        return cloudvmImage.getSize();
    }

    @Override
    public String getStatus() {
        CloudvmImageStatus status = cloudvmImage.getStatus();
        if (status != null) {
            return status.toString();
        } else {
            return null;
        }
    }

    @Override
    public String getType() {
        return cloudvmImage.getType().getDisplayName();
    }

    @Override
    public String getPlatform() {
        return cloudvmImage.getPlatform();
    }

    @Override
    public String getVmName() {
        return cloudvmImage.getServerName();
    }

    @Override
    public String getRegion() {
        return cloudvmImage.getRegion();
    }

    @Override
    public String getId() {
        return cloudvmImage.getImageId();
    }
}
