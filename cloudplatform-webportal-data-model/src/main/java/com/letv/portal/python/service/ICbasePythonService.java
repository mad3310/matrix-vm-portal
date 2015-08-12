package com.letv.portal.python.service;

import java.util.Map;

import com.letv.common.result.ApiResultObject;

public interface ICbasePythonService {

	public ApiResultObject createContainer(Map<String, String> params, String ip,
			String username, String password);

	public ApiResultObject checkContainerCreateStatus(String cacheName, String ip,
			String username, String password);

	public ApiResultObject initUserAndPwd4Manager(String nodeIp, String port,
			String username, String password);

	public ApiResultObject configClusterMemQuota(String nodeIp, String port,
			String memoryQuota, String username, String password);

	public ApiResultObject addNodeToCluster(String srcNodeIp, String addNodeIp,
			String port, String username, String password);

	public ApiResultObject rebalanceCluster(String nodeIp, String port,
			String knownNodes, String username, String password);

	public ApiResultObject checkClusterRebalanceStatus(String nodeIp, String port,
			String username, String password);

	public ApiResultObject createPersistentBucket(String nodeIp, String port,
			String bucketName, String ramQuotaMB, String authType,
			String saslPassword, String username, String password);

	public ApiResultObject createUnPersistentBucket(String nodeIp, String port,
			String bucketName, String ramQuotaMB, String authType,
			String saslPassword, String username, String password);

	public ApiResultObject CheckClusterStatus(String nodeIp1, String port,
			String adminUser, String adminPassword);

	public ApiResultObject start(Map<String, String> params, String nodeIp1,
			String port, String adminUser, String adminPassword);

	public ApiResultObject stop(Map<String, String> params, String nodeIp1, String port,
			String adminUser, String adminPassword);

	public ApiResultObject restart(Map<String, String> params, String nodeIp1,
			String port, String adminUser, String adminPassword);

}
