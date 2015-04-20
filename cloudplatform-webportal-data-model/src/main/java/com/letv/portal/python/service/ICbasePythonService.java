package com.letv.portal.python.service;

import java.util.Map;

public interface ICbasePythonService {

	public String createContainer(Map<String, String> params, String ip,
			String username, String password);

	public String checkContainerCreateStatus(String cacheName, String ip,
			String username, String password);

	public String initUserAndPwd4Manager(String nodeIp, String port,
			String username, String password);

	public String startCluster(String nodeIp1, String port, String adminUser,
			String adminPassword);

	public String CheckClusterStatus(String nodeIp1, String port,
			String adminUser, String adminPassword);

	public String start(Map<String, String> params, String nodeIp1,
			String port, String adminUser, String adminPassword);

	public String stop(Map<String, String> params, String nodeIp1, String port,
			String adminUser, String adminPassword);

	public String restart(Map<String, String> params, String nodeIp1,
			String port, String adminUser, String adminPassword);

}
