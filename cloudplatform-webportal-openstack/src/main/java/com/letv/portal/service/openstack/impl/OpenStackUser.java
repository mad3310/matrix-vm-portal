package com.letv.portal.service.openstack.impl;

public class OpenStackUser {

	private String userId;
	private String password;
	private boolean firstLogin;
	private boolean internalUser;

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

	public boolean getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public boolean getInternalUser() {
		return internalUser;
	}

	public void setInternalUser(boolean internalUser) {
		this.internalUser = internalUser;
	}

}
