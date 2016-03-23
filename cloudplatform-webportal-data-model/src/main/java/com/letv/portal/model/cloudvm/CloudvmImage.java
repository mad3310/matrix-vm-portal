package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
public class CloudvmImage extends BaseModel {

    private static final long serialVersionUID = -8649677463047922945L;

    private Long cloudvmClusterId;
    private String region;
    private String imageId;
    private String name;
    private Long size;
    private Long minDisk;
    private Long minRam;
    private CloudvmImageStatus status;
    private Long tenantId;
    private CloudvmImageType imageType;
    private String platform;
    private CloudvmImageShareType type;
    private String serverId;
    private String serverName;

    

	public Long getCloudvmClusterId() {
		return cloudvmClusterId;
	}

	public void setCloudvmClusterId(Long cloudvmClusterId) {
		this.cloudvmClusterId = cloudvmClusterId;
	}

	public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public CloudvmImageShareType getType() {
        return type;
    }

    public void setType(CloudvmImageShareType type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getMinDisk() {
        return minDisk;
    }

    public void setMinDisk(Long minDisk) {
        this.minDisk = minDisk;
    }

    public Long getMinRam() {
        return minRam;
    }

    public void setMinRam(Long minRam) {
        this.minRam = minRam;
    }

    public CloudvmImageStatus getStatus() {
        return status;
    }

    public void setStatus(CloudvmImageStatus status) {
        this.status = status;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public CloudvmImageType getImageType() {
        return imageType;
    }

    public void setImageType(CloudvmImageType imageType) {
        this.imageType = imageType;
    }
}
