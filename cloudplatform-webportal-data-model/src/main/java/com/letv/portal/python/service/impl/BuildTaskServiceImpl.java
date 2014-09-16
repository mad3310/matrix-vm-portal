package com.letv.portal.python.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.portal.constant.Constant;
import com.letv.portal.model.BuildModel;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IBuildService;
import com.letv.portal.service.IContainerService;
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
	private IContainerService containerService;
	@Resource
	private IBuildService buildService;
	
	
	@Override
	public void buildMcluster(MclusterModel mclusterModel) {
		boolean nextStep = true;
		mclusterModel.setId(UUID.randomUUID().toString());
		
		if(nextStep) {
			nextStep = createContainer(mclusterModel);
		}
		/*if(nextStep) {
			nextStep = initContainer(mclusterModel);
		}*/
		/*result = transResult(pythonService.checkContainerStatus(nodeIp, username, password));
		while(!analysisResult(result)) {
			result = transResult(pythonService.checkContainerStatus(nodeIp, username, password));
		}*/
		
	}
	@Override
	public boolean createContainer(MclusterModel mclusterModel) {
		boolean nextStep = true;
		mclusterModel.setStatus(Constant.STATUS_BUILDDING);
		this.mclusterService.insert(mclusterModel);
		this.buildService.initStatus(mclusterModel.getId());
		
		int step = 0;
		Date startTime = new Date();
		
		if(nextStep) {
			step++;
			nextStep = this.analysis(this.pythonService.createContainer(mclusterModel.getMclusterName()), step, startTime, mclusterModel.getId(), null);
		}
		//测试集群：mclusterModel.setMclusterName("test_mcluster_003");
		if(nextStep) {
			Map<String,Object> result =  transResult(pythonService.checkContainerCreateStatus(mclusterModel.getMclusterName()));
			while(!analysisCheckResult(result)) {
				result = transResult(pythonService.checkContainerCreateStatus(mclusterModel.getMclusterName()));
			}
			//nextStep = analysis(pythonService.checkContainerCreateStatus(mclusterModel.getMclusterName()), step, startTime, mclusterModel.getId(), null);
			//保存container信息
			
			List<Map> containers = (List<Map>) ((Map)result.get("response")).get("message");
			
			for (Map map : containers) {
				ContainerModel container = new ContainerModel();
				try {
					BeanUtils.populate(container, map);
					container.setClusterId(mclusterModel.getId());
					container.setIpMask((String) map.get("netMask"));
					container.setContainerName((String) map.get("containerClusterName"));
					container.setClusterNodeName((String)map.get("containerName"));
				}catch (Exception e) {
					e.printStackTrace();
				}
				this.containerService.insert(container);
			}
		}
		return nextStep;
	}
	
	@Override
	public void buildDb(String dbId) {
		Map<String,String> params = this.dbService.selectCreateParams(dbId);
		String result = this.pythonService.createDb(params.get("nodeIp"), params.get("dbName"), params.get("dbName"), null, params.get("username"), params.get("password"));
		
		String status = "";
		if(analysisResult(transResult(result))) {
			status = Constant.STATUS_OK;
		} else {
			status = Constant.STATUS_BUILD_FAIL;
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
				dbUserModel.setStatus(Constant.STATUS_OK);
			} else {
				dbUserModel.setStatus(Constant.STATUS_BUILD_FAIL);
			}
			//保存用户创建成功状态
			this.dbUserService.updateStatus(dbUserModel);
		}
		
	}
	
	@Override
	public boolean initContainer(MclusterModel mclusterModel) {
		boolean nextStep = true;
		List<ContainerModel> containers = this.containerService.selectByClusterId(mclusterModel.getId());
		
		Map<String,String> params = new HashMap<String,String>();
		
		//假设数据
		String username = params.get("username");//"root";
		String password = params.get("password");//"webportal-test";
		
		String mclusterName = params.get("mclusterName");//"webportal-test";
		
		String nodeIp1 = params.get("nodeIp1");//"10.200.85.110";
		String nodeName1 = params.get("nodeName1");//"webportal-test-node1";
		
		String nodeIp2 = params.get("nodeIp2");//"10.200.85.111";
		String nodeName2 = params.get("nodeName2");//"webportal-test-node2";

		String nodeIp3 = params.get("nodeIp3");//"10.200.85.112";
		String nodeName3 = params.get("nodeName3");//"webportal-test-node3";
		
		String mclusterId = params.get("mclusterId");//"";
		String dbId = params.get("dbId");//"";
		
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
		
		int step = 0;
		Date startTime = new Date();
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(this.pythonService.initZookeeper(nodeIp1),step,startTime,mclusterId,dbId);
		}
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(this.pythonService.initUserAndPwd4Manager(nodeIp1,username,password),step,startTime,mclusterId,dbId);
		}
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(this.pythonService.postMclusterInfo(mclusterName, nodeIp1, nodeName1, username, password),step,startTime,mclusterId,dbId);
		}
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(this.pythonService.initMcluster(nodeIp1, username, password),step,startTime,mclusterId,dbId);
		}
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(this.pythonService.syncContainer(nodeIp2, username, password),step,startTime,mclusterId,dbId);
		} 
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(this.pythonService.postContainerInfo(nodeIp2, nodeName2, username, password),step,startTime,mclusterId,dbId);
		} 
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(this.pythonService.syncContainer(nodeIp3, username, password),step,startTime,mclusterId,dbId);
		} 
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(this.pythonService.postContainerInfo(nodeIp3, nodeName3, username, password),step,startTime,mclusterId,dbId);
		} 
		if(nextStep) {
			step++;
			startTime = new Date();
			nextStep = analysis(this.pythonService.startMcluster(nodeIp1, username, password),step,startTime,mclusterId,dbId);
		} 
		return nextStep;
	}
	
	private boolean analysis(String result,int step,Date startTime,String mclusterId,String dbId){
		Map<String,Object> jsonResult = transResult(result);
		Map<String,Object> meta = (Map)jsonResult.get("meta");
		BuildModel buildModel = new BuildModel();
		
		buildModel.setMclusterId(mclusterId);
		buildModel.setDbId(dbId);
		buildModel.setStep(step);
		buildModel.setStartTime(startTime);
		buildModel.setEndTime(new Date());
		
		boolean flag = true;
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			Map<String,Object> response = (Map)jsonResult.get("response");
			buildModel.setCode((String)response.get("code"));
			buildModel.setMsg((String) response.get("message"));
			buildModel.setStatus("SUCCESS");
		} else {
			buildModel.setCode(String.valueOf(meta.get("code")));
			buildModel.setMsg((String)meta.get("errorDetail"));
			buildModel.setStatus("FAIL");
			flag =  false;
		}
		this.buildService.updateBySelective(buildModel);
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
	private boolean analysisCheckResult(Map result){
		boolean flag = true;
		Map meta = (Map) result.get("meta");
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			if(!Constant.PYTHON_API_RESULT_SUCCESS.equals(((Map)result.get("response")).get("code"))) {
				flag = false;
			}
		} 
		return flag;
	}
	
}
