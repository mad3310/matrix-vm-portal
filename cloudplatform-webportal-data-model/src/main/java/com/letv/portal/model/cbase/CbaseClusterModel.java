package com.letv.portal.model.cbase;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.UserModel;

public class CbaseClusterModel extends BaseModel {

	private static final long serialVersionUID = 5248463017990587985L;

	private String cbaseClusterName; // 名称
	private String adminUser;
	private String adminPassword;
	private Integer status; // 状态
	
	private Integer type;

	private Long hclusterId;
	private HclusterModel hcluster;

	private UserModel createUserModel;

	
	
	public String getCbaseClusterName() {
		return cbaseClusterName;
	}

	public void setCbaseClusterName(String cbaseClusterName) {
		this.cbaseClusterName = cbaseClusterName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public UserModel getCreateUserModel() {
		return createUserModel;
	}

	public void setCreateUserModel(UserModel createUserModel) {
		this.createUserModel = createUserModel;
	}

	public Long getHclusterId() {
		return hclusterId;
	}

	public void setHclusterId(Long hclusterId) {
		this.hclusterId = hclusterId;
	}

	public HclusterModel getHcluster() {
		return hcluster;
	}

	public void setHcluster(HclusterModel hcluster) {
		this.hcluster = hcluster;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
