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
}
