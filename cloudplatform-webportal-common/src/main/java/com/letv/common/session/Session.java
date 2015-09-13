package com.letv.common.session;

import java.io.Serializable;

public class Session implements Serializable{
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -8540774365214309089L;
	
	public static final String USER_SESSION_REQUEST_ATTRIBUTE = "userSession";

	private Long userId;
	
	private String userName;
	
	private boolean passwordExpired = false;
	
	private String clientId;
	private String clientSecret;
	private String email;
	private Long ucId;
	
	private boolean isAdmin;
	
	private Object openStackSession;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserInfoId(Long userId)
	{
		this.userId = userId;
	}

	public boolean isPasswordExpired() {
		return passwordExpired;
	}
	
	public void setPasswordExpired(boolean passwordExpired)
	{
		this.passwordExpired = passwordExpired;
	}

	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
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

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public void setOpenStackSession(Object openStackSession) {
		this.openStackSession = openStackSession;
	}
	
	public Object getOpenStackSession() {
		return openStackSession;
	}

	public Long getUcId() {
		return ucId;
	}

	public void setUcId(Long ucId) {
		this.ucId = ucId;
	}
	
	
}
