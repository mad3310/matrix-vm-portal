package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

public class CloudvmRegion extends BaseModel {

	private static final long serialVersionUID = -8757063845109274144L;

	private String code;
	private String displayName;
	
	private String adminEndpoint;
	private String publicEndpoint;
	private String tenantName;
	private String tenantEmail;
	private String tenantPassword;

	public CloudvmRegion() {
	}

	public CloudvmRegion(String code, String displayName) {
		this();
		this.code = code;
		this.displayName = displayName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAdminEndpoint() {
		return adminEndpoint;
	}

	public void setAdminEndpoint(String adminEndpoint) {
		this.adminEndpoint = adminEndpoint;
	}

	public String getPublicEndpoint() {
		return publicEndpoint;
	}

	public void setPublicEndpoint(String publicEndpoint) {
		this.publicEndpoint = publicEndpoint;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenantEmail() {
		return tenantEmail;
	}

	public void setTenantEmail(String tenantEmail) {
		this.tenantEmail = tenantEmail;
	}

	public String getTenantPassword() {
		return tenantPassword;
	}

	public void setTenantPassword(String tenantPassword) {
		this.tenantPassword = tenantPassword;
	}

}
