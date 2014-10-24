package com.letv.portal.model;

import java.util.List;

public class ZabbixPushDeleteModel {
	private String jsonrpc;
	private String method;
	private List<String> params;
	private String auth;
	private int id;
	public ZabbixPushDeleteModel(){
		this.jsonrpc="2.0";
		this.method="host.delete";
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
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
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
