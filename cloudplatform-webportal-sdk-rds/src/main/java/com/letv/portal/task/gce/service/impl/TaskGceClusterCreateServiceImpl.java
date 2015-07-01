package com.letv.portal.task.gce.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.portal.enumeration.GceType;
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
	@Value("${matrix.gce.jetty.default.image}")
	private String MATRIX_GCE_JETTY_DEFAULT_IMAGE;
	@Value("${matrix.gce.nginx.default.image}")
	private String MATRIX_GCE_NGINX_DEFAULT_IMAGE;
	private final static Logger logger = LoggerFactory.getLogger(TaskGceClusterCreateServiceImpl.class);
	
	private final static String  CONTAINER_MEMORY_SIZE = "2147483648";
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;
		
		GceCluster gceCluster = super.getGceCluster(params);
		HostModel host = super.getHost(gceCluster.getHclusterId());
		GceServer gceServer = super.getGceServer(params);
		
		
		GceType gceType = gceServer.getType();
		boolean isNginx = gceType.equals(GceType.NGINX) || gceType.equals(GceType.NGINX_PROXY);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("containerClusterName", gceCluster.getClusterName());
		map.put("componentType", gceType.toString().toLowerCase());
		map.put("image", gceServer.getGceImageName());
		map.put("networkMode", "bridge");
		map.put("memory",gceServer.getMemorySize()!=null?String.valueOf(gceServer.getMemorySize()):CONTAINER_MEMORY_SIZE);
		
		if(isNginx)
			map.put("componentType", "nginx");
		
		if(StringUtils.isEmpty(gceServer.getGceImageName())) {
			if(isNginx) {
				map.put("image", MATRIX_GCE_NGINX_DEFAULT_IMAGE);
			}else {
				map.put("image", MATRIX_GCE_JETTY_DEFAULT_IMAGE);
			}
		}
		
		String result = this.gcePythonService.createContainer(map,host.getHostIp(),host.getName(),host.getPassword());
		tr = analyzeRestServiceResult(result);
		
		tr.setParams(params);
		return tr;
	}
	
}
