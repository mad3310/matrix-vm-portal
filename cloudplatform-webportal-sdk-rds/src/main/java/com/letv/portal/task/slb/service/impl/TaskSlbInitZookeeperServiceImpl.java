package com.letv.portal.task.slb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.result.ApiResultObject;
import com.letv.portal.model.common.ZookeeperInfo;
import com.letv.portal.model.slb.SlbContainer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ISlbPythonService;

@Service("taskSlbInitZookeeperService")
public class TaskSlbInitZookeeperServiceImpl extends BaseTask4SlbServiceImpl implements IBaseTaskService{

	@Autowired
	private ISlbPythonService slbPythonService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskSlbInitZookeeperServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;

		//执行业务
		List<SlbContainer> containers = super.getContainers(params);
		String nodeIp1 = containers.get(0).getIpAddr();
		ZookeeperInfo zk = super.getMinusedZk();
		Map<String, String> zkParm = new HashMap<String,String>();
		zkParm.put("zkAddress", zk.getIp());
		zkParm.put("zkPort", zk.getPort());
		ApiResultObject result = this.slbPythonService.initZookeeper(nodeIp1,zkParm);
		tr = analyzeRestServiceResult(result);
		
		tr.setParams(params);
		return tr;
	}
	
}
