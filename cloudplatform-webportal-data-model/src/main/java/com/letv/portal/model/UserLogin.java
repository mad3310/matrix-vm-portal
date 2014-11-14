package com.letv.portal.model;

public class UserLogin {
	
	private String userName;
	
	private String userPassword;
	
	private String loginIp;
	
	public UserLogin(){}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginIp() {
		return loginIp;
	}
}
