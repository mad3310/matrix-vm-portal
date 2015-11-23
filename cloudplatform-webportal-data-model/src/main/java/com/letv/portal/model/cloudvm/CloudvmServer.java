package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

import java.util.Date;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public class CloudvmServer extends BaseModel {

    private static final long serialVersionUID = 441262241566576508L;

    private String region;
    private String serverId;
    private String flavorId;
    private String name;
    private Long tenantId;
    private CloudvmServerStatus status;
    private String imageId;
    private String extendedStatusTaskState;
    private String extendedStatusVmState;
    private Integer extendedPowerState;
    private String publicIp;
    private String privateIp;
    private String subnetId;
    private String networkId;

    public CloudvmServer() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getFlavorId() {
        return flavorId;
    }

    public void setFlavorId(String flavorId) {
        this.flavorId = flavorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public CloudvmServerStatus getStatus() {
        return status;
    }

    public void setStatus(CloudvmServerStatus status) {
        this.status = status;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getExtendedStatusTaskState() {
        return extendedStatusTaskState;
    }

    public void setExtendedStatusTaskState(String extendedStatusTaskState) {
        this.extendedStatusTaskState = extendedStatusTaskState;
    }

    public String getExtendedStatusVmState() {
        return extendedStatusVmState;
    }

    public void setExtendedStatusVmState(String extendedStatusVmState) {
        this.extendedStatusVmState = extendedStatusVmState;
    }

    public Integer getExtendedPowerState() {
        return extendedPowerState;
    }

    public void setExtendedPowerState(Integer extendedPowerState) {
        this.extendedPowerState = extendedPowerState;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getPrivateIp() {
        return privateIp;
    }

    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }
}
