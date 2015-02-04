package com.letv.portal.model.task;

import com.letv.common.model.BaseModel;

public class TaskTemplate extends BaseModel {

	private static final long serialVersionUID = 1343462845762405347L;
	
	private String taskName;
	private int version;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
}
