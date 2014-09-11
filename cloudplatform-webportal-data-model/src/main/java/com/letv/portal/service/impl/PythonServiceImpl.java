package com.letv.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.util.ConfigUtil;
import com.letv.common.util.HttpClient;
import com.letv.portal.service.IPythonService;

@Service("pythonService")
public class PythonServiceImpl implements IPythonService{
	private final static Logger logger = LoggerFactory.getLogger(PythonServiceImpl.class);
	private final static String MCLUSTER_CREATE_URL = ConfigUtil.getString("mcluster_create_url");
	private final static String URL_HEAD = "http://";	//ConfigUtil.getString("http://");
	private final static String URL_IP = "";			//ConfigUtil.getString("");
	private final static String URL_PORT = "8888";		//ConfigUtil.getString("8888");

	@Override
	public String createContainer(String mclusterName) {
		String result = HttpClient.post(MCLUSTER_CREATE_URL + "/containers/create", null);
		return result;
	}

	@Override
	public String checkContainerCreateStatus() {
		String result = HttpClient.post(MCLUSTER_CREATE_URL + "/containers/status", null);
		return result;
	}

	@Override
	public String initZookeeper(String nodeIp) {
		String url = URL_HEAD  + nodeIp + this.URL_PORT + "/admin/conf";
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("zkAddress", "127.0.0.1");
		
		String result = HttpClient.post(url, map);
		return result;
	}

	@Override
	public String initUserAndPwd4Manager(String nodeIp,String username,String password) {
		String url = URL_HEAD  + nodeIp + this.URL_PORT + "/admin/user";
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("username", username);
		map.put("password", password);
		
		String result = HttpClient.post(url, map);
		return result;
	}

	@Override
	public String postMclusterInfo(String mclusterName,String nodeIp,String nodeName,String username,String password) {
		String url = URL_HEAD  + nodeIp + this.URL_PORT + "/cluster";
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("clusterName", mclusterName);
		map.put("dataNodeIp", nodeIp);
		map.put("dataNodeName", nodeName);
		
		String result = HttpClient.post(url, map,username,password);
		return result;
	}

	@Override
	public String initMcluster(String nodeIp,String username,String password) {
		String url = URL_HEAD  + nodeIp + this.URL_PORT + "/cluster/init";
	
		Map<String,String> map = new HashMap<String,String>();
		map.put("forceInit", "false");
	
		String result = HttpClient.post(url, map,username,password);
		return result;
	}

	@Override
	public String postContainerInfo(String nodeIp,String nodeName,String username,String password) {
		String url = URL_HEAD  + nodeIp + this.URL_PORT + "/cluster/node";
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("dataNodeIp", nodeIp);
		map.put("dataNodeName", nodeName);
		
		String result = HttpClient.post(url, map,username,password);
		return result;
	}

	@Override
	public String syncContainer(String nodeIp,String username,String password) {
		String url = URL_HEAD  + nodeIp + this.URL_PORT + "/cluster/sync";
		String result = HttpClient.post(url, null,username,password);
		return result;
	}

	@Override
	public String startMcluster(String nodeIp,String username,String password) {
		String url = URL_HEAD  + nodeIp + this.URL_PORT + "/cluster/start";

		Map<String,String> map = new HashMap<String,String>();
		map.put("cluster_flag", "new");
		
		String result = HttpClient.post(url, map,username,password);
		return result;
	}

	@Override
	public String checkContainerStatus(String nodeIp,String username,String password) {
		String url = URL_HEAD  + nodeIp + this.URL_PORT + "/cluster/check/online_node";
		String result = HttpClient.post(url, null,username,password);
		return result;
	}

	@Override
	public String createDb(String nodeIp,String dbName,String dbUserName,String ipAddress,String username,String password) {
		ipAddress = "127.0.0.1";
		String url = URL_HEAD  + nodeIp + this.URL_PORT + "/db";
		String result = HttpClient.post(url, null,username,password);
		return result;
	}

	@Override
	public String createDbManagerUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createDbRoDbUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createDbWrUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String initContainer() {
		String nodeIp1 = "";
		this.initZookeeper(nodeIp1);
		
		
		
		
		
		
		return null;
	}
	
}
