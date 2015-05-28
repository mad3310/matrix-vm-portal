package com.letv.portal.task.cbase.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.model.HostModel;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ICbasePythonService;

@Service("taskCbaseClusterCreateService")
public class TaskCbaseClusterCreateServiceImpl extends
		BaseTask4CbaseServiceImpl implements IBaseTaskService {

	@Autowired
	private ICbasePythonService cbasePythonService;
	private final static Logger logger = LoggerFactory
			.getLogger(TaskCbaseClusterCreateServiceImpl.class);

	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if (!tr.isSuccess())
			return tr;

		CbaseClusterModel cbaseCluster = super.getCbaseCluster(params);
		CbaseBucketModel bucket = super.getCbaseBucket(params);
		HostModel host = super.getHost(cbaseCluster.getHclusterId());
		Long hostSize = getLongFromObject(params.get("hostSize"));
		String mountDir = "[{'/opt/letv/cbase/var/lib/couchbase/data':'/opt/letv/cbase/var/lib/couchbase/data"
				+ cbaseCluster.getCbaseClusterName() + "', 'ro':False}]";

		// per cluster node mem quota = (bucket mem quota / hostSize) + 100MB
		long tmpPerClusterNodeMemQuotaMB = Integer.valueOf(bucket
				.getRamQuotaMB()) / hostSize;
		long perClusterNodeMemQuotaMB = (long) tmpPerClusterNodeMemQuotaMB + 100;
		String memory = String.valueOf(perClusterNodeMemQuotaMB * 1024 * 1024);

		Map<String, String> map = new HashMap<String, String>();
		map.put("containerClusterName", cbaseCluster.getCbaseClusterName());
		map.put("componentType", "cbase");
		map.put("networkMode", "ip");
		map.put("nodeCount", String.valueOf(hostSize));
		map.put("mountDir", mountDir);
		map.put("memory", memory);

		String result = this.cbasePythonService.createContainer(map,
				host.getHostIp(), host.getName(), host.getPassword());
		tr = analyzeRestServiceResult(result);

		tr.setParams(params);
		return tr;
	}

}
