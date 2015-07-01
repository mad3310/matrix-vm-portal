package com.letv.portal.task.cbase.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.result.ApiResultObject;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.model.cbase.CbaseContainerModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ICbasePythonService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.cbase.ICbaseContainerService;

@Service("taskCbaseRebalanceCheckStatusService")
public class TaskCbaseRebalanceCheckStatusServiceImpl extends
		BaseTask4CbaseServiceImpl implements IBaseTaskService {

	@Autowired
	private ICbasePythonService cbasePythonService;
	@Autowired
	private IHostService hostService;
	@Autowired
	private ICbaseContainerService cbaseContainerService;

	private final static long PYTHON_CREATE_CHECK_TIME = 180000;
	private final static long PYTHON_CHECK_INTERVAL_TIME = 3000;

	private final static Logger logger = LoggerFactory
			.getLogger(TaskCbaseRebalanceCheckStatusServiceImpl.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if (!tr.isSuccess())
			return tr;

		List<CbaseContainerModel> containers = super.getContainers(params);
		String nodeIp1 = containers.get(0).getIpAddr();

		CbaseClusterModel cluster = super.getCbaseCluster(params);

		ApiResultObject result = this.cbasePythonService.checkClusterRebalanceStatus(
				nodeIp1, super.getCbaseManagePort(), cluster.getAdminUser(),
				cluster.getAdminPassword());
		tr = analyzeRestServiceResult(result);

		Long start = new Date().getTime();
		while (!tr.isSuccess()) {
			Thread.sleep(PYTHON_CHECK_INTERVAL_TIME);
			if (new Date().getTime() - start > PYTHON_CREATE_CHECK_TIME) {
				tr.setResult("check time over");
				break;
			}
			result = cbasePythonService.checkClusterRebalanceStatus(nodeIp1,
					super.getCbaseManagePort(), cluster.getAdminUser(),
					cluster.getAdminPassword());
			tr = analyzeRestServiceResult(result);
		}

		tr.setParams(params);
		return tr;
	}

	@Override
	public TaskResult analyzeRestServiceResult(ApiResultObject result) {
		TaskResult tr = new TaskResult();
		Map<String, Object> map = transToMap(result.getResult());
		if (map == null) {
			tr.setSuccess(false);
			tr.setResult("api connect failed:" + result.getUrl());
			return tr;
		}

		boolean isSucess = Constant.CREATE_REBALANCE_STATUS_RESPONSE_SUCCESS
				.equals(String.valueOf(map.get("status")));
		if (isSucess) {
			tr.setResult("Rebalance DONE");
		} else {
			tr.setResult("Rebalance In Progress");
		}
		tr.setSuccess(isSucess);
		return tr;
	}

}
