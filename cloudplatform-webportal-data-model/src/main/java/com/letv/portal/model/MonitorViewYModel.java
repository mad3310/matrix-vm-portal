package com.letv.portal.model;

import java.util.ArrayList;
import java.util.List;

public class MonitorViewYModel {
	private String type;
	private String name;
	private List<String> data =new ArrayList<String>();
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}

   
	
	
	
}
