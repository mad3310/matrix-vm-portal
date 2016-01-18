package com.letv.lcp.cloudvm.service.task.storagecreate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.letv.lcp.cloudvm.model.storage.VolumeCreateConf;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.storage.IStorageDbService;
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeStatusEnum;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskStorageCreateService")
public class TaskStorageCreateServiceImpl extends BaseTask4StorageCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskStorageCreateServiceImpl.class);
	
	@Autowired
    private IStorageDbService storageDbService;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		
		
		VolumeCreateConf volumeCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("volumeCreateConf")), VolumeCreateConf.class);
		
		String ret = storageService.create(Long.parseLong((String)params.get("userId")), volumeCreateConf, null, null, params);
		logger.info("创建云硬盘，结果：{}", ret);
		
		tr.setResult(ret);
		if("success".equals(ret) || "true".equals(ret)) {
			//更新数据库
			List<VmCreateContext> vmCreateContexts = (List<VmCreateContext>) params.get("vmCreateContexts");
			for (VmCreateContext vmCreateContext : vmCreateContexts) {
				this.storageDbService.updateStorage(vmCreateContext.getVolumeDbId(), Long.parseLong((String)params.get("userId")), 
						vmCreateContext.getVolumeInstanceId(), CloudvmVolumeStatusEnum.AVAILABLE);
			}
			
			tr.setSuccess(true);
			tr.setParams(params);
		} else {
			tr.setSuccess(false);
		}
		
		return tr;
	}
	
	@Override
	public void rollBack(TaskResult tr) {
		Map<String, Object> params = (Map<String, Object>) tr.getParams();
		VolumeCreateConf volumeCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("volumeCreateConf")), VolumeCreateConf.class);
		
		List<JSONObject> contexts =  JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateContexts")), List.class);
		List<VmCreateContext> vmCreateContexts = new ArrayList<VmCreateContext>();
		for (JSONObject json : contexts) {
			VmCreateContext vmCreateContext = JSONObject.parseObject(json.toJSONString(), VmCreateContext.class);
			vmCreateContexts.add(vmCreateContext);
			if(null != vmCreateContext.getVolumeInstanceId()) {
				boolean ret = storageService.deleteVolumeById((Long)params.get("userId"), volumeCreateConf.getRegion(), vmCreateContext.getVolumeInstanceId(), params);
				if(ret) {
					vmCreateContext.setVolumeInstanceId(null);
				}
			}
		}
		params.put("vmCreateContexts", vmCreateContexts);
	}
	
}
