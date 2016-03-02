package com.letv.portal.service.proxy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.lcp.cloudvm.dispatch.DispatchCenter;
import com.letv.lcp.cloudvm.enumeration.ServiceTypeEnum;
import com.letv.lcp.cloudvm.service.compute.IComputeService;
import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;
import com.letv.portal.service.lcp.ICloudvmServerService;
import com.letv.portal.service.proxy.ICmdbCallbackService;

@Service("cmdbCallbackService")
public class CmdbCallbackServiceImpl implements ICmdbCallbackService {
	
	@Autowired
	protected DispatchCenter dispatchCenter;
	@Autowired
    private ICloudvmServerService cloudvmServerService;
	
	private final static Logger logger = LoggerFactory.getLogger(CmdbCallbackServiceImpl.class);

	@Override
	public String getPhysicalHostNameByServerInfo(CloudvmServerModel serverModel) {
		IComputeService computeService = (IComputeService) dispatchCenter.getServiceByStrategy(ServiceTypeEnum.COMPUTE);
		return computeService.getPhysicalHostNameByServerInfo(serverModel);
	}

	@Override
	public void saveVmInfo(String serverInstanceId) {
		CloudvmServerModel serverModel = this.cloudvmServerService.selectByServerInstanceId(serverInstanceId);
		String hostName = this.getPhysicalHostNameByServerInfo(serverModel);
		logger.info("====================================="+hostName);
		
	}
	

}
