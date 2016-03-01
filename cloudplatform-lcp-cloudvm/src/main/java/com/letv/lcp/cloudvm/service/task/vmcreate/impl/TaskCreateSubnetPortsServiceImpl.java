package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskCreateSubnetPortsService")
public class TaskCreateSubnetPortsServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskCreateSubnetPortsServiceImpl.class);
	
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		if (StringUtils.isNotEmpty(vmCreateConf.getPrivateSubnetId())) {
			String ret = networkService.createSubnetPorts(params);
			logger.info("创建子网，结果：{}", ret);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void rollBack(TaskResult tr) {
		Map<String, Object> params = (Map<String, Object>) tr.getParams();
		networkService.rollBackSubnetPortsWithCreateVmFail(params);
	}
	
	
}
