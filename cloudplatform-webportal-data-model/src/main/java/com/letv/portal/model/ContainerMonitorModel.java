package com.letv.portal.model;

import java.util.List;

public class ContainerMonitorModel {
	private Long id; 
	private String mclusterName; 
	private String hclusterName;	  
	private String ip; 
	private String status; 
	private List<NodeMonitorModel> nodeMoList;
	private List<DbMonitorModel> dbMoList;
	private List<ClusterMonitorModel> clMoList;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMclusterName() {
		return mclusterName;
	}
	public void setMclusterName(String mclusterName) {
		this.mclusterName = mclusterName;
	}
	public String getHclusterName() {
		return hclusterName;
	}
	public void setHclusterName(String hclusterName) {
		this.hclusterName = hclusterName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<NodeMonitorModel> getNodeMoList() {
		return nodeMoList;
	}
	public void setNodeMoList(List<NodeMonitorModel> nodeMoList) {
		this.nodeMoList = nodeMoList;
	}
	public List<DbMonitorModel> getDbMoList() {
		return dbMoList;
	}
	public void setDbMoList(List<DbMonitorModel> dbMoList) {
		this.dbMoList = dbMoList;
	}
	public List<ClusterMonitorModel> getClMoList() {
		return clMoList;
	}
	public void setClMoList(List<ClusterMonitorModel> clMoList) {
		this.clMoList = clMoList;
	}
	
	
	
}
