package com.letv.portal.model;

import java.util.List;

import com.letv.common.model.BaseModel;

/**
 * 
 * @author liyunhui
 *
 */
public class CbaseClusterModel extends BaseModel {

	private static final long serialVersionUID = -1726561625544003334L;

	private String hCbaseClusterName; // 集群名称
	private Integer status; // 状态:
	private Integer descn; // 描述:
	private String hCbaseClusterAlias;// 别名
	private List<HCbaseNodeModel> hCbaseNodeList;  // 节点列表

	public String gethCbaseClusterName() {
		return hCbaseClusterName;
	}

	public void sethCbaseClusterName(String hCbaseClusterName) {
		this.hCbaseClusterName = hCbaseClusterName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDescn() {
		return descn;
	}

	public void setDescn(Integer descn) {
		this.descn = descn;
	}

	public String gethCbaseClusterAlias() {
		return hCbaseClusterAlias;
	}

	public void sethCbaseClusterAlias(String hCbaseClusterAlias) {
		this.hCbaseClusterAlias = hCbaseClusterAlias;
	}
	
	public List<HCbaseNodeModel> gethCbaseNodeList() {
		return hCbaseNodeList;
	}

	public void sethCbaseNodeList(List<HCbaseNodeModel> hCbaseNodeList) {
		this.hCbaseNodeList = hCbaseNodeList;
	}
	

}
