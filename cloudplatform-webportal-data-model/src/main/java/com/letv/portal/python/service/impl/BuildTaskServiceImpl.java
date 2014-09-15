package com.letv.portal.python.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.portal.constant.Constant;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.python.service.IPythonService;
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
		
		this.pythonService.checkContainerCreateStatus();
		
		this.pythonService.initContainer();
		
//		this.pythonService.checkContainerStatus(nodeIp, username, password);
		
		this.mclusterService.update(mclusterModel);
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
