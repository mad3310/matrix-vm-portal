package com.letv.portal.model.task.test.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.exception.TaskExecuteException;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.BaseTask4RDSServiceImpl;
import com.letv.portal.model.task.service.IBaseTaskService;

@Service("testTask2Service")
public class TestTask2ServiceImpl extends BaseTask4RDSServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TestTask2ServiceImpl.class);

	@Override
	public TaskResult execute(Map<String, Object> params) {
		
		logger.debug("testTask2Service's execute begin----------------------------------------");
		
		TaskResult tr = new TaskResult();
		if(0 == (Integer)params.get("id")) {
			tr.setSuccess(false);
			tr.setResult("id is wrong");
		} else {
			tr.setSuccess(true);
		}
		if(params.get("retry") != null && (Boolean)params.get("retry")) {
			params.put("id", (Integer)params.get("id")+1);
		}
		tr.setParams(params);
		return tr;
	}

	@Override
	public void rollBack(TaskResult tr) {
		logger.debug("testTask2Service's rollBack begin----------------------------------------");
	}

	@Override
	public void callBack(TaskResult tr) {
		logger.debug("testTask2Service's success! callBack begin----------------------------------------");
	}

}
