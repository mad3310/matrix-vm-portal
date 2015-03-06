package com.letv.portal.model.task.test.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;

@Service("testTask1Service")
public class TestTask1ServiceImpl implements IBaseTaskService{

	private final static Logger logger = LoggerFactory.getLogger(TestTask1ServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) {
		
		logger.debug("testTask1Service's execute begin----------------------------------------");
		
		TaskResult tr = new TaskResult();
		tr.setSuccess(true);
		tr.setParams(params);
		return tr;
	}

	@Override
	public void rollBack(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callBack(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

}
