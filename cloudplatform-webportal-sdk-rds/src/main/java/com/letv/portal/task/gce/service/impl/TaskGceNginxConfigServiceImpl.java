package com.letv.portal.task.gce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.model.gce.GceContainer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.IGcePythonService;
import com.letv.portal.service.gce.IGceClusterService;
import com.letv.portal.service.gce.IGceContainerService;
import com.letv.portal.service.gce.IGceServerService;

@Service("taskGceNginxConfigService")
public class TaskGceNginxConfigServiceImpl extends BaseTask4GceServiceImpl implements IBaseTaskService{

	@Autowired
	private IGcePythonService gcePythonService;
	@Autowired
	private IGceClusterService gceClusterService;
	@Autowired
	private IGceServerService gceServerService;
	@Autowired
	private IGceContainerService gceContainerService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskGceNginxConfigServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;

		Long nginxClusterId = super.getLongFromObject(params.get("nginxClusterId"));
		Long jettyClusterId = super.getLongFromObject(params.get("jettyClusterId"));
		
		GceCluster nginxCluster = this.gceClusterService.selectById(nginxClusterId);
		List<GceContainer> nginxContainers = gceContainerService.selectByGceClusterId(nginxClusterId);
		List<GceContainer> jettyContainers = gceContainerService.selectByGceClusterId(jettyClusterId);
		
		StringBuffer sb = new StringBuffer();
		
		for (GceContainer jettyContainer : jettyContainers) {
			sb.append(jettyContainer.getBingHostIp()).append(":").append(jettyContainer.getBindContainerPort()).append(",");
		}
		
		Map<String, String> param = new HashMap<String,String>();
		
		param.put("containerClusterName", nginxCluster.getClusterName());
		param.put("upstreamName", "newupstream");
		param.put("serverPorts", sb.length()>0?sb.substring(0, sb.length()-1):sb.toString());
		
		for (GceContainer c : nginxContainers) {
			String result = this.gcePythonService.nginxProxyConfig(param,c.getHostIp(),c.getBindContainerPort(),nginxCluster.getAdminUser(),nginxCluster.getAdminPassword());
			tr = analyzeRestServiceResult(result);
		}
		
		tr.setParams(params);
		return tr;
	}
	
}
