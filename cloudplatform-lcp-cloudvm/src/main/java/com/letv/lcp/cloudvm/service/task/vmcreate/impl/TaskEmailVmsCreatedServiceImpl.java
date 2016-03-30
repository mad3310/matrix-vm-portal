package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskEmailVmsCreatedService")
public class TaskEmailVmsCreatedServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskEmailVmsCreatedServiceImpl.class);
	
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		if(params.get("auditUser")==null || (Boolean)params.get("auditUser")==false) {
			String ret = computeService.emailVmsCreated(params);
			logger.info("创建云主机完成后发送邮件，结果：{}", ret);
			
			tr.setResult(ret);
			if("success".equals(ret) || "true".equals(ret)) {
				tr.setSuccess(true);
				tr.setParams(params);
			} else {
				tr.setSuccess(false);
			}
		} else {
			//跳过该步骤
			tr.setResult("skip");
		}
		
		return tr;
	}
	
}
