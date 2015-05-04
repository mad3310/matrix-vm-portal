package com.letv.portal.model.log;

import java.util.List;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.UserModel;

public class LogServer extends BaseModel {
	
	private static final long serialVersionUID = -7999485658204466572L;

	private String logName;
	
	private Long logClusterId;
	private Long hclusterId;

	private String ip;
	private Integer status;
	private String descn;
	private String type;
	private String accessUrl;
	
	private HclusterModel hcluster;
	private LogCluster logCluster;
	private UserModel createUserModel;
	private List<LogContainer> logContainers;
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	public Long getLogClusterId() {
		return logClusterId;
	}
	public void setLogClusterId(Long logClusterId) {
		this.logClusterId = logClusterId;
	}
	public Long getHclusterId() {
		return hclusterId;
	}
	public void setHclusterId(Long hclusterId) {
		this.hclusterId = hclusterId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccessUrl() {
		return accessUrl;
	}
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
	public HclusterModel getHcluster() {
		return hcluster;
	}
	public void setHcluster(HclusterModel hcluster) {
		this.hcluster = hcluster;
	}
	public LogCluster getLogCluster() {
		return logCluster;
	}
	public void setLogCluster(LogCluster logCluster) {
		this.logCluster = logCluster;
	}
	public UserModel getCreateUserModel() {
		return createUserModel;
	}
	public void setCreateUserModel(UserModel createUserModel) {
		this.createUserModel = createUserModel;
	}
	public List<LogContainer> getLogContainers() {
		return logContainers;
	}
	public void setLogContainers(List<LogContainer> logContainers) {
		this.logContainers = logContainers;
	}
	
}
