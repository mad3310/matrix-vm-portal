package com.letv.portal.task.gce.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.exception.TaskExecuteException;
import com.letv.common.result.ApiResultObject;
import com.letv.portal.enumeration.SlbStatus;
import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.model.gce.GceContainer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.model.task.service.ITaskEngine;
import com.letv.portal.python.service.IGcePythonService;

@Service("taskGceCheckStartService")
public class TaskGceCheckStartServiceImpl extends BaseTask4GceServiceImpl implements IBaseTaskService{

	@Autowired
	private IGcePythonService gcePythonService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskGceCheckStartServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;

		//执行业务
		List<GceContainer> containers = super.getContainers(params);
		String nodeIp1 = containers.get(0).getHostIp();
		String port = containers.get(0).getMgrBindHostPort();
		GceCluster cluster = super.getGceCluster(params);
		ApiResultObject resultObject =  this.gcePythonService.CheckClusterStatus(nodeIp1,port,cluster.getAdminUser(),cluster.getAdminPassword());
		tr = super.analyzeRestServiceResult(resultObject);
		String result = "";
		if(tr.isSuccess()) {
			Map<String,Object> response = (Map<String, Object>) tr.getParams();
			result =  (String) ((Map<String,Object>)response.get("data")).get("status");
		}
		if(!"STARTED".equals(result)) {
			tr.setSuccess(false);
			tr.setResult("service start failed,the status was " + result +",the api url:" + resultObject.getUrl());
		}
		tr.setParams(params);
		return tr;
	}
	
	@Override
	public void callBack(TaskResult tr) {
	}
	
}
