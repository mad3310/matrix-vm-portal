package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.lcp.cloudvm.model.network.FloatingIpCreateConf;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskCreateFloatingIpService")
public class TaskCreateFloatingIpServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskCreateFloatingIpServiceImpl.class);
	
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		VMCreateConf2 vmCreateConf = (VMCreateConf2)params.get("vmCreateConf");
		if (!vmCreateConf.getBindFloatingIp()) {
			return tr;
		}
		
		FloatingIpCreateConf floatingIpCreateConf = new FloatingIpCreateConf();
		floatingIpCreateConf.setName(vmCreateConf.getName());
		floatingIpCreateConf.setRegion(vmCreateConf.getRegion());
		floatingIpCreateConf.setBandWidth(vmCreateConf.getBandWidth());
		floatingIpCreateConf.setPublicNetworkId(vmCreateConf.getFloatingNetworkId());
		floatingIpCreateConf.setCount(vmCreateConf.getCount());
		
		String ret = networkService.createFloatingIp((Long)params.get("userId"), floatingIpCreateConf, null, null, params);
		logger.info("创建云主机中的公网IP，结果：{}", ret);
		
		tr.setResult(ret);
		if("success".equals(ret) || "true".equals(ret)) {
			tr.setSuccess(true);
			tr.setParams(params);
		} else {
			tr.setSuccess(false);
		}
		
		return tr;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void rollBack(TaskResult tr) {
		Map<String, Object> params = (Map<String, Object>) tr.getParams();
		VMCreateConf2 vmCreateConf = (VMCreateConf2)params.get("vmCreateConf");
		if (!vmCreateConf.getBindFloatingIp()) {
			return;
		}
		networkService.rollBackFloatingIpWithCreateVmFail(params);
	}
	
}
