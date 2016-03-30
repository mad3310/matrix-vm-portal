package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

import java.util.ArrayList;
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
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.network.INetworkDbService;
import com.letv.portal.enumeration.cloudvm.CloudvmNetworkStatusEnum;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskCreateFloatingIpService")
public class TaskCreateFloatingIpServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskCreateFloatingIpServiceImpl.class);
	
	@Autowired
	private INetworkDbService networkDbService;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		String jsonConf = JSONObject.toJSONString(params.get("vmCreateConf"));
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(jsonConf, VMCreateConf2.class);
		if (!vmCreateConf.getBindFloatingIp()) {
			tr.setResult("skip");
			return tr;
		}
		
		FloatingIpCreateConf floatingIpCreateConf = new FloatingIpCreateConf();
		floatingIpCreateConf.setName(vmCreateConf.getName());
		floatingIpCreateConf.setRegion(vmCreateConf.getRegion());
		floatingIpCreateConf.setBandWidth(vmCreateConf.getBandWidth());
		floatingIpCreateConf.setPublicNetworkId(vmCreateConf.getFloatingNetworkId());
		floatingIpCreateConf.setCount(vmCreateConf.getCount());
		
		String ret = networkService.createFloatingIp(Long.parseLong((String)params.get("userId")), floatingIpCreateConf, null, null, params);
		logger.info("创建云主机中的公网IP，结果：{}", ret);
		
		tr.setResult(ret);
		if("success".equals(ret) || "true".equals(ret)) {
			Long userId = Long.parseLong((String)params.get("userId"));
			//更新数据库
			List<VmCreateContext> vmCreateContexts = (List<VmCreateContext>) params.get("vmCreateContexts");
			for (VmCreateContext vmCreateContext : vmCreateContexts) {
				this.networkDbService.updatePublicNetwork(vmCreateContext.getFloatingIpDbId(), userId, 
						vmCreateContext.getFloatingIpInstanceId(), vmCreateContext.getPublicIp(), vmCreateContext.getCarrierName(), CloudvmNetworkStatusEnum.AVAILABLE);
			}
			params.put("vmCreateContexts", JSONObject.toJSON(vmCreateContexts));
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
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		if (!vmCreateConf.getBindFloatingIp()) {
			return;
		}
		List<JSONObject> contexts =  JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateContexts")), List.class);
		List<VmCreateContext> vmCreateContexts = new ArrayList<VmCreateContext>();
		for (JSONObject json : contexts) {
			VmCreateContext vmCreateContext = JSONObject.parseObject(json.toJSONString(), VmCreateContext.class);
			vmCreateContexts.add(vmCreateContext);
			if(null != vmCreateContext.getFloatingIpInstanceId()) {
				boolean ret = networkService.deleteFloatingIpById((Long)params.get("userId"), vmCreateConf.getRegion(), vmCreateContext.getFloatingIpInstanceId(), params);
				if(ret) {
					vmCreateContext.setFloatingIpInstanceId(null);
				}
			}
		}
		params.put("vmCreateContexts", vmCreateContexts);
	}
	
}
