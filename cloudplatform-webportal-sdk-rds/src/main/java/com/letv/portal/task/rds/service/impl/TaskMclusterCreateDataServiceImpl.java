package com.letv.portal.task.rds.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.exception.ValidateException;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.task.gce.service.impl.TaskGceClusterCreateServiceImpl;

@Service("taskMclusterCreateDataService")
public class TaskMclusterCreateDataServiceImpl extends BaseTask4RDSServiceImpl implements IBaseTaskService{
	
	@Autowired
	private IPythonService pythonService;
	@Autowired
	private IHostService hostService;
	@Autowired
	private IMclusterService mclusterService;
	@Value("${matrix.rds.data.default.image}")
	private String MATRIX_RDS_DATA_DEFAULT_IMAGE;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskMclusterCreateDataServiceImpl.class);

	private final static String  CONTAINER_MEMORY_SIZE = "2147483648";
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception{
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
		HostModel host = this.hostService.getHostByHclusterId(mclusterModel.getHclusterId());
		if(host == null || mclusterModel.getHclusterId() == null)
			throw new ValidateException("host is null by hclusterIdId:" + mclusterModel.getHclusterId());
		Map<String,String> map = new HashMap<String,String>();
		map.put("containerClusterName", mclusterModel.getMclusterName());
		map.put("componentType", "mcluster");
		map.put("networkMode", "ip");
		map.put("memory",CONTAINER_MEMORY_SIZE);
		map.put("image", MATRIX_RDS_DATA_DEFAULT_IMAGE);
		String result = this.pythonService.createContainer(map,host.getHostIp(),host.getName(),host.getPassword());
		tr = analyzeRestServiceResult(result);
		
		tr.setParams(params);
		return tr;
	}
	
}
