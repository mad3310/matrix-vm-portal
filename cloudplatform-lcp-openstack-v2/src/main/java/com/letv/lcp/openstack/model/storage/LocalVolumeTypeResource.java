package com.letv.lcp.openstack.model.storage;

import com.letv.portal.model.cloudvm.CloudvmVolumeType;


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

    @Override
    public Boolean getEnable() {
        return cloudvmVolumeType.getEnable();
    }

    @Override
    public Long getCapacity() {
        return cloudvmVolumeType.getCapacity();
    }
}
