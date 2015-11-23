package com.letv.portal.model.task;

import com.letv.common.model.BaseModel;

public class TemplateTask extends BaseModel {

	private static final long serialVersionUID = 1343462845762405347L;
	
	private String name;
	private String descn;
	private int version;
	private String taskType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
}
