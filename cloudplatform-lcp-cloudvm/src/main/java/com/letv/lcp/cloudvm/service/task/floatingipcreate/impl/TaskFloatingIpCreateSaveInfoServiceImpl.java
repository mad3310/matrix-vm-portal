package com.letv.lcp.cloudvm.service.task.floatingipcreate.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.letv.lcp.cloudvm.model.network.FloatingIpCreateConf;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.network.INetworkDbService;
import com.letv.portal.enumeration.cloudvm.CloudvmNetworkStatusEnum;
import com.letv.portal.model.cloudvm.lcp.CloudvmPublicNetworkModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskFloatingIpCreateSaveInfoService")
public class TaskFloatingIpCreateSaveInfoServiceImpl extends BaseTask4FloatingIpCreateServiceImpl implements IBaseTaskService{
	
	@Autowired
	private INetworkDbService networkDbService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskFloatingIpCreateSaveInfoServiceImpl.class);
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		initParams(params);
		FloatingIpCreateConf floatingIpCreateConf = JSONObject.parseObject((String)params.get("floatingIpCreateConf"), FloatingIpCreateConf.class);
		
        params.put("floatingIpCreateConf", JSONObject.toJSON(floatingIpCreateConf));
		Long userId = Long.parseLong((String)params.get("userId"));
		List<VmCreateContext> vmCreateContexts = (List<VmCreateContext>) params.get("vmCreateContexts");
		
		saveInfo(userId, floatingIpCreateConf, vmCreateContexts);
		params.put("vmCreateContexts", JSONObject.toJSON(vmCreateContexts));
		
		logger.info("创建云硬盘参数保存到数据库!");
		tr.setResult("success");
		tr.setSuccess(true);
		tr.setParams(params);
		return tr;
	}
	
	
	private void saveInfo(Long userId, FloatingIpCreateConf floatingIpCreateConf, List<VmCreateContext> vmCreateContexts) {
		for (VmCreateContext vmCreateContext : vmCreateContexts) {
			CloudvmPublicNetworkModel network = this.networkDbService.createPublicNetwork(userId, userId, floatingIpCreateConf.getRegion(), floatingIpCreateConf.getBandWidth(), 
					CloudvmNetworkStatusEnum.CREATING, vmCreateContext.getResourceName());
			vmCreateContext.setFloatingIpDbId(network.getId());
			
		}
	}
	
	
}
