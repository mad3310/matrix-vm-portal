package com.letv.portal.model;

import java.util.List;

public class MonitorViewYModel {
	private String type;
	private String name;
	private List<List<Object>>data;
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
	public List<List<Object>> getData() {
		return data;
	}
	public void setData(List<List<Object>> data) {
		this.data = data;
	}
}
