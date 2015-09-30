package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

import java.util.Date;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
public class CloudvmImage extends BaseModel {

    private static final long serialVersionUID = -8649677463047922945L;

    private String region;
    private String imageId;
    private String name;
    private String containerFormat;
    private String diskFormat;
    private Long size;
    private String checkSum;
    private Long minDisk;
    private Long minRam;
    private String location;
    private String owner;
    private Date updatedAt;
    private Date createdAt;
    private Date deletedAt;
    private String status;
    private Boolean isPublic;

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

    public String getContainerFormat() {
        return containerFormat;
    }

    public void setContainerFormat(String containerFormat) {
        this.containerFormat = containerFormat;
    }

    public String getDiskFormat() {
        return diskFormat;
    }

    public void setDiskFormat(String diskFormat) {
        this.diskFormat = diskFormat;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}
