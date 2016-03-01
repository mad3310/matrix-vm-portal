package com.letv.portal.model.zabbix;

import java.util.ArrayList;
import java.util.List;

public class HostParam {
    private String  host;

    private List<Object> groups = new ArrayList<Object>();
    private List<Object> templates = new ArrayList<Object>();
    private List<InterfacesModel> interfaces = new ArrayList<InterfacesModel>();
    private InventoryModel inventory = new InventoryModel();
    private String proxy_hostid;
    
    //添加构造函数，传入模板id,groupid,proxy_hostid 20150116 by liuhao
    public HostParam(String templateId,String groupid,String proxy_hostid){
    	this.proxy_hostid = proxy_hostid;
    	TemplatesModel templatesModel = new TemplatesModel(templateId);
    	String[] gids = groupid.split(",");
    	for (String gid : gids) {
    		GroupsModel groupsModel = new GroupsModel(gid);
    		groups.add(groupsModel);
		}
    	templates.add(templatesModel);
    }
    
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public List<Object> getGroups() {
		return groups;
	}
	public void setGroups(List<Object> groups) {
		this.groups = groups;
	}
	public List<Object> getTemplates() {
		return templates;
	}
	public void setTemplates(List<Object> templates) {
		this.templates = templates;
	}

	public List<InterfacesModel> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<InterfacesModel> list) {
		this.interfaces = list;
	}

	public InventoryModel getInventory() {
		return inventory;
	}

	public void setInventory(InventoryModel inventory) {
		this.inventory = inventory;
	}

	public String getProxy_hostid() {
		return proxy_hostid;
	}

	public void setProxy_hostid(String proxy_hostid) {
		this.proxy_hostid = proxy_hostid;
	}
    
}
