package com.letv.portal.task.slb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.result.ApiResultObject;
import com.letv.portal.model.slb.SlbCluster;
import com.letv.portal.model.slb.SlbContainer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ISlbPythonService;
import com.letv.portal.service.slb.ISlbContainerService;

@Service("taskSlbContainer1CreateService")
public class TaskSlbContainer1CreateServiceImpl extends BaseTask4SlbServiceImpl implements IBaseTaskService{

	@Autowired
	private ISlbPythonService slbPythonService;
	@Autowired
	private ISlbContainerService slbContainerService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskSlbContainer1CreateServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;

		//执行业务
		List<SlbContainer> containers = super.getContainers(params);
		SlbContainer container = containers.get(0);
		String nodeIp1 = container.getIpAddr();
		SlbCluster cluster = super.getCluster(params);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("clusterName", cluster.getClusterName());
		map.put("dataNodeName", container.getContainerName());
		map.put("dataNodeIp", container.getIpAddr());
		
		ApiResultObject result = this.slbPythonService.createContainer1(map,nodeIp1,cluster.getAdminUser(),cluster.getAdminPassword());
		tr = analyzeRestServiceResult(result);
		if(tr.isSuccess()) {
			Map data = (Map) ((Map)transToMap(result.getResult()).get("response")).get("data");
			container.setContainerUuid((String)data.get("uuid"));
			this.slbContainerService.updateBySelective(container);
		}
		tr.setParams(params);
		return tr;
	}
	
}
