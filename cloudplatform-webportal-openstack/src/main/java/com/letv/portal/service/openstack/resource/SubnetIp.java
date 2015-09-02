package com.letv.portal.service.openstack.resource;

public class SubnetIp {
	private String subnetId;
	private String ipAddress;

	public SubnetIp() {
	}

	public SubnetIp(String subnetId, String ipAddress) {
		this.subnetId = subnetId;
		this.ipAddress = ipAddress;
	}

	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
