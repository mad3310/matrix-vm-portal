package com.letv.lcp.cloudvm.service.task.storagecreate.impl;

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
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeTypeEnum;
import com.letv.portal.model.cloudvm.lcp.CloudvmStorageModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskStorageCreateSaveInfoService")
public class TaskStorageCreateSaveInfoServiceImpl extends BaseTask4StorageCreateServiceImpl implements IBaseTaskService{
	
	@Autowired
    private IStorageDbService storageDbService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskStorageCreateSaveInfoServiceImpl.class);
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		initParams(params);
		VolumeCreateConf volumeCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("volumeCreateConf")), VolumeCreateConf.class);
		
        params.put("volumeCreateConf", JSONObject.toJSON(volumeCreateConf));
		Long userId = Long.parseLong((String)params.get("userId"));
		List<VmCreateContext> vmCreateContexts = (List<VmCreateContext>) params.get("vmCreateContexts");
		
		saveInfo(userId, volumeCreateConf, vmCreateContexts);
		params.put("vmCreateContexts", JSONObject.toJSON(vmCreateContexts));
		
		logger.info("创建云硬盘参数保存到数据库!");
		tr.setResult("success");
		tr.setSuccess(true);
		tr.setParams(params);
		return tr;
	}
	
	
	private void saveInfo(Long userId, VolumeCreateConf volumeCreateConf, List<VmCreateContext> vmCreateContexts) {
		for (VmCreateContext vmCreateContext : vmCreateContexts) {
			if (volumeCreateConf.getSize() != 0) {//保存硬盘信息
				CloudvmStorageModel storageModel = this.storageDbService.create(userId, userId, 
						volumeCreateConf.getRegion(), volumeCreateConf.getSize(), CloudvmVolumeTypeEnum.fromValue(volumeCreateConf.getVolumeTypeId()), 
						CloudvmVolumeStatusEnum.CREATING, vmCreateContext.getResourceName());
				vmCreateContext.setVolumeDbId(storageModel.getId());
			}
			
		}
	}
	
	
}
