package com.letv.portal.model.zabbix;

public class GroupsModel {
	private String groupid;
	public GroupsModel(){
		this.groupid="9";
	}
	public GroupsModel(String groupid){
		this.groupid = groupid;
	}
	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	  
}
