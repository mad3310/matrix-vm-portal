package com.letv.portal.model.zabbix;

public class InventoryModel {
	private String macaddress_a;
	private String macaddress_b;
	public InventoryModel(){
		this.macaddress_a="01234";
		this.macaddress_b="56768";
	};
	public String getMacaddress_a() {
		return macaddress_a;
	}

	public void setMacaddress_a(String macaddress_a) {
		this.macaddress_a = macaddress_a;
	}
	public String getMacaddress_b() {
		return macaddress_b;
	}
	public void setMacaddress_b(String macaddress_b) {
		this.macaddress_b = macaddress_b;
	} 
	
}
