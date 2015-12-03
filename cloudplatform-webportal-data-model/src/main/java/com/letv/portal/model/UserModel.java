package com.letv.portal.model;

import com.letv.common.model.BaseModel;

public class UserModel extends BaseModel{

	private static final long serialVersionUID = 5336795056773086076L;

	private Long ucId;
	private String oauthId;

	private String email;
	private String userName;
	private String mobile;

	private boolean isAdmin;

	public Long getUcId() {
		return ucId;
	}

	public void setUcId(Long ucId) {
		this.ucId = ucId;
	}

	public String getOauthId() {
		return oauthId;
	}

	public void setOauthId(String oauthId) {
		this.oauthId = oauthId;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
