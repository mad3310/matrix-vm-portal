package com.letv.portal.service.openstack.impl;

import java.io.Serializable;

public class OpenStackConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3301523223935880732L;

	private String adminEndpoint;

	private String publicEndpoint;

	private String userRegisterToken;
	
	private int routerGatewayBandWidth;

	private String basicUserName;
	
	public OpenStackConf() {
	}

	public void setBasicUserName(String basicUserName) {
		this.basicUserName = basicUserName;
	}

	public String getBasicUserName() {
		return basicUserName;
	}

	public String getPublicEndpoint() {
		return publicEndpoint;
	}

	public void setPublicEndpoint(String publicEndpoint) {
		this.publicEndpoint = publicEndpoint;
	}

	public void setAdminEndpoint(String adminEndpoint) {
		this.adminEndpoint = adminEndpoint;
	}

	public String getAdminEndpoint() {
		return adminEndpoint;
	}

	public String getUserRegisterToken() {
		return userRegisterToken;
	}

	public void setUserRegisterToken(String userRegisterToken) {
		this.userRegisterToken = userRegisterToken;
	}

	public int getRouterGatewayBandWidth() {
		return routerGatewayBandWidth;
	}

	public void setRouterGatewayBandWidth(int routerGatewayBandWidth) {
		this.routerGatewayBandWidth = routerGatewayBandWidth;
	}

}
