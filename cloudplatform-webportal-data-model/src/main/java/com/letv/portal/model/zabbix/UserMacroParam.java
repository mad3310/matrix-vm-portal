package com.letv.portal.model.zabbix;


public class UserMacroParam {
    private String hostid;
    private String macro;
    private String value;

    public UserMacroParam(String hostid,String value){
    	this.macro = "{$PROXY_IP}";
    	this.hostid = hostid;
    	this.value = value;
    }
   
	public String getHostid() {
		return hostid;
	}

	public void setHostid(String hostid) {
		this.hostid = hostid;
	}

	public String getMacro() {
		return macro;
	}
	public void setMacro(String macro) {
		this.macro = macro;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
    
}
