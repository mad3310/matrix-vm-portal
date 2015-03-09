package com.letv.portal.model.slb;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.SlbStatus;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.UserModel;

public class SlbServer extends BaseModel {
	
	private static final long serialVersionUID = -91201537637391429L;

	private String slbName;
	
	private Long slbClusterId;
	private Long hclusterId;

	private String ip;
	private Integer status;
	private String descn;
	
	private HclusterModel hcluster;
	private SlbCluster slbCluster;
	private UserModel createUserModel;
	
	public String getSlbName() {
		return slbName;
	}
	public void setSlbName(String slbName) {
		this.slbName = slbName;
	}
	public Long getSlbClusterId() {
		return slbClusterId;
	}
	public void setSlbClusterId(Long slbClusterId) {
		this.slbClusterId = slbClusterId;
	}
	public Long getHclusterId() {
		return hclusterId;
	}
	public void setHclusterId(Long hclusterId) {
		this.hclusterId = hclusterId;
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
	public SlbCluster getSlbCluster() {
		return slbCluster;
	}
	public void setSlbCluster(SlbCluster slbCluster) {
		this.slbCluster = slbCluster;
	}
	public UserModel getCreateUserModel() {
		return createUserModel;
	}
	public void setCreateUserModel(UserModel createUserModel) {
		this.createUserModel = createUserModel;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
