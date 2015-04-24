package com.letv.portal.task.cbase.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.constant.Constant;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.model.cbase.CbaseContainerModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ICbasePythonService;

@Service("taskCbaseRebalanceService")
public class TaskCbaseRebalanceServiceImpl extends BaseTask4CbaseServiceImpl
		implements IBaseTaskService {

	@Autowired
	private ICbasePythonService cbasePythonService;

	private final static Logger logger = LoggerFactory
			.getLogger(TaskCbaseRebalanceServiceImpl.class);

	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if (!tr.isSuccess())
			return tr;

		// 执行业务
		List<CbaseContainerModel> containers = super.getContainers(params);
		String nodeIp1 = containers.get(0).getIpAddr();
		String nodeIp2 = containers.get(1).getIpAddr();
		String nodeIp3 = containers.get(2).getIpAddr();
		StringBuffer knownNodes = new StringBuffer();
		knownNodes.append("ns_1@").append(nodeIp1).append(",").append("ns_1@")
				.append(nodeIp2).append(",").append("ns_1@").append(nodeIp3);

		CbaseClusterModel cluster = super.getCbaseCluster(params);

		String result = this.cbasePythonService.rebalanceCluster(nodeIp1,
				super.getCbaseManagePort(), knownNodes.toString(),
				cluster.getAdminUser(), cluster.getAdminPassword());
		tr = analyzeRestServiceResult(result);

		tr.setParams(params);
		return tr;
	}

	@Override
	public TaskResult analyzeRestServiceResult(String result) {
		TaskResult tr = new TaskResult();
		if (result == null) {
			tr.setSuccess(false);
			tr.setResult("api connect failed");
			return tr;
		}

		boolean isSucess = Constant.PYTHON_API_RESPONSE_SUCCESS.equals(result);
		if (isSucess) {
			tr.setResult("Rebalance Operation SUCCESS");
		} else {
			tr.setResult("Rebalance Operation FAILURE");
		}
		tr.setSuccess(isSucess);
		return tr;
	}
}
