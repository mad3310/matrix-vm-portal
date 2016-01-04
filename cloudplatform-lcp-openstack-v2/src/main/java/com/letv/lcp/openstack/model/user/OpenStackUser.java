package com.letv.lcp.openstack.model.user;

import java.io.Serializable;

public class OpenStackUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937930998427002260L;

	public final OpenStackTenant tenant;

//	private long userVoUserId;
//	private String userId;
//	private String password;
//	private String email;
	private String userName;
//	private boolean firstLogin;
	private boolean internalUser;
	private String tenantId;
	
	public OpenStackUser(OpenStackTenant tenant){
		this.tenant = tenant;
	}

		public long getUserVoUserId() {
		return tenant.userId;
	}
//
//	public void setUserVoUserId(long userVoUserId) {
//		this.userVoUserId = userVoUserId;
//	}
//
	public String getUserId() {
		return tenant.tenantName;
	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//
	public String getPassword() {
		return tenant.password;
	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

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
		return tenant.email;
	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
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
