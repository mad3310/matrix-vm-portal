package com.letv.portal.model.cbase;

import java.util.List;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.UserModel;

public class CbaseBucketMode extends BaseModel {

	private static final long serialVersionUID = -3992964949274238740L;

	private Long cbaseClusterId; // 所属cluster
	private String bucketName; // bucket名称
	private Integer status; // 状态

	private CbaseClusterMode cbaseCluster; // 所属cluster

	private String descn; // 描述
	private Integer bucketType;   //bucket类型:    0:持久化   1:非持久化

	private UserModel user;

	private Long hclusterId;
	private HclusterModel hcluster;

	private List<CbaseContainerMode> containers;

	public Long getHclusterId() {
		return hclusterId;
	}

	public void setHclusterId(Long hclusterId) {
		this.hclusterId = hclusterId;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public List<CbaseContainerMode> getContainers() {
		return containers;
	}

	public void setContainers(List<CbaseContainerMode> containers) {
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

	public CbaseClusterMode getCbaseCluster() {
		return cbaseCluster;
	}

	public void setCbaseCluster(CbaseClusterMode cbaseCluster) {
		this.cbaseCluster = cbaseCluster;
	}

	public Integer getBucketType() {
		return bucketType;
	}

	public void setBucketType(Integer bucketType) {
		this.bucketType = bucketType;
	}
}
