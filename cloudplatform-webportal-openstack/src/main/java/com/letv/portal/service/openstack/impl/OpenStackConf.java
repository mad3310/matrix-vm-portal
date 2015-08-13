package com.letv.portal.service.openstack.impl;

import java.io.Serializable;

public class OpenStackConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3301523223935880732L;

	private String publicEndpoint;

	private String globalPublicNetworkId;

	private String globalSharedNetworkId;

	private String userPrivateNetworkName;

	private String userPrivateNetworkSubnetName;

	private String userPrivateNetworkSubnetCidr;

	private String userPrivateRouterName;
	
	public OpenStackConf() {
	}
	
	public String getUserPrivateRouterName() {
		return userPrivateRouterName;
	}

	public void setUserPrivateRouterName(String userPrivateRouterName) {
		this.userPrivateRouterName = userPrivateRouterName;
	}

	public String getPublicEndpoint() {
		return publicEndpoint;
	}

	public void setPublicEndpoint(String publicEndpoint) {
		this.publicEndpoint = publicEndpoint;
	}

	public String getGlobalPublicNetworkId() {
		return globalPublicNetworkId;
	}

	public void setGlobalPublicNetworkId(String globalPublicNetworkId) {
		this.globalPublicNetworkId = globalPublicNetworkId;
	}

	public String getGlobalSharedNetworkId() {
		return globalSharedNetworkId;
	}

	public void setGlobalSharedNetworkId(String globalSharedNetworkId) {
		this.globalSharedNetworkId = globalSharedNetworkId;
	}

	public String getUserPrivateNetworkName() {
		return userPrivateNetworkName;
	}

	public void setUserPrivateNetworkName(String userPrivateNetworkName) {
		this.userPrivateNetworkName = userPrivateNetworkName;
	}

	public String getUserPrivateNetworkSubnetName() {
		return userPrivateNetworkSubnetName;
	}

	public void setUserPrivateNetworkSubnetName(String userPrivateNetworkSubnetName) {
		this.userPrivateNetworkSubnetName = userPrivateNetworkSubnetName;
	}

	public String getUserPrivateNetworkSubnetCidr() {
		return userPrivateNetworkSubnetCidr;
	}

	public void setUserPrivateNetworkSubnetCidr(String userPrivateNetworkSubnetCidr) {
		this.userPrivateNetworkSubnetCidr = userPrivateNetworkSubnetCidr;
	}

}
