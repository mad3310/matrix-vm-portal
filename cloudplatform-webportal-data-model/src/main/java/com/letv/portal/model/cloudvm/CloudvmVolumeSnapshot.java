package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public class CloudvmVolumeSnapshot extends BaseModel {
    private static final long serialVersionUID = -9026774077482265961L;

    private String region;
    private String volumeSnapshotId;
    private String volumeId;
    private CloudvmVolumeStatus status;
    private Integer size;
    private String name;
    private String description;
    private Long tenantId;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getVolumeSnapshotId() {
        return volumeSnapshotId;
    }

    public void setVolumeSnapshotId(String volumeSnapshotId) {
        this.volumeSnapshotId = volumeSnapshotId;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public CloudvmVolumeStatus getStatus() {
        return status;
    }

    public void setStatus(CloudvmVolumeStatus status) {
        this.status = status;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
