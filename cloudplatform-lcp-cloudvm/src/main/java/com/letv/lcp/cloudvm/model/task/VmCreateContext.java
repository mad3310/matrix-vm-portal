package com.letv.lcp.cloudvm.model.task;


public class VmCreateContext {

	private String volumeId;
	private String floatingIpId;
	private String subnetPortId;
	private String serverId;
	private String serverCreatedId;
	private String resourceName;
	
	
	public String getServerCreatedId() {
		return serverCreatedId;
	}
	public void setServerCreatedId(String serverCreatedId) {
		this.serverCreatedId = serverCreatedId;
	}
	public String getSubnetPortId() {
		return subnetPortId;
	}
	public void setSubnetPortId(String subnetPortId) {
		this.subnetPortId = subnetPortId;
	}
	public String getVolumeId() {
		return volumeId;
	}
	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}
	public String getFloatingIpId() {
		return floatingIpId;
	}
	public void setFloatingIpId(String floatingIpId) {
		this.floatingIpId = floatingIpId;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	

}
