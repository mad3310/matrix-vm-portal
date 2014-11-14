package com.letv.portal.model;
 
public class ZabbixPushModel {
	private String jsonrpc;
	private String method;
	private ZabbixParam params;
	private String auth;
	private int id;
	public ZabbixPushModel(){
		this.jsonrpc="2.0";
		this.method="host.create";
		this.id=1;
	}

	public String getJsonrpc() {
		return jsonrpc;
	}
	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public ZabbixParam getParams() {
		return params;
	}
	public void setParams(ZabbixParam params) {
		this.params = params;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
