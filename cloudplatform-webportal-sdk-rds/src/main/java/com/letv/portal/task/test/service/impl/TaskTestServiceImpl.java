package com.letv.portal.task.test.service.impl;

import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;

@Service("taskTestService")
public class TaskTestServiceImpl extends BaseTask4TestServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskTestServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;
		int i = new Random().nextInt(10);
		Thread.sleep(i*1000);
		tr.setParams(params);
		tr.setResult("sleep"+i+"s,test SUCCESS!");
		return tr;
	}
	
	@Override
	public void callBack(TaskResult tr) {
		super.rollBack(tr);
	}
	
}
