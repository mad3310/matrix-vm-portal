package com.letv.portal.model.cbase;

import java.util.List;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.UserModel;

public class CbaseBucketModel extends BaseModel {

	private static final long serialVersionUID = -3992964949274238740L;

	private Long cbaseClusterId; // 所属cluster
	private CbaseClusterModel cbaseCluster; // 所属cluster
	
	private Long hclusterId;
	private HclusterModel hcluster;
	
	private String bucketName; // bucket名称

	private Integer status; // 状态
	private String descn; // 描述
	private Integer bucketType;   //bucket类型:    0:持久化   1:非持久化
	
	private String ramQuotaMB;  //大小
	private String authType;    // 认证类型
	
	private UserModel createUserModel;

	

	private List<CbaseContainerModel> containers;

	public Long getHclusterId() {
		return hclusterId;
	}

	public void setHclusterId(Long hclusterId) {
		this.hclusterId = hclusterId;
	}

	public List<CbaseContainerModel> getContainers() {
		return containers;
	}

	public void setContainers(List<CbaseContainerModel> containers) {
		this.containers = containers;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public HclusterModel getHcluster() {
		return hcluster;
	}

	public void setHcluster(HclusterModel hcluster) {
		this.hcluster = hcluster;
	}

	public Long getCbaseClusterId() {
		return cbaseClusterId;
	}

	public void setCbaseClusterId(Long cbaseClusterId) {
		this.cbaseClusterId = cbaseClusterId;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public CbaseClusterModel getCbaseCluster() {
		return cbaseCluster;
	}

	public void setCbaseCluster(CbaseClusterModel cbaseCluster) {
		this.cbaseCluster = cbaseCluster;
	}

	public Integer getBucketType() {
		return bucketType;
	}

	public void setBucketType(Integer bucketType) {
		this.bucketType = bucketType;
	}

	public String getRamQuotaMB() {
		return ramQuotaMB;
	}

	public void setRamQuotaMB(String ramQuotaMB) {
		this.ramQuotaMB = ramQuotaMB;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public UserModel getCreateUserModel() {
		return createUserModel;
	}

	public void setCreateUserModel(UserModel createUserModel) {
		this.createUserModel = createUserModel;
	}
}
