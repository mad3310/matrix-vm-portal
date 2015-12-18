package com.letv.portal.service.task;

import com.letv.common.result.ApiResultObject;
import com.letv.portal.model.task.TaskResult;

import java.util.Map;

public interface IBaseTaskService {

	public TaskResult execute(Map<String, Object> params) throws Exception;

	public void rollBack(TaskResult tr);
	
	public void callBack(TaskResult tr);

	public void beforExecute(Map<String, Object> params);

	public TaskResult analyzeRestServiceResult(ApiResultObject resultObject);
	
}
