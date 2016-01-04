package com.letv.lcp.openstack.model.network;

public class SubnetIp {
	private String subnetId;
	private SubnetResource subnetResource;
	private String ipAddress;

	public SubnetIp() {
	}

	public SubnetIp(String subnetId, String ipAddress) {
		this.subnetId = subnetId;
		this.ipAddress = ipAddress;
	}

	public SubnetIp(SubnetResource subnetResource, String ipAddress) {
		this.subnetResource = subnetResource;
		this.ipAddress = ipAddress;
	}

	public SubnetIp(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public SubnetResource getSubnet() {
		return subnetResource;
	}

	public void setSubnet(SubnetResource subnetResource) {
		this.subnetResource = subnetResource;
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
