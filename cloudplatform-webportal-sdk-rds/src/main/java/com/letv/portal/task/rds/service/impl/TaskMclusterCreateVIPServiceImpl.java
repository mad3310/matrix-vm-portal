package com.letv.portal.task.rds.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.exception.ValidateException;
import com.letv.common.result.ApiResultObject;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.image.Image;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.service.image.IImageService;

@Service("taskMclusterCreateVIPService")
public class TaskMclusterCreateVIPServiceImpl extends BaseTask4RDSServiceImpl implements IBaseTaskService{
	
	@Autowired
	private IPythonService pythonService;
	@Autowired
	private IHostService hostService;
	@Autowired
	private IContainerService containerService;
	@Autowired
	private IMclusterService mclusterService;
	@Autowired
	private IImageService imageService;
	@Value("${matrix.rds.vip.default.image}")
	private String MATRIX_RDS_VIP_DEFAULT_IMAGE;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskMclusterCreateVIPServiceImpl.class);
	
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
		
		List<ContainerModel> containers = this.containerService.selectByMclusterId(mclusterId);
		if(containers == null ||containers.isEmpty())
			throw new ValidateException("containers is empty by mclusterId:" + mclusterId);
		StringBuffer excludeServers = new StringBuffer();
		for (ContainerModel containerModel : containers) {
			excludeServers.append(containerModel.getHostIp()).append(",");
		}

		//从数据库获取image
		Map<String,String> map = new HashMap<String,String>();
		map.put("dictionaryName", "RDS");
		map.put("purpose", "vip");
		map.put("isUsed", "1");
		List<Image> images = this.imageService.selectByMap(map);
		if(images!=null && images.size()>1)
			throw new ValidateException("get one Image had many result, params :" + map.toString());
		
		map.clear();
		map.put("containerClusterName", mclusterModel.getMclusterName() + Constant.MCLUSTER_NODE_TYPE_VIP_SUFFIX);
		map.put("componentType", "gbalancer");
		map.put("networkMode", "ip");
		map.put("image", images==null||images.size()==0||images.get(0).getUrl()==null ? MATRIX_RDS_VIP_DEFAULT_IMAGE : images.get(0).getUrl());
		map.put("exclude_servers", excludeServers.substring(0, excludeServers.length()-1));
		ApiResultObject result = this.pythonService.createContainer(map,host.getHostIp(),host.getName(),host.getPassword());
		tr = analyzeRestServiceResult(result);
		
		tr.setParams(params);
		return tr;
	}
	
}
