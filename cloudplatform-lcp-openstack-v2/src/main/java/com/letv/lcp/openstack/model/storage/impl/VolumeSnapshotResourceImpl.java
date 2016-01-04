package com.letv.lcp.openstack.model.storage.impl;

import org.jclouds.openstack.cinder.v1.domain.Snapshot;

import com.letv.lcp.openstack.model.storage.VolumeResource;
import com.letv.lcp.openstack.model.storage.VolumeSnapshotResource;

/**
 * Created by zhouxianguang on 2015/10/12.
 */
public class VolumeSnapshotResourceImpl implements VolumeSnapshotResource {

    private String region;
    private Snapshot snapshot;
    private VolumeResource volume;

    public VolumeSnapshotResourceImpl(String region, Snapshot snapshot, VolumeResource volume) {
        this.region = region;
        this.snapshot = snapshot;
        this.volume = volume;
    }

    public VolumeSnapshotResourceImpl(String region, Snapshot snapshot) {
        this.region = region;
        this.snapshot = snapshot;
    }

    public void setVolume(VolumeResource volume) {
        this.volume = volume;
    }

    @Override
    public String getName() {
        return snapshot.getName();
    }

    @Override
    public String getVolumeName() {
        return volume != null ? volume.getName() : null;
    }

    @Override
    public String getVolumeId() {
        return volume != null ? volume.getId() : null;
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
    public String getDescription() {
        return snapshot.getDescription();
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
