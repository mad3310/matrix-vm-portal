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
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.storage.IStorageDbService;
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeStatusEnum;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskAddVolumeService")
public class TaskAddVolumeServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskAddVolumeServiceImpl.class);
	
	@Autowired
    private IStorageDbService storageDbService;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		String ret = null;
		if(vmCreateConf.getVolumeSize() > 0) {
			ret = storageService.addVolume(params);
			logger.info("云主机创建完成后添加硬盘，结果：{}", ret);
			
			tr.setResult(ret);
			if("success".equals(ret) || "true".equals(ret)) {
				Long userId = Long.parseLong((String)params.get("userId"));
				//更新数据库
				List<JSONObject> vmCreateContexts = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateContexts")), List.class );
				for (JSONObject json : vmCreateContexts) {
					VmCreateContext vmCreateContext = JSONObject.parseObject(json.toString(), VmCreateContext. class);
					this.storageDbService.updateStorage(vmCreateContext.getVolumeDbId(), userId, 
							vmCreateContext.getServerDbId(), CloudvmVolumeStatusEnum.IN_USE);
				}
				tr.setSuccess(true);
				tr.setParams(params);
			} else {
				tr.setSuccess(false);
			}
		} else {
			//跳过该步骤
			tr.setResult("skip");
		}
		
		return tr;
	}
	
}
