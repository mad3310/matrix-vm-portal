package com.letv.portal.model.task;

import java.util.Date;

import com.letv.common.model.BaseModel;

public class TaskChain extends BaseModel {
	
	private static final long serialVersionUID = -1095818484708108162L;
	
	private Long taskId;
	private Long taskDetailId;
	private int  executeOrder;
	
	private Long chainIndexId;
	private TaskExecuteStatus status;
	
	private Date startTime;
	private Date endTime;
	
	private String result;
	
	private String params; //json params
	
	public Long getChainIndexId() {
		return chainIndexId;
	}
	public void setChainIndexId(Long chainIndexId) {
		this.chainIndexId = chainIndexId;
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
	public TaskExecuteStatus getStatus() {
		return status;
	}
	public void setStatus(TaskExecuteStatus status) {
		this.status = status;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
