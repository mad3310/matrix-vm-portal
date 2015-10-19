package com.letv.portal.service.openstack.resource.manager;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class VolumeCreateConf {
    private String region;
    private Integer size;
    private String volumeTypeId;
    private String volumeSnapshotId;
    private String name;
    private String description;
    private Integer count;

    public VolumeCreateConf() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getVolumeTypeId() {
        return volumeTypeId;
    }

    public void setVolumeTypeId(String volumeTypeId) {
        this.volumeTypeId = volumeTypeId;
    }

    public String getVolumeSnapshotId() {
        return volumeSnapshotId;
    }

    public void setVolumeSnapshotId(String volumeSnapshotId) {
        this.volumeSnapshotId = volumeSnapshotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
