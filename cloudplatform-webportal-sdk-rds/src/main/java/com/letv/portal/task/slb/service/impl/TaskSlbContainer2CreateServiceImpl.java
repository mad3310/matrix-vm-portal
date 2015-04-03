package com.letv.portal.task.slb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.model.slb.SlbCluster;
import com.letv.portal.model.slb.SlbContainer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ISlbPythonService;

@Service("taskSlbContainer2CreateService")
public class TaskSlbContainer2CreateServiceImpl extends BaseTask4SlbServiceImpl implements IBaseTaskService{

	@Autowired
	private ISlbPythonService slbPythonService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskSlbContainer2CreateServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;

		//执行业务
		List<SlbContainer> containers = super.getContainers(params);
		String nodeIp2 = containers.get(1).getIpAddr();
		SlbCluster cluster = super.getCluster(params);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("clusterName", cluster.getClusterName());
		map.put("dataNodeName", containers.get(1).getContainerName());
		map.put("dataNodeIp", containers.get(1).getIpAddr());
		String result = this.slbPythonService.createContainer2(map,nodeIp2,cluster.getAdminUser(),cluster.getAdminPassword());
		tr = analyzeRestServiceResult(result);
		
		tr.setParams(params);
		return tr;
	}
	
}
