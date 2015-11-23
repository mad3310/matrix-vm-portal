package com.letv.portal.service.openstack.resource;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class IPAddresses {
	
	private List<SubnetIp> privateIP;
	
	private List<String> publicIP;
	
	private List<String> sharedIP;

//	private List<String> internalIP;

	public IPAddresses() {
		privateIP = new LinkedList<SubnetIp>();
		publicIP = new LinkedList<String>();
		sharedIP = new LinkedList<String>();
//		internalIP = new LinkedList<String>();
	}

	@JsonProperty("private")
	public List<SubnetIp> getPrivateIP() {
		return privateIP;
	}

	public void setPrivateIP(List<SubnetIp> privateIP) {
		this.privateIP = privateIP;
	}

	@JsonProperty("public")
	public List<String> getPublicIP() {
		return publicIP;
	}

	public void setPublicIP(List<String> publicIP) {
		this.publicIP = publicIP;
	}

	@JsonProperty("shared")
	public List<String> getSharedIP() {
		return sharedIP;
	}

	public void setSharedIP(List<String> sharedIP) {
		this.sharedIP = sharedIP;
	}

//	@JsonProperty("internal")
//	public List<String> getInternalIP() {
//		return internalIP;
//	}
//
//	public void setInternalIP(List<String> internalIP) {
//		this.internalIP = internalIP;
//	}
}
