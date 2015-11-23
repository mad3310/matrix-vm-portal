package com.letv.portal.task.log.service.impl;

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
import com.letv.portal.model.HostModel;
import com.letv.portal.model.image.Image;
import com.letv.portal.model.log.LogCluster;
import com.letv.portal.model.log.LogServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ILogPythonService;
import com.letv.portal.service.image.IImageService;

@Service("taskLogClusterCreateService")
public class TaskLogClusterCreateServiceImpl extends BaseTask4LogServiceImpl implements IBaseTaskService{
	
	@Autowired
	private ILogPythonService logPythonService;
	@Autowired
	private IImageService imageService;
	@Value("${matrix.logstash.default.image}")
	private String MATRIX_LOGSTASH_DEFAULT_IMAGE;
	private final static Logger logger = LoggerFactory.getLogger(TaskLogClusterCreateServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;
		if(!(Boolean) params.get("isCreateLog"))  {
			tr.setSuccess(true);
			tr.setResult("no need to create Log");
			return tr;
		}
			
		LogCluster logCluster = super.getLogCluster(params);
		HostModel host = super.getHost(logCluster.getHclusterId());
		LogServer logServer = super.getLogServer(params);
		
		
		//从数据库获取image
		Map<String,String> map = new HashMap<String,String>();
		map.put("dictionaryName", "LOG");
		map.put("purpose", "default");
		map.put("isUsed", "1");
		List<Image> images = this.imageService.selectByMap(map);
		if(images!=null && images.size()>1)
			throw new ValidateException("get one Image had many result, params :" + map.toString());
		
		map.clear();
		map.put("containerClusterName", logCluster.getClusterName());
		map.put("componentType", logServer.getType());
		map.put("networkMode", "bridge");
		map.put("image", images==null||images.size()==0||images.get(0).getUrl()==null ? MATRIX_LOGSTASH_DEFAULT_IMAGE : images.get(0).getUrl());
		ApiResultObject result = this.logPythonService.createContainer(map,host.getHostIp(),host.getName(),host.getPassword());
		tr = analyzeRestServiceResult(result);
		
		tr.setParams(params);
		return tr;
	}
	
}
