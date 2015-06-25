package com.letv.portal.service.openstack.resource;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class IPAddresses {
	@JsonProperty("private")
	private List<String> privateIP;
	@JsonProperty("public")
	private List<String> publicIP;
	@JsonProperty("shared")
	private List<String> sharedIP;

	public IPAddresses() {
		privateIP = new LinkedList<String>();
		publicIP = new LinkedList<String>();
		sharedIP = new LinkedList<String>();
	}

	public List<String> getPrivateIP() {
		return privateIP;
	}

	public void setPrivateIP(List<String> privateIP) {
		this.privateIP = privateIP;
	}

	public List<String> getPublicIP() {
		return publicIP;
	}

	public void setPublicIP(List<String> publicIP) {
		this.publicIP = publicIP;
	}

	public List<String> getSharedIP() {
		return sharedIP;
	}

	public void setSharedIP(List<String> sharedIP) {
		this.sharedIP = sharedIP;
	}

}
