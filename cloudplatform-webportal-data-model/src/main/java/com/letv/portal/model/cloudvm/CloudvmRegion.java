package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

public class CloudvmRegion extends BaseModel {

	private static final long serialVersionUID = -8757063845109274144L;

	private String code;
	private String displayName;
	
	private String adminEndpoint;
	private String publicEndpoint;
	private String tenantName;//创建用户用户名
	private String projectName;//创建用户项目
	private String tenantPassword;//创建用户密码
	
	private String adminTenantName;//管理员用户名
	private String adminTenantPassword;//管理员密码
	private String adminProjectName;//管理员项目

	public CloudvmRegion() {
	}

	public CloudvmRegion(String code, String displayName) {
		this();
		this.code = code;
		this.displayName = displayName;
	}

	
	public String getAdminTenantName() {
		return adminTenantName;
	}

	public void setAdminTenantName(String adminTenantName) {
		this.adminTenantName = adminTenantName;
	}

	public String getAdminTenantPassword() {
		return adminTenantPassword;
	}

	public void setAdminTenantPassword(String adminTenantPassword) {
		this.adminTenantPassword = adminTenantPassword;
	}

	public String getAdminProjectName() {
		return adminProjectName;
	}

	public void setAdminProjectName(String adminProjectName) {
		this.adminProjectName = adminProjectName;
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTenantPassword() {
		return tenantPassword;
	}

	public void setTenantPassword(String tenantPassword) {
		this.tenantPassword = tenantPassword;
	}

}
