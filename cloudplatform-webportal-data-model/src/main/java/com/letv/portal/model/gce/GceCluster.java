package com.letv.portal.model.gce;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.UserModel;

public class GceCluster extends BaseModel {
	
	private static final long serialVersionUID = -8757063845109274144L;

	private String clusterName; //名称
	private String adminUser;
	private String adminPassword;
	private Integer status; //状态
	
	private String sstPwd; //gbalancer监控密码
	private Integer type; //手动创建、自动创建
	
	private Long hclusterId;
	private HclusterModel hcluster;
	
	private UserModel createUserModel;
	
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
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
	
	public String getSstPwd() {
		return sstPwd;
	}
	public void setSstPwd(String sstPwd) {
		this.sstPwd = sstPwd;
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
