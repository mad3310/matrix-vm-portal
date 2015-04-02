package com.letv.portal.task.slb.service.impl;

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

@Service("taskSlbInitAdminUserAndPwdService")
public class TaskSlbInitAdminUserAndPwdServiceImpl extends BaseTask4SlbServiceImpl implements IBaseTaskService{

	@Autowired
	private ISlbPythonService slbPythonService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskSlbInitAdminUserAndPwdServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;

		//执行业务
		List<SlbContainer> containers = super.getContainers(params);
		String nodeIp1 = containers.get(0).getIpAddr();
		SlbCluster cluster = super.getCluster(params);
		
		String result = this.slbPythonService.initUserAndPwd4Manager(nodeIp1, cluster.getAdminUser(), cluster.getAdminPassword());
		tr = analyzeRestServiceResult(result);
		
		tr.setParams(params);
		return tr;
	}
	
}
