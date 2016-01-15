package com.letv.portal.model.cloudvm.lcp;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeStatusEnum;
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeTypeEnum;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public class CloudvmStorageModel extends BaseModel{

    private static final long serialVersionUID = 1302396074647557995L;

    private String name;
    private String region;
    private String storageInstanceId;
    private CloudvmVolumeStatusEnum status;//存储状态
    private Integer size;
    private CloudvmVolumeTypeEnum storageType;//存数类型
    private Long serverId;//挂载的云主机ID
    private Long tenantId;//租户ID
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getStorageInstanceId() {
		return storageInstanceId;
	}
	public void setStorageInstanceId(String storageInstanceId) {
		this.storageInstanceId = storageInstanceId;
	}
	public CloudvmVolumeStatusEnum getStatus() {
		return status;
	}
	public void setStatus(CloudvmVolumeStatusEnum status) {
		this.status = status;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public CloudvmVolumeTypeEnum getStorageType() {
		return storageType;
	}
	public void setStorageType(CloudvmVolumeTypeEnum storageType) {
		this.storageType = storageType;
	}
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
    
}
