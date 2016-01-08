package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskAddVolumeService")
public class TaskAddVolumeServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskAddVolumeServiceImpl.class);
	
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		VMCreateConf2 vmCreateConf = JSONObject.parseObject((String)params.get("vmCreateConf"), VMCreateConf2.class);
		String ret = null;
		if(vmCreateConf.getVolumeSize() > 0) {
			ret = storageService.addVolume(params);
			logger.info("云主机创建完成后添加硬盘，结果：{}", ret);
		} else {
			ret = "success";
		}
		
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
