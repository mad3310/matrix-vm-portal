package com.letv.portal.model.task.test.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.BaseTask4RDSServiceImpl;
import com.letv.portal.model.task.service.IBaseTaskService;

@Service("testTask1Service")
public class TestTask1ServiceImpl extends BaseTask4RDSServiceImpl implements IBaseTaskService{

	private final static Logger logger = LoggerFactory.getLogger(TestTask1ServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		
		logger.debug("testTask1Service's execute begin----------------------------------------");
		
		TaskResult tr = new TaskResult();
		tr.setSuccess(true);
		tr.setParams(params);
		return tr;
	}

}
