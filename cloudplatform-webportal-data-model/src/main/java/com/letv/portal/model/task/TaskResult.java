package com.letv.portal.model.task;

import com.letv.common.exception.TaskExecuteException;
import com.letv.common.model.BaseModel;

public class TaskResult extends BaseModel {

	private static final long serialVersionUID = 6232740793479016919L;

	private boolean isSuccess;
	
	private Object params; //json params
	
	private TaskExecuteException taskExecuteException; //if field.

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public TaskExecuteException getTaskExecuteException() {
		return taskExecuteException;
	}

	public void setTaskExecuteException(TaskExecuteException taskExecuteException) {
		this.taskExecuteException = taskExecuteException;
	}

}
