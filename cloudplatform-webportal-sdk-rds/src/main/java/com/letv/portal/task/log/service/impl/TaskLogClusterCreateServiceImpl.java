package com.letv.portal.task.log.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.portal.model.HostModel;
import com.letv.portal.model.log.LogCluster;
import com.letv.portal.model.log.LogServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ILogPythonService;

@Service("taskLogClusterCreateService")
public class TaskLogClusterCreateServiceImpl extends BaseTask4LogServiceImpl implements IBaseTaskService{
	
	@Autowired
	private ILogPythonService logPythonService;
	@Value("${matrix.logstash.default.image}")
	private String MATRIX_LOGSTASH_DEFAULT_IMAGE;
	private final static Logger logger = LoggerFactory.getLogger(TaskLogClusterCreateServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;
		if(!(Boolean) params.get("isCreateLog"))  {
			tr.setSuccess(true);
			tr.setResult("no need to create Log");
			return tr;
		}
			
		LogCluster logCluster = super.getLogCluster(params);
		HostModel host = super.getHost(logCluster.getHclusterId());
		LogServer logServer = super.getLogServer(params);
		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("containerClusterName", logCluster.getClusterName());
		map.put("componentType", logServer.getType());
		map.put("networkMode", "bridge");
		map.put("image", MATRIX_LOGSTASH_DEFAULT_IMAGE);
		String result = this.logPythonService.createContainer(map,host.getHostIp(),host.getName(),host.getPassword());
		tr = analyzeRestServiceResult(result);
		
		tr.setParams(params);
		return tr;
	}
	
}
