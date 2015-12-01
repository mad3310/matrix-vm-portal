package com.letv.common.session;

import java.io.Serializable;

public class Session implements Serializable{

	private static final long serialVersionUID = -8540774365214309089L;

	public static final String USER_SESSION_REQUEST_ATTRIBUTE = "userSession";

	private Long userId;
	private Long ucId;
	private String oauthId;

	private String userName;
	private String email;
	private String mobile;

	private boolean isAdmin;

	private String clientId;
	private String clientSecret;

	private Object openStackSession;

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

	public void setUcId(Long ucId) {
		this.ucId = ucId;
	}

	public Long getUcId() {
		return ucId;
	}

	public String getOauthId() {
		return oauthId;
	}

	public void setOauthId(String oauthId) {
		this.oauthId = oauthId;
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
}
