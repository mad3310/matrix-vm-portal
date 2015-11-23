package com.letv.portal.service.openstack.impl;

import java.io.Serializable;

public class OpenStackUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937930998427002260L;
	private long userVoUserId;
	private String userId;
	private String password;
	private String email;
	private String userName;
//	private boolean firstLogin;
	private boolean internalUser;
	private String tenantId;
	
	public OpenStackUser(){
	}

	public long getUserVoUserId() {
		return userVoUserId;
	}

	public void setUserVoUserId(long userVoUserId) {
		this.userVoUserId = userVoUserId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public boolean getFirstLogin() {
//		return firstLogin;
//	}
//
//	public void setFirstLogin(boolean firstLogin) {
//		this.firstLogin = firstLogin;
//	}

	public boolean getInternalUser() {
		return internalUser;
	}

	public void setInternalUser(boolean internalUser) {
		this.internalUser = internalUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
