package com.letv.portal.model.task.service;

import java.util.Map;

import com.letv.portal.model.task.TaskResult;

public interface IBaseTaskService {

	public TaskResult execute(Map<String,Object> params);

	public void rollBack(Map<String, Object> params);
	
	public void callBack(Map<String, Object> params);
}
