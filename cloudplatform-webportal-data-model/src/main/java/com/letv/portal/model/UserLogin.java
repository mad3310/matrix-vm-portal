package com.letv.portal.model;

public class UserLogin {
	
	private String userName;
	
	private String password;
	
	private String loginIp;
	
	public UserLogin(){}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginIp() {
		return loginIp;
	}
}
