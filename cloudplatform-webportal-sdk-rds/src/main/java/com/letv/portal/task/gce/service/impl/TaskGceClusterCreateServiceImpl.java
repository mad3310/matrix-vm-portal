package com.letv.portal.task.gce.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.model.HostModel;
import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.model.gce.GceServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.IGcePythonService;

@Service("taskGceClusterCreateService")
public class TaskGceClusterCreateServiceImpl extends BaseTask4GceServiceImpl implements IBaseTaskService{
	
	@Autowired
	private IGcePythonService gcePythonService;
	private final static Logger logger = LoggerFactory.getLogger(TaskGceClusterCreateServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;
		
		GceCluster gceCluster = super.getGceCluster(params);
		HostModel host = super.getHost(gceCluster.getHclusterId());
		GceServer gceServer = super.getGceServer(params);
		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("containerClusterName", gceCluster.getClusterName());
		map.put("componentType", gceServer.getType());
		map.put("networkMode", "bridge");
		
		String result = this.gcePythonService.createContainer(map,host.getHostIp(),host.getName(),host.getPassword());
		tr = analyzeRestServiceResult(result);
		
		tr.setParams(params);
		return tr;
	}
	
}
