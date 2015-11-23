package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public class CloudvmVolume extends BaseModel{

    private static final long serialVersionUID = 1302396074647557995L;

    private String region;
    private String volumeId;
    private CloudvmVolumeStatus status;
    private Integer size;
    private CloudvmVolumeType volumeType;
    private String snapshotId;
    private String name;
    private String description;
    private String serverId;
    private String serverName;
    private Long tenantId;
    private CloudvmServer server;

    public CloudvmVolume() {
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public CloudvmServer getServer() {
        return server;
    }

    public void setServer(CloudvmServer server) {
        this.server = server;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public CloudvmVolumeType getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(CloudvmVolumeType volumeType) {
        this.volumeType = volumeType;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
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

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
