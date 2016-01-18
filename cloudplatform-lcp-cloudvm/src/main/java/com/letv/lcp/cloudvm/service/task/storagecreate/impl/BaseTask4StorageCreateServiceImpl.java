package com.letv.lcp.cloudvm.service.task.storagecreate.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.letv.common.email.ITemplateMessageSender;
import com.letv.lcp.cloudvm.dispatch.DispatchCenter;
import com.letv.lcp.cloudvm.enumeration.ServiceTypeEnum;
import com.letv.lcp.cloudvm.model.storage.VolumeCreateConf;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.storage.IStorageService;
import com.letv.lcp.cloudvm.util.NameUtil;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.BaseTaskServiceImpl;
import com.letv.portal.service.task.IBaseTaskService;

@Component("baseStorageCreateTaskService")
public class BaseTask4StorageCreateServiceImpl extends BaseTaskServiceImpl implements IBaseTaskService{

	@Value("${service.notice.email.to}")
	private String SERVICE_NOTICE_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	@Autowired
	protected DispatchCenter dispatchCenter;
	
	protected IStorageService storageService;
	
	private final static Logger logger = LoggerFactory.getLogger(BaseTask4StorageCreateServiceImpl.class);
	
	protected void initParams(Map<String, Object> params) {
		VolumeCreateConf volumeCreateConf = JSONObject.parseObject((String)params.get("volumeCreateConf"), VolumeCreateConf.class);
		List<VmCreateContext> vmCreateContexts = new LinkedList<VmCreateContext>();
        for (int i = 0; i < volumeCreateConf.getCount(); i++) {
            vmCreateContexts.add(new VmCreateContext());
        }
        
        if (vmCreateContexts.size() == 1) {
            vmCreateContexts.get(0).setResourceName(volumeCreateConf.getName());
        } else if (vmCreateContexts.size() > 1) {//批量创建修改名称
            String sourceName = volumeCreateConf.getName();
            int i = 1;
            for (VmCreateContext vmCreateContext : vmCreateContexts) {
                vmCreateContext.setResourceName(NameUtil.nameAddNumber(sourceName, i++));
            }
        }
        params.put("uuid", UUID.randomUUID().toString());
        params.put("vmCreateContexts", vmCreateContexts);
	}
	
	@Override
	public void beforExecute(Map<String, Object> params) {
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
