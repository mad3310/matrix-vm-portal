package com.letv.portal.task.cbase.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.exception.TaskExecuteException;
import com.letv.common.result.ApiResultObject;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.model.cbase.CbaseContainerModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ICbasePythonService;

@Service("taskCbaseAddNodeService")
public class TaskCbaseAddNodeServiceImpl extends BaseTask4CbaseServiceImpl
		implements IBaseTaskService {

	@Autowired
	private ICbasePythonService cbasePythonService;

	private final static Logger logger = LoggerFactory
			.getLogger(TaskCbaseAddNodeServiceImpl.class);

	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if (!tr.isSuccess())
			return tr;

		// 执行业务
		List<CbaseContainerModel> containers = super.getContainers(params);
		String nodeIp1 = containers.get(0).getIpAddr();
		Long hostCount = getLongFromObject(params.get("hostCount"));
		String nodeIp2 = null;
		if (hostCount != 1) {
			nodeIp2 = containers.get((int) (hostCount - 1)).getIpAddr();
			hostCount--;
			params.put("hostCount", hostCount);
		} else {
			throw new TaskExecuteException("task chain config error");
		}
		CbaseClusterModel cluster = super.getCbaseCluster(params);

		ApiResultObject result = this.cbasePythonService.addNodeToCluster(nodeIp1,
				nodeIp2, super.getCbaseManagePort(), cluster.getAdminUser(),
				cluster.getAdminPassword());
		tr = analyzeRestServiceResult(result);

		tr.setParams(params);
		return tr;
	}

	@Override
	public TaskResult analyzeRestServiceResult(ApiResultObject result) {
		TaskResult tr = new TaskResult();
		if (result.getResult() == null) {
			tr.setSuccess(false);
			tr.setResult("api connect failed:" + result.getUrl());
			return tr;
		}

		boolean isSucess = Constant.PYTHON_API_RESPONSE_SUCCESS.equals(result.getResult());
		if (isSucess) {
			tr.setResult("AddNode SUCCESS");
		} else {
			tr.setResult("AddNode FAILURE:" + result.getUrl());
		}
		tr.setSuccess(isSucess);
		return tr;
	}
}
