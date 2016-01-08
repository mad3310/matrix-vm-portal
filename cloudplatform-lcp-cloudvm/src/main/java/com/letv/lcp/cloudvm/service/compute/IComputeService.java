package com.letv.lcp.cloudvm.service.compute;

import java.util.Map;

import com.letv.lcp.cloudvm.listener.VmCreateListener;
import com.letv.lcp.cloudvm.model.compute.ComputeModel;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.service.resource.IResourceService;


public interface IComputeService extends IResourceService<ComputeModel>  {
	String createVm(Long userId, VMCreateConf2 vmCreateConf, VmCreateListener vmCreateListener, Object listenerUserData, Map<String, Object> params);

	String getVmCreatePrepare(Map<String, Object> params);
	
	String checkVmCreateConf(Map<String, Object> params);
	
	String checkQuota(Map<String, Object> params);
	
	String waitingVmsCreated(Map<String, Object> params);
	
	String bindFloatingIp(Map<String, Object> params);
	
	String emailVmsCreated(Map<String, Object> params);
}
