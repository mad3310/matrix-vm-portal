package com.letv.common.session;

import java.io.Serializable;

public class Session implements Serializable{
	
	private static final long serialVersionUID = -8540774365214309089L;
	
	public static final String USER_SESSION_REQUEST_ATTRIBUTE = "userSession";
	
	/**
	 * from http://uc.letvcloud.com/user/userInfo.do?sessionId=7aa301f4-669f-497b-b760-1a012a61d78e
	 * {
		"contacts":"刘浩",
		"countryCode":"86",
		"createdTime":"2015-09-13 16:23:15",
		"email":"liuhao1@letv.com",
		"id":400061,
		"isOld":1,
		"lastUpdateTime":"2015-09-13 16:23:15",
		"mobile":"18510086398",
		"mobileStatus":1,
		"siteDomain":"",
		"siteName":"",
		"siteType":3,
		"userKey":"092e87297c3b21d30ff8b4afdc0d9547",
		"userStatus":1,
		"userType":2,
		"userUnique":"2qezyczenh"
	}
	 */
	
	private Long userId;
	private String userName;
	private boolean passwordExpired = false;
	private String clientId;
	private String clientSecret;
	private String email;
	private boolean isAdmin;
	private Object openStackSession;
	
	private String mobile;
	
	private Object userVo;
	
	public Session(){}
	
	public Session(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isPasswordExpired() {
		return passwordExpired;
	}

	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Object getOpenStackSession() {
		return openStackSession;
	}

	public void setOpenStackSession(Object openStackSession) {
		this.openStackSession = openStackSession;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Object getUserVo() {
		return userVo;
	}

	public void setUserVo(Object userVo) {
		this.userVo = userVo;
	}
	
}
