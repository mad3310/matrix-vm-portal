package com.letv.portal.task.cbase.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.constant.Constant;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.model.cbase.CbaseContainerModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ICbasePythonService;

@Service("taskCbaseCreateBucketService")
public class TaskCbaseCreateBucketServiceImpl extends BaseTask4CbaseServiceImpl
		implements IBaseTaskService {

	@Autowired
	private ICbasePythonService cbasePythonService;

	private final static long PYTHON_CHECK_INTERVAL_TIME = 3000;

	private final static Logger logger = LoggerFactory
			.getLogger(TaskCbaseCreateBucketServiceImpl.class);

	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if (!tr.isSuccess())
			return tr;

		List<CbaseContainerModel> containers = super.getContainers(params);
		String nodeIp1 = containers.get(0).getIpAddr();

		CbaseClusterModel cluster = super.getCbaseCluster(params);
		CbaseBucketModel bucket = super.getCbaseBucket(params);

		if (bucket.getBucketType() == 0) {
			String result = this.cbasePythonService.createPersistentBucket(
					nodeIp1, super.getCbaseManagePort(),
					bucket.getBucketName(), bucket.getRamQuotaMB(),
					bucket.getAuthType(), bucket.getBucketName(),
					cluster.getAdminUser(), cluster.getAdminPassword());
			tr = analyzeRestServiceResult(result);

			while (!tr.isSuccess()) {
				Thread.sleep(PYTHON_CHECK_INTERVAL_TIME);
				result = this.cbasePythonService.createPersistentBucket(
						nodeIp1, super.getCbaseManagePort(),
						bucket.getBucketName(), bucket.getRamQuotaMB(),
						bucket.getAuthType(), bucket.getBucketName(),
						cluster.getAdminUser(), cluster.getAdminPassword());
				tr = analyzeRestServiceResult(result);
			}
		} else if (bucket.getBucketType() == 1) {
			String result = this.cbasePythonService.createUnPersistentBucket(
					nodeIp1, super.getCbaseManagePort(),
					bucket.getBucketName(), bucket.getRamQuotaMB(),
					bucket.getAuthType(), bucket.getBucketName(),
					cluster.getAdminUser(), cluster.getAdminPassword());
			tr = analyzeRestServiceResult(result);

			while (!tr.isSuccess()) {
				Thread.sleep(PYTHON_CHECK_INTERVAL_TIME);
				result = this.cbasePythonService.createUnPersistentBucket(
						nodeIp1, super.getCbaseManagePort(),
						bucket.getBucketName(), bucket.getRamQuotaMB(),
						bucket.getAuthType(), bucket.getBucketName(),
						cluster.getAdminUser(), cluster.getAdminPassword());
				tr = analyzeRestServiceResult(result);
			}
		}

		tr.setParams(params);
		return tr;
	}

	@Override
	public void callBack(TaskResult tr) {
		super.rollBack(tr);
	}

	@Override
	public TaskResult analyzeRestServiceResult(String result) {
		TaskResult tr = new TaskResult();
		if (result == null) {
			tr.setSuccess(false);
			tr.setResult("api connect failed");
			return tr;
		}

		boolean isSucess = Constant.CREATE_BUCKET_API_RESPONSE_SUCCESS
				.equals(result);
		if (isSucess) {
			tr.setResult("Create Bucket SUCCESS");
		} else {
			tr.setResult("Create Bucket FAILURE");
		}
		tr.setSuccess(isSucess);
		return tr;
	}
}
