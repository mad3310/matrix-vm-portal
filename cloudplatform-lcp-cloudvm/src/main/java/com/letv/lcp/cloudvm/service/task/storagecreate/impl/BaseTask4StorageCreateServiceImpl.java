package com.letv.lcp.cloudvm.service.task.storagecreate.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.lcp.cloudvm.dispatch.DispatchCenter;
import com.letv.lcp.cloudvm.enumeration.ServiceTypeEnum;
import com.letv.lcp.cloudvm.service.compute.IComputeService;
import com.letv.lcp.cloudvm.service.network.INetworkService;
import com.letv.lcp.cloudvm.service.storage.IStorageService;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.BaseTaskServiceImpl;
import com.letv.portal.service.task.IBaseTaskService;

@Component("baseVmCreateTaskService")
public class BaseTask4StorageCreateServiceImpl extends BaseTaskServiceImpl implements IBaseTaskService{

	@Value("${service.notice.email.to}")
	private String SERVICE_NOTICE_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	@Autowired
	protected DispatchCenter dispatchCenter;
	
	protected IComputeService computeService;
	protected INetworkService networkService;
	protected IStorageService storageService;
	
	private final static Logger logger = LoggerFactory.getLogger(BaseTask4StorageCreateServiceImpl.class);
	
	@Override
	public void beforExecute(Map<String, Object> params) {
		computeService = (IComputeService) dispatchCenter.getServiceByStrategy(ServiceTypeEnum.COMPUTE);
		networkService = (INetworkService) dispatchCenter.getServiceByStrategy(ServiceTypeEnum.NETWORK);
		storageService = (IStorageService) dispatchCenter.getServiceByStrategy(ServiceTypeEnum.STORAGE);
	}
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = new TaskResult();
		if(params == null || params.isEmpty()) {
			tr.setResult("params is empty");
			tr.setSuccess(false);
		}
		tr.setParams(params);
		return tr;
	}

	@Override
	public void rollBack(TaskResult tr) {

	}
	
}
