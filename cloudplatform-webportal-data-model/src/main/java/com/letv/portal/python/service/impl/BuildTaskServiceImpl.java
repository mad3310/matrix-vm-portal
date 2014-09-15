package com.letv.portal.python.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.portal.constant.Constant;
import com.letv.portal.model.BuildModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IBuildService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IMclusterService;

@Service("buildTaskService")
public class BuildTaskServiceImpl implements IBuildTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(BuildTaskServiceImpl.class);
	@Resource
	private IMclusterService mclusterService;
	@Resource
	private IDbUserService dbUserService;
	@Resource
	private IPythonService pythonService;
	@Resource
	private IDbService dbService;
	@Resource
	private IBuildService buildService;
	
	
	@Override
	public void buildMcluster(MclusterModel mclusterModel) {
		/*
		 * 创建过程
		 * 1、保存mcluster初始状态
		 * 2、调用python api 创建mcluster
		 * 3、循环检查创建结果，检查成功后，保存创建结果。 
		 */
		this.mclusterService.insert(mclusterModel);
		this.pythonService.createContainer(mclusterModel.getMclusterName());
		
		Map<String,Object> result =  transResult(pythonService.checkContainerCreateStatus());
		while(!analysisResult(result)) {
			result = transResult(pythonService.checkContainerCreateStatus());
		}
		
		this.initContainer();
		
		/*result = transResult(pythonService.checkContainerStatus(nodeIp, username, password));
		while(!analysisResult(result)) {
			result = transResult(pythonService.checkContainerStatus(nodeIp, username, password));
		}*/
		
	}
	
	
	@Override
	public void buildDb(String dbId) {
		Map<String,String> params = this.dbService.selectCreateParams(dbId);
		String result = this.pythonService.createDb(params.get("nodeIp"), params.get("dbName"), params.get("dbName"), null, params.get("username"), params.get("password"));
		
		String status = "";
		if(analysisResult(transResult(result))) {
			status = Constant.DB_USER_STATUS_BUILD_SUCCESS;
		} else {
			status = Constant.DB_USER_STATUS_BUILD_FAIL;
		}
		//保存用户创建成功状态
		this.dbService.build(new DbModel(dbId,status));
	}


	@Override
	public void buildUser(String ids) {
		String[] str = ids.split(",");
		for (String id : str) {
			
			//查询所属db 所属mcluster 及container数据
			DbUserModel dbUserModel = this.dbUserService.selectById(id);
			Map<String,String> params = this.dbUserService.selectCreateParams(id);
			
			String result = this.pythonService.createDbUser(dbUserModel, params.get("dbName"), params.get("nodeIp"), params.get("username"), params.get("password"));
			if(analysisResult(transResult(result))) {
				dbUserModel.setStatus(Constant.DB_USER_STATUS_BUILD_SUCCESS);
			} else {
				dbUserModel.setStatus(Constant.DB_USER_STATUS_BUILD_FAIL);
			}
			//保存用户创建成功状态
			this.dbUserService.updateStatus(dbUserModel);
		}
		
	}
	
	@Override
	public void initContainer() {
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
		
		String mclusterId="";
		String dbId="";
		
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
		int step = 0;
		String stepMsg = "";
		String startTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(nextStep) {
			step++;
			stepMsg = "初始化Zookeeper节点";
			startTime = sdf.format(new Date());
			nextStep = analysis(this.pythonService.initZookeeper(nodeIp1),step,stepMsg,startTime,mclusterId,dbId);
		} else {
			return;
		}
		if(nextStep) {
			step++;
			stepMsg = "初始化mcluster管理用户名密码";
			startTime = sdf.format(new Date());
			nextStep = analysis(this.pythonService.initUserAndPwd4Manager(nodeIp1,username,password),step,stepMsg,startTime,mclusterId,dbId);
		} else {
			return;
		}
		if(nextStep) {
			step++;
			stepMsg = "提交mcluster集群信息";
			startTime = sdf.format(new Date());
			nextStep = analysis(this.pythonService.postMclusterInfo(mclusterName, nodeIp1, nodeName1, username, password),step,stepMsg,startTime,mclusterId,dbId);
		} else {
			return;
		}
		if(nextStep) {
			step++;
			stepMsg = "初始化集群";
			startTime = sdf.format(new Date());
			nextStep = analysis(this.pythonService.initMcluster(nodeIp1, username, password),step,stepMsg,startTime,mclusterId,dbId);
		} else {
			return;
		}
		if(nextStep) {
			step++;
			stepMsg = "同步节点信息 " + nodeIp2;
			startTime = sdf.format(new Date());
			nextStep = analysis(this.pythonService.syncContainer(nodeIp2, username, password),step,stepMsg,startTime,mclusterId,dbId);
		} else {
			return;
		}
		if(nextStep) {
			step++;
			stepMsg = "提交节点信息" + nodeIp2;
			startTime = sdf.format(new Date());
			nextStep = analysis(this.pythonService.postContainerInfo(nodeIp2, nodeName2, username, password),step,stepMsg,startTime,mclusterId,dbId);
		} else {
			return;
		}
		if(nextStep) {
			step++;
			stepMsg = "同步节点信息 " + nodeIp3;
			startTime = sdf.format(new Date());
			nextStep = analysis(this.pythonService.syncContainer(nodeIp3, username, password),step,stepMsg,startTime,mclusterId,dbId);
		} else {
			return;
		}
		if(nextStep) {
			step++;
			stepMsg = "提交节点信息 " + nodeIp3;
			startTime = sdf.format(new Date());
			nextStep = analysis(this.pythonService.postContainerInfo(nodeIp3, nodeName3, username, password),step,stepMsg,startTime,mclusterId,dbId);
		} else {
			return;
		}
		if(nextStep) {
			step++;
			stepMsg = "启动集群";
			startTime = sdf.format(new Date());
			nextStep = analysis(this.pythonService.startMcluster(nodeIp1, username, password),step,stepMsg,startTime,mclusterId,dbId);
		} else {
			return;
		}
		
	}
	
	private boolean analysis(String result,int step,String stepMsg,String startTime,String mclusterId,String dbId){
		Map<String,Object> jsonResult = transResult(result);
		Map<String,Object> meta = (Map)jsonResult.get("meta");
		BuildModel buildModel = new BuildModel();
		
		buildModel.setMclusterId(mclusterId);
		buildModel.setDbId(dbId);
		buildModel.setStep(step);
		buildModel.setStepMsg(stepMsg);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		buildModel.setStartTime(startTime);
		buildModel.setEndTime(sdf.format(new Date()));
		
		//SUCCESS==>{"meta": {"code": 200}, "response": {"message": "admin conf successful!", "code": "000000"}}
		//FAIL==>{"notification": {"message": "direct"}, 
		//		  "meta": {"code": 417, "errorType": "user_visible_error", "errorDetail": "server has belong to a cluster,should be not create new cluster!"}, 
		//	      "response": "the server has belonged to a cluster,should be not create new cluster!"}
		
		boolean flag = true;
		
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			Map<String,Object> response = (Map)jsonResult.get("response");
			buildModel.setCode((String)response.get("code"));
			buildModel.setMsg((String) response.get("message"));
			if(Constant.PYTHON_API_RESULT_SUCCESS.equals(response.get("code"))) {
				buildModel.setStatus("SUCCESS");
				//写入数据库 执行状态   + step
				//返回true，执行下一步
			} else {
				buildModel.setStatus("FAIL");
				//返回false，执行结束
				flag =  false;
			}
			
		} else {
			buildModel.setCode(String.valueOf(meta.get("code")));
			buildModel.setMsg((String)meta.get("errorDetail"));
			buildModel.setStatus("FAIL");
			flag =  false;
		}
		
		this.buildService.insert(buildModel);
		return flag;
	}
	
	private Map<String,Object> transResult(String result){
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	private boolean analysisResult(Map result){
		boolean flag = true;
		Map meta = (Map) result.get("meta");
		if(!Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			flag = false;
		} 
		return flag;
	}
	
}
