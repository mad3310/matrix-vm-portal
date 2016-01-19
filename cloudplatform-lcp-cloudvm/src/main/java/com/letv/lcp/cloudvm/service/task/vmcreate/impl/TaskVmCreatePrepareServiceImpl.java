package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskVmCreatePrepareService")
public class TaskVmCreatePrepareServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskVmCreatePrepareServiceImpl.class);
	
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		String ret = computeService.getVmCreatePrepare(params);
		logger.info("创建云主机参数及前提工作准备，执行结果：{}", ret);
		
		tr.setResult(ret);
		if("success".equals(ret) || "true".equals(ret)) {
			tr.setSuccess(true);
			tr.setParams(params);
		} else {
			tr.setSuccess(false);
		}
		
		return tr;
	}
	
}
