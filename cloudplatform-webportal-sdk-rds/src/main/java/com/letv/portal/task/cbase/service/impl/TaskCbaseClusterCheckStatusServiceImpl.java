package com.letv.portal.task.cbase.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.result.ApiResultObject;
import com.letv.portal.constant.Constant;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.model.cbase.CbaseContainerModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ICbasePythonService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.cbase.ICbaseContainerService;

@Service("taskCbaseClusterCheckStatusService")
public class TaskCbaseClusterCheckStatusServiceImpl extends
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
			.getLogger(TaskCbaseClusterCheckStatusServiceImpl.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if (!tr.isSuccess())
			return tr;

		CbaseClusterModel cbaseCluster = super.getCbaseCluster(params);
		HostModel host = super.getHost(cbaseCluster.getHclusterId());

		ApiResultObject result = cbasePythonService.checkContainerCreateStatus(
				cbaseCluster.getCbaseClusterName(), host.getHostIp(),
				host.getName(), host.getPassword());
		tr = analyzeRestServiceResult(result);

		Long start = new Date().getTime();
		while (!tr.isSuccess()) {
			Thread.sleep(PYTHON_CHECK_INTERVAL_TIME);
			if (new Date().getTime() - start > PYTHON_CREATE_CHECK_TIME) {
				tr.setResult("check time over:"+result.getUrl());
				break;
			}
			result = cbasePythonService.checkContainerCreateStatus(
					cbaseCluster.getCbaseClusterName(), host.getHostIp(),
					host.getName(), host.getPassword());
			tr = analyzeRestServiceResult(result);
		}
		if (tr.isSuccess()) {
			List<Map> containers = (List<Map>) ((Map) transToMap(result.getResult()).get(
					"response")).get("containers");
			if (containers.size() != getLongFromObject(params.get("hostSize"))
					.intValue()) {
				tr.setSuccess(false);
				tr.setResult("container count != nodeCount");
				tr.setParams(params);
				return tr;
			}
			for (Map map : containers) {
				CbaseContainerModel container = new CbaseContainerModel();
				BeanUtils.populate(container, map);
				container.setCbaseClusterId(cbaseCluster.getId());
				container.setIpMask((String) map.get("netMask"));
				container.setContainerName((String) map.get("containerName"));
				container.setStatus(MclusterStatus.RUNNING.getValue());
				container.setHostIp((String) map.get("hostIp"));
				HostModel hostModel = this.hostService.selectByIp((String) map
						.get("hostIp"));
				if (null != hostModel) {
					container.setHostId(hostModel.getId());
				}
				this.cbaseContainerService.insert(container);
			}
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
		Map<String, Object> meta = (Map<String, Object>) map.get("meta");
		Map<String, Object> response = null;

		boolean isSucess = Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String
				.valueOf(meta.get("code")));
		if (isSucess) {
			response = (Map<String, Object>) map.get("response");
			isSucess = Constant.PYTHON_API_RESULT_SUCCESS.equals(String
					.valueOf(response.get("code")));
		}
		if (isSucess) {
			tr.setResult((String) response.get("message"));
		} else {
			tr.setResult((String) meta.get("errorType") +",the api url:" + result.getUrl());
		}
		tr.setSuccess(isSucess);
		return tr;
	}

}
