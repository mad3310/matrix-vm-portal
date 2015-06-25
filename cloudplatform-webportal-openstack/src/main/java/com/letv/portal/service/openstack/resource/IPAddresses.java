package com.letv.portal.service.openstack.resource;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class IPAddresses {
	
	private List<String> privateIP;
	
	private List<String> publicIP;
	
	private List<String> sharedIP;

	public IPAddresses() {
		privateIP = new LinkedList<String>();
		publicIP = new LinkedList<String>();
		sharedIP = new LinkedList<String>();
	}

	@JsonProperty("private")
	public List<String> getPrivateIP() {
		return privateIP;
	}

	public void setPrivateIP(List<String> privateIP) {
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

}
