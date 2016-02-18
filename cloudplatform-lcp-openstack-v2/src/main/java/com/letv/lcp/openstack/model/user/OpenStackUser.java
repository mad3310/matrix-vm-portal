package com.letv.lcp.openstack.model.user;

import java.io.Serializable;

public class OpenStackUser implements Serializable {

	private static final long serialVersionUID = -5937930998427002260L;

	public final OpenStackTenant tenant;//openstack租户（创建用户）

	private String userName;//申请人用户名
	private boolean internalUser;//是否为内部用户
	private Long applyUserId;//申请人id
	

	public OpenStackUser(OpenStackTenant tenant) {
		this.tenant = tenant;
	}

	public Long getApplyUserId() {
		return applyUserId;
	}


	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}


	public long getTenantUserId() {
		return tenant.userId;
	}

	public String getTenantUserName() {
		return tenant.tenantName;
	}

	public String getTenantUserPassword() {
		return tenant.password;
	}

	public boolean getInternalUser() {
		return internalUser;
	}

	public void setInternalUser(boolean internalUser) {
		this.internalUser = internalUser;
	}

	public String getTenantEmail() {
		return tenant.email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOpenStackTenantId() {
		return tenant.openStackTenantId;
	}

	public void setOpenStackTenantId(String tenantId) {
		tenant.openStackTenantId = tenantId;
	}

}
