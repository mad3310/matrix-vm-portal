package com.letv.portal.model.gce;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.UserModel;

public class GceServer extends BaseModel {
	
	private static final long serialVersionUID = -7999485658204466572L;

	private String gceName;
	
	private Long gceClusterId;
	private Long hclusterId;

	private String gceImageName;
	
	public String getGceName() {
		return gceName;
	}
	public void setGceName(String gceName) {
		this.gceName = gceName;
	}
	public Long getGceClusterId() {
		return gceClusterId;
	}
	public void setGceClusterId(Long gceClusterId) {
		this.gceClusterId = gceClusterId;
	}
	public Long getHclusterId() {
		return hclusterId;
	}
	public void setHclusterId(Long hclusterId) {
		this.hclusterId = hclusterId;
	}
	public String getGceImageName() {
		return gceImageName;
	}
	public void setGceImageName(String gceImageName) {
		this.gceImageName = gceImageName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPortForward() {
		return portForward;
	}
	public void setPortForward(String portForward) {
		this.portForward = portForward;
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
	public GceCluster getGceCluster() {
		return gceCluster;
	}
	public void setGceCluster(GceCluster gceCluster) {
		this.gceCluster = gceCluster;
	}
	public UserModel getCreateUserModel() {
		return createUserModel;
	}
	public void setCreateUserModel(UserModel createUserModel) {
		this.createUserModel = createUserModel;
	}
	private String ip;
	private String portForward;
	private Integer status;
	private String descn;
	
	private HclusterModel hcluster;
	private GceCluster gceCluster;
	private UserModel createUserModel;
}
