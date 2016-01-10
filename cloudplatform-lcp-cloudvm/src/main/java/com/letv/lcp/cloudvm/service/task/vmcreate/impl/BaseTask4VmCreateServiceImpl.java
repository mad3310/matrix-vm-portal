package com.letv.lcp.cloudvm.service.task.vmcreate.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.letv.common.email.ITemplateMessageSender;
import com.letv.lcp.cloudvm.dispatch.DispatchCenter;
import com.letv.lcp.cloudvm.enumeration.ServiceTypeEnum;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.compute.IComputeService;
import com.letv.lcp.cloudvm.service.network.INetworkService;
import com.letv.lcp.cloudvm.service.storage.IStorageService;
import com.letv.lcp.cloudvm.util.NameUtil;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.BaseTaskServiceImpl;
import com.letv.portal.service.task.IBaseTaskService;

@Component("baseVmCreateTaskService")
public class BaseTask4VmCreateServiceImpl extends BaseTaskServiceImpl implements IBaseTaskService{

	@Value("${service.notice.email.to}")
	private String SERVICE_NOTICE_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	@Autowired
	protected DispatchCenter dispatchCenter;
	
	protected IComputeService computeService;
	protected INetworkService networkService;
	protected IStorageService storageService;
	
	private final static Logger logger = LoggerFactory.getLogger(BaseTask4VmCreateServiceImpl.class);
	
	protected void initParams(Map<String, Object> params) {
		VMCreateConf2 vmCreateConf = JSONObject.parseObject((String) params.get("vmCreateConf"), VMCreateConf2.class);
		List<VmCreateContext> vmCreateContexts = new LinkedList<VmCreateContext>();
        for (int i = 0; i < vmCreateConf.getCount(); i++) {
            vmCreateContexts.add(new VmCreateContext());
        }
        
        if (vmCreateContexts.size() == 1) {
            vmCreateContexts.get(0).setResourceName(vmCreateConf.getName());
        } else if (vmCreateContexts.size() > 1) {//批量创建修改名称
            String sourceName = vmCreateConf.getName();
            int i = 1;
            for (VmCreateContext vmCreateContext : vmCreateContexts) {
                vmCreateContext.setResourceName(NameUtil.nameAddNumber(sourceName, i++));
            }
        }
        params.put("vmCreateContexts", JSONObject.toJSON(vmCreateContexts));
	}
	
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
