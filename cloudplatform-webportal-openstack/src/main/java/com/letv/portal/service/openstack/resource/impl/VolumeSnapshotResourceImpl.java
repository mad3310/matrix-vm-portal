package com.letv.portal.service.openstack.resource.impl;

import com.letv.portal.service.openstack.resource.VolumeSnapshotResource;
import org.jclouds.openstack.cinder.v1.domain.Snapshot;
import org.jclouds.openstack.cinder.v1.domain.Volume;

/**
 * Created by zhouxianguang on 2015/10/12.
 */
public class VolumeSnapshotResourceImpl implements VolumeSnapshotResource {

    private String region;
    private Snapshot snapshot;
    private Volume volume;

    public VolumeSnapshotResourceImpl(String region, Snapshot snapshot, Volume volume) {
        this.region = region;
        this.snapshot = snapshot;
        this.volume = volume;
    }

    public VolumeSnapshotResourceImpl(String region, Snapshot snapshot) {
        this.region = region;
        this.snapshot = snapshot;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    @Override
    public String getName() {
        return snapshot.getName();
    }

    @Override
    public String getVolumeName() {
        return volume.getName();
    }

    @Override
    public String getVolumeId() {
        return volume.getId();
    }

    @Override
    public String getStatus() {
        return snapshot.getStatus().toString();
    }

    @Override
    public Integer getSize() {
        return snapshot.getSize();
    }

    @Override
    public Long getCreated() {
        return snapshot.getCreated().getTime();
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public String getId() {
        return snapshot.getId();
    }
}
