package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

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
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.storage.IStorageDbService;
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeStatusEnum;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskCreateStorageService")
public class TaskCreateStorageServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskCreateStorageServiceImpl.class);
	
	@Autowired
    private IStorageDbService storageDbService;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		if (vmCreateConf.getVolumeSize() == 0) {
			return tr;
		}
		VolumeCreateConf volumeCreateConf = new VolumeCreateConf();
		volumeCreateConf.setRegion(vmCreateConf.getRegion());
		volumeCreateConf.setName(vmCreateConf.getName());
		volumeCreateConf.setVolumeTypeId(vmCreateConf.getVolumeTypeId());
		volumeCreateConf.setSize(vmCreateConf.getVolumeSize());
		volumeCreateConf.setCount(vmCreateConf.getCount());
		
		String ret = storageService.create(Long.parseLong((String)params.get("userId")), volumeCreateConf, null, null, params);
		logger.info("创建云主机中的云硬盘，结果：{}", ret);
		
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void rollBack(TaskResult tr) {
		Map<String, Object> params = (Map<String, Object>) tr.getParams();
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		if (!vmCreateConf.getBindFloatingIp()) {
			return;
		}
		storageService.rollBackWithCreateVmFail(params);
	}
	
}
