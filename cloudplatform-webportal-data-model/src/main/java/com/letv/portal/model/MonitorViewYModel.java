package com.letv.portal.model;

import java.util.ArrayList;
import java.util.List;

public class MonitorViewYModel {
	private String type;
	private String name;
	private List<Integer> data =new ArrayList<Integer>();
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
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}
    

   
	
	
	
}
