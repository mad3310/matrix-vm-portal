package com.letv.portal.model.task.service;

import java.util.Map;

import com.letv.portal.model.task.TaskResult;

public interface IBaseTaskService {

	public TaskResult execute(Map<String,Object> params) throws Exception;

	public void rollBack(TaskResult tr);
	
	public void callBack(TaskResult tr);
	
}
