package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskNotifyCallerService")
public class TaskNotifyCallerServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskNotifyCallerServiceImpl.class);
	
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		List<JSONObject> vmCreateContexts = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateContexts")), List.class);
		
		
		//TODO 调用创建回调接口
		String ret = "success";
        
		
		logger.info("创建云主机，结果：{}", ret);
		
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
