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
	private String serverUuid;
	private String tenantId;
	private String userId;
	private Date updated;
	private Date created;
	private String hostId;
	private String accessIpv4;
	private String accessIpv6;
	private String status;
	private String imageId;
	private String keyName;
	private String configDrive;
	private String extendedStatusTaskState;
	private String extendedStatusVmState;
	private Integer extendedPowerState;
	private String extendedAttributesInstanceName;
	private String extendedAttributesHostName;
	private String extendedAttributesHypervisorHostName;
	private String diskConfig;
	private String availabilityZone;

	public CloudvmServer() {
	}

	public CloudvmServer(String region, String serverId, String flavorId) {
		this();
		this.region = region;
		this.serverId = serverId;
		this.flavorId = flavorId;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServerUuid() {
		return serverUuid;
	}

	public void setServerUuid(String serverUuid) {
		this.serverUuid = serverUuid;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAccessIpv4() {
		return accessIpv4;
	}

	public void setAccessIpv4(String accessIpv4) {
		this.accessIpv4 = accessIpv4;
	}

	public String getAccessIpv6() {
		return accessIpv6;
	}

	public void setAccessIpv6(String accessIpv6) {
		this.accessIpv6 = accessIpv6;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getConfigDrive() {
		return configDrive;
	}

	public void setConfigDrive(String configDrive) {
		this.configDrive = configDrive;
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

	public String getExtendedAttributesInstanceName() {
		return extendedAttributesInstanceName;
	}

	public void setExtendedAttributesInstanceName(String extendedAttributesInstanceName) {
		this.extendedAttributesInstanceName = extendedAttributesInstanceName;
	}

	public String getExtendedAttributesHostName() {
		return extendedAttributesHostName;
	}

	public void setExtendedAttributesHostName(String extendedAttributesHostName) {
		this.extendedAttributesHostName = extendedAttributesHostName;
	}

	public String getExtendedAttributesHypervisorHostName() {
		return extendedAttributesHypervisorHostName;
	}

	public void setExtendedAttributesHypervisorHostName(String extendedAttributesHypervisorHostName) {
		this.extendedAttributesHypervisorHostName = extendedAttributesHypervisorHostName;
	}

	public String getDiskConfig() {
		return diskConfig;
	}

	public void setDiskConfig(String diskConfig) {
		this.diskConfig = diskConfig;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}
}
