package com.letv.portal.python.service;

import java.util.Map;

public interface ICbasePythonService {

	public String createContainer(Map<String, String> params, String ip,
			String username, String password);

	public String checkContainerCreateStatus(String cacheName, String ip,
			String username, String password);

	public String initUserAndPwd4Manager(String nodeIp, String port,
			String username, String password);

	public String configClusterMemQuota(String nodeIp, String port,
			String memoryQuota, String username, String password);

	public String addNodeToCluster(String srcNodeIp, String addNodeIp,
			String port, String username, String password);

	public String rebalanceCluster(String nodeIp, String port,
			String knownNodes, String username, String password);

	public String checkClusterRebalanceStatus(String nodeIp, String port,
			String username, String password);

	public String createPersistentBucket(String nodeIp, String port,
			String bucketName, String ramQuotaMB, String authType,
			String saslPassword, String username, String password);
	
	public String createUnPersistentBucket(String nodeIp, String port,
			String bucketName, String ramQuotaMB, String authType,
			String saslPassword, String username, String password);

	public String CheckClusterStatus(String nodeIp1, String port,
			String adminUser, String adminPassword);

	public String start(Map<String, String> params, String nodeIp1,
			String port, String adminUser, String adminPassword);

	public String stop(Map<String, String> params, String nodeIp1, String port,
			String adminUser, String adminPassword);

	public String restart(Map<String, String> params, String nodeIp1,
			String port, String adminUser, String adminPassword);

}
