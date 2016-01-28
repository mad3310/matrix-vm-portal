package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.compute.IComputeDbService;
import com.letv.portal.enumeration.cloudvm.CloudvmServerStatusEnum;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskCreateVmService")
public class TaskCreateVmServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskCreateVmServiceImpl.class);
	
	@Autowired
	private IComputeDbService computeDbService;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		String ret = computeService.createVm(Long.parseLong((String)params.get("userId")), vmCreateConf, null, null, params);
		logger.info("创建云主机，结果：{}", ret);
		
		tr.setResult(ret);
		if("success".equals(ret) || "true".equals(ret)) {
			Long createUser = params.get("createUser")==null?Long.parseLong((String)params.get("userId")):
				Long.parseLong((String)params.get("createUser"));
			//更新数据库
			List<VmCreateContext> vmCreateContexts = (List<VmCreateContext>) params.get("vmCreateContexts");
			for (VmCreateContext vmCreateContext : vmCreateContexts) {
				this.computeDbService.updateServer(vmCreateContext.getServerDbId(), createUser, 
						vmCreateContext.getCpu(), vmCreateContext.getRam(), vmCreateContext.getServerInstanceId(), 
						CloudvmServerStatusEnum.ACTIVE, vmCreateContext.getPrivateIp());
			}
			params.put("vmCreateContexts", JSONObject.toJSON(vmCreateContexts));
			tr.setSuccess(true);
			tr.setParams(params);
		} else {
			tr.setSuccess(false);
		}
		
		return tr;
	}
	
}
