package com.letv.portal.service.openstack.local.resource;

import com.letv.portal.model.cloudvm.CloudvmVolumeType;
import com.letv.portal.service.openstack.resource.VolumeTypeResource;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public class LocalVolumeTypeResource implements VolumeTypeResource {

    private String region;
    private CloudvmVolumeType cloudvmVolumeType;

    public LocalVolumeTypeResource(String region, CloudvmVolumeType cloudvmVolumeType) {
        this.region = region;
        this.cloudvmVolumeType = cloudvmVolumeType;
    }

    @Override
    public String getName() {
        return cloudvmVolumeType.getDisplayName();
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public String getId() {
        return cloudvmVolumeType.getVolumeTypeId();
    }

    public Long getThroughput() {
        return cloudvmVolumeType.getThroughput();
    }

    public Long getIops() {
        return cloudvmVolumeType.getIops();
    }
}
