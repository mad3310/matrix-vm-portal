package com.letv.portal.model.task;

import com.letv.common.model.BaseModel;

public class TemplateTaskChain extends BaseModel {

	private static final long serialVersionUID = -3782438981595282255L;
	
	private Long taskId;
	private Long taskDetailId;
	private int  executeOrder;
	private int  retry;
	
	public int getRetry() {
		return retry;
	}
	public void setRetry(int retry) {
		this.retry = retry;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getTaskDetailId() {
		return taskDetailId;
	}
	public void setTaskDetailId(Long taskDetailId) {
		this.taskDetailId = taskDetailId;
	}
	public int getExecuteOrder() {
		return executeOrder;
	}
	public void setExecuteOrder(int executeOrder) {
		this.executeOrder = executeOrder;
	}
	
	
}
