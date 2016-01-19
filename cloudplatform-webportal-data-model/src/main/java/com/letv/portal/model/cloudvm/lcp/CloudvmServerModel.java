package com.letv.portal.model.cloudvm.lcp;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.cloudvm.CloudvmServerStatusEnum;


public class CloudvmServerModel extends BaseModel {

    private static final long serialVersionUID = 441262241566576508L;

    private String region;
    private String serverInstanceId;
    private String name;
    private Long tenantId;
    private CloudvmServerStatusEnum status;
    
    private Integer cpu;
    private Integer ram;//内存
    private Integer storage;//该云主机下所有硬盘大小总和
    
    private String privateIp;//内网ip
    private Long publicNetworkId;//公网id
    private Long imageId;//镜像id
    
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getServerInstanceId() {
		return serverInstanceId;
	}
	public void setServerInstanceId(String serverInstanceId) {
		this.serverInstanceId = serverInstanceId;
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
	public CloudvmServerStatusEnum getStatus() {
		return status;
	}
	public void setStatus(CloudvmServerStatusEnum status) {
		this.status = status;
	}
	public Integer getCpu() {
		return cpu;
	}
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	public Integer getRam() {
		return ram;
	}
	public void setRam(Integer ram) {
		this.ram = ram;
	}
	public Integer getStorage() {
		return storage;
	}
	public void setStorage(Integer storage) {
		this.storage = storage;
	}
	public String getPrivateIp() {
		return privateIp;
	}
	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}
	public Long getPublicNetworkId() {
		return publicNetworkId;
	}
	public void setPublicNetworkId(Long publicNetworkId) {
		this.publicNetworkId = publicNetworkId;
	}
	public Long getImageId() {
		return imageId;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
    
}
