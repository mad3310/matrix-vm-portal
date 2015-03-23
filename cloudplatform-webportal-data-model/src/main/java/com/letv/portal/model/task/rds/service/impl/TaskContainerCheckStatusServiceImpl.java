package com.letv.portal.model.task.rds.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.exception.ValidateException;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.BaseTask4RDSServiceImpl;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IMclusterService;

@Service("taskContainerCheckStatusService")
public class TaskContainerCheckStatusServiceImpl extends BaseTask4RDSServiceImpl implements IBaseTaskService{

	@Value("${python_create_interval_init_time}")
	private long PYTHON_CREATE_INTERVAL_INIT_TIME;
	@Value("${python_init_check_time}")
	private long PYTHON_INIT_CHECK_TIME;
	@Value("${python_init_check_interval_time}")
	private long PYTHON_INIT_CHECK_INTERVAL_TIME;
	
	@Autowired
	private IPythonService pythonService;
	@Autowired
	private IContainerService containerService;
	@Autowired
	private IHostService hostService;
	@Autowired
	private IMclusterService mclusterService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskContainerCheckStatusServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;
		
		Long mclusterId = getLongFromObject(params.get("mclusterId"));
		if(mclusterId == null)
			throw new ValidateException("params's mclusterId is null");
		//执行业务
		MclusterModel mclusterModel = this.mclusterService.selectById(mclusterId);
		if(mclusterModel == null)
			throw new ValidateException("mclusterModel is null by mclusterId:" + mclusterId);
		
		List<ContainerModel> containers = this.containerService.selectByMclusterId(mclusterId);
		if(containers.isEmpty())
			throw new ValidateException("containers is empty by mclusterId:" + mclusterId);
		String nodeIp1 = containers.get(0).getIpAddr();
		String username = mclusterModel.getAdminUser();
		String password = mclusterModel.getAdminPassword();
		
		String result = pythonService.checkContainerStatus(nodeIp1, username, password);
		tr = analyzeRestServiceResult(result);
		
		Long start = new Date().getTime();
		while(!tr.isSuccess()) {
			Thread.sleep(PYTHON_INIT_CHECK_INTERVAL_TIME);
			if(new Date().getTime()-start >PYTHON_INIT_CHECK_TIME) {
				tr.setResult("check time over");
				break;
			}
			result = pythonService.checkContainerStatus(nodeIp1, username, password);
			tr = analyzeRestServiceResult(result);
		}
		
		tr.setParams(params);
		return tr;
	}
	
	@Override
	public TaskResult analyzeRestServiceResult(String result) throws Exception {
		TaskResult tr = new TaskResult();
		Map<String, Object> map = transToMap(result);
		if(map == null) {
			tr.setSuccess(false);
			tr.setResult("api connect failed");
			return tr;
		}
		Map<String,Object> meta = (Map<String, Object>) map.get("meta");
		Map<String,Object> response = null;
		
		boolean isSucess = Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")));
		if(isSucess) {
			response = (Map<String, Object>) map.get("response");
			isSucess = Constant.MCLUSTER_INIT_STATUS_RUNNING.equals(response.get("message"));
		}
		if(isSucess) {
			tr.setResult((String) response.get("message"));
		} else {
			tr.setResult((String) meta.get("errorType") +":"+ (String) meta.get("errorDetail"));
		}
		tr.setSuccess(isSucess);
		return tr;
	}
	
}
