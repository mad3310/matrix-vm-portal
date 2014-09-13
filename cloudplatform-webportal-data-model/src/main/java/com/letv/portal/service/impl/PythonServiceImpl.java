package com.letv.portal.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.util.ConfigUtil;
import com.letv.common.util.HttpClient;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.BuildModel;
import com.letv.portal.service.IBuildService;
import com.letv.portal.service.IPythonService;

@Service("pythonService")
public class PythonServiceImpl implements IPythonService{
	
	private final static Logger logger = LoggerFactory.getLogger(PythonServiceImpl.class);
	
	private final static String MCLUSTER_CREATE_URL = ConfigUtil.getString("mcluster_create_url");
	private final static String URL_HEAD = "http://";	//ConfigUtil.getString("http://");
	private final static String URL_IP = "";			//ConfigUtil.getString("");
	private final static String URL_PORT = ":8888";		//ConfigUtil.getString("8888");
	
	@Resource
	private IBuildService buildService;

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
		map.put("adminUser", username);
		map.put("adminPassword", password);
		
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
		String url = URL_HEAD  + nodeIp + this.URL_PORT + "/cluster/init?forceInit=false";
	
		String result = HttpClient.get(url,username,password);
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
		String result = HttpClient.get(url,username,password);
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
		
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		while(!Constant.PYTHON_API_CHECK_CONTAINER_RUNNING.equals(jsonResult.get("message"))) {
			jsonResult = transResult(HttpClient.get(url,username,password));
			if(!Constant.PYTHON_API_RESULT_SECCESS.equals(jsonResult.get("code"))) {
				//请求错误，记录错误信息。
				return "";
			}
		}
		//成功！记录成功信息，返回1
		return "";
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
	public void initContainer() {
		logger.debug("initContainer==>in");
		
		//假设数据
		String username = "root";
		String password = "webportal-test";
		
		String mclusterName="webportal-test";
		
		String nodeIp1 = "10.200.85.110";
		String nodeName1="webportal-test-node1";
		
		String nodeIp2 = "10.200.85.111";
		String nodeName2="webportal-test-node2";

		String nodeIp3 = "10.200.85.112";
		String nodeName3="webportal-test-node3";
		
		
		/*mcluster-manager测试用集群
		10.200.85.110
		10.200.85.111
		10.200.85.112
		
		物理机
		10.200.91.142
		10.200.91.143
		10.200.91.144
		root/dabingge$1985
		*/
		
		boolean nextStep = true;
		String step = "";
		if(nextStep) {
			step = "初始化Zookeeper节点";
			nextStep = analysis(this.initZookeeper(nodeIp1),step);
		} else {
			return;
		}
		if(nextStep) {
			step = "初始化mcluster管理用户名密码";
			nextStep = analysis(this.initUserAndPwd4Manager(nodeIp1,username,password),step);
		}
		if(nextStep) {
			step = "提交mcluster集群信息";
			nextStep = analysis(this.postMclusterInfo(mclusterName, nodeIp1, nodeName1, username, password),step);
		}
		if(nextStep) {
			step = "初始化集群";
			nextStep = analysis(this.initMcluster(nodeIp1, username, password),step);
		}
		if(nextStep) {
			step = "同步节点信息 " + nodeIp2;
			nextStep = analysis(this.syncContainer(nodeIp2, username, password),step);
		}
		if(nextStep) {
			step = "提交节点信息" + nodeIp2;
			nextStep = analysis(this.postContainerInfo(nodeIp2, nodeName2, username, password),step);
		}
		if(nextStep) {
			step = "同步节点信息 " + nodeIp3;
			nextStep = analysis(this.syncContainer(nodeIp3, username, password),step);
		}
		if(nextStep) {
			step = "提交节点信息 " + nodeIp3;
			nextStep = analysis(this.postContainerInfo(nodeIp3, nodeName3, username, password),step);
		}
		if(nextStep) {
			step = "启动集群";
			nextStep = analysis(this.startMcluster(nodeIp1, username, password),step);
		}
		if(nextStep) {
			step = "检查各节点状态";
			while(1==1){
				nextStep = analysis(this.checkContainerStatus(nodeIp1, username, password),step);
			}
		}
		
	}
	
	private boolean analysis(String result,String step){
		Map<String,Object> jsonResult = transResult(result);
		BuildModel buildModel = new BuildModel();
		boolean flag = true;
		
		if(Constant.PYTHON_API_RESULT_SECCESS.equals(jsonResult.get("code"))) {
			buildModel.setStatus("SUCCESS");
			//写入数据库 执行状态   + step
			//返回true，执行下一步
		} else {
			buildModel.setStatus("FAIL");
			//返回false，执行结束
			flag =  false;
		}
		
		buildModel.setStep(step);
		buildModel.setCode((String) jsonResult.get("code"));
		buildModel.setMsg((String) jsonResult.get("message"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		buildModel.setCreateTime(sdf.format(new Date()));
		logger.debug("===================================================");
		logger.debug("buildModel===>" + buildModel);
		
//		this.buildService.insert(buildModel);
		return flag;
	}
	
	
	
	private Map<String,Object> transResult(String result){
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
			jsonResult = (Map)jsonResult.get("response");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
}
