package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.compute.IComputeDbService;
import com.letv.lcp.cloudvm.service.network.INetworkDbService;
import com.letv.lcp.cloudvm.service.storage.IStorageDbService;
import com.letv.portal.enumeration.cloudvm.CloudvmNetworkStatusEnum;
import com.letv.portal.enumeration.cloudvm.CloudvmServerStatusEnum;
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeStatusEnum;
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeTypeEnum;
import com.letv.portal.model.cloudvm.lcp.CloudvmPublicNetworkModel;
import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;
import com.letv.portal.model.cloudvm.lcp.CloudvmStorageModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskVmCreateSaveInfoService")
public class TaskVmCreateSaveInfoServiceImpl extends BaseTask4VmCreateServiceImpl implements IBaseTaskService{
	
	@Autowired
    private IStorageDbService storageDbService;
	@Autowired
	private INetworkDbService networkDbService;
	@Autowired
	private IComputeDbService computeDbService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskVmCreateSaveInfoServiceImpl.class);
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		initParams(params);
		VMCreateConf2 vmCreateConf = JSONObject.parseObject((String)params.get("vmCreateConf"), VMCreateConf2.class);
		if (StringUtils.isEmpty(vmCreateConf.getSharedNetworkId())) {
            vmCreateConf.setBindFloatingIp(false);
        }
        params.put("vmCreateConf", JSONObject.toJSON(vmCreateConf));
		Long userId = Long.parseLong((String)params.get("userId"));
		List<VmCreateContext> vmCreateContexts = (List<VmCreateContext>) params.get("vmCreateContexts");
		Long createUser = params.get("createUser")==null?userId:Long.parseLong((String)params.get("createUser"));
		
		saveInfo(createUser, userId, vmCreateConf, vmCreateContexts);
		params.put("vmCreateContexts", JSONObject.toJSON(vmCreateContexts));
		
		logger.info("创建云主机参数保存到数据库!");
		tr.setResult("success");
		tr.setSuccess(true);
		tr.setParams(params);
		return tr;
	}
	
	
	private void saveInfo(Long createUser, Long userId, VMCreateConf2 vmCreateConf, List<VmCreateContext> vmCreateContexts) {
		for (VmCreateContext vmCreateContext : vmCreateContexts) {
			if(StringUtils.isNotEmpty(vmCreateConf.getSharedNetworkId())) {//保存公网信息
				CloudvmPublicNetworkModel network = this.networkDbService.createPublicNetwork(createUser, userId, vmCreateConf.getRegion(), vmCreateConf.getBandWidth(), 
						CloudvmNetworkStatusEnum.CREATING, vmCreateContext.getResourceName());
				vmCreateContext.setFloatingIpDbId(network.getId());
			}
			//保存主机信息
			CloudvmServerModel serverModel = this.computeDbService.createServer(createUser, userId, vmCreateConf.getRegion(), 
					vmCreateConf.getVolumeSize(), CloudvmServerStatusEnum.BUILD, vmCreateContext.getResourceName(), 
					vmCreateConf.getImageId());
			vmCreateContext.setServerDbId(serverModel.getId());
			if (vmCreateConf.getVolumeSize() != 0) {//保存硬盘信息
				CloudvmStorageModel storageModel = this.storageDbService.create(createUser, userId, 
						vmCreateConf.getRegion(), vmCreateConf.getVolumeSize(), CloudvmVolumeTypeEnum.fromValue(vmCreateConf.getVolumeTypeId()), 
						CloudvmVolumeStatusEnum.CREATING, vmCreateContext.getResourceName());
				vmCreateContext.setVolumeDbId(storageModel.getId());
			}
			
		}
	}
	
	
}
