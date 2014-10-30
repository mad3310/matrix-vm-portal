package com.letv.portal.model;


public class InterfacesModel {	
	private int type; 
	private int main; 
	private int useip; 
	private String ip; 
	private String dns; 
	private String port;
	
	public InterfacesModel(){
		this.type=1;
		this.main=1;
		this.useip =1;
		this.dns = "";
		this.port="10050";
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMain() {
		return main;
	}
	public void setMain(int main) {
		this.main = main;
	}
	public int getUseip() {
		return useip;
	}
	public void setUseip(int useip) {
		this.useip = useip;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDns() {
		return dns;
	}
	public void setDns(String dns) {
		this.dns = dns;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}
