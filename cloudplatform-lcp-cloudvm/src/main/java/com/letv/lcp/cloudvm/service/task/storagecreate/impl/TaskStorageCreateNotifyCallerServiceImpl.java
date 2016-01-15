package com.letv.lcp.cloudvm.service.task.storagecreate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.letv.lcp.cloudvm.enumeration.ListenerTypeEnum;
import com.letv.lcp.cloudvm.model.event.VmCreateEvent;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.listener.ListenerManagerService;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.service.task.IBaseTaskService;

@Service("taskNotifyCallerService")
public class TaskStorageCreateNotifyCallerServiceImpl extends BaseTask4StorageCreateServiceImpl implements IBaseTaskService{
	
	@Autowired
	private ListenerManagerService listenerManagerService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskStorageCreateNotifyCallerServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess()) {
			return tr;
		}
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		List<JSONObject> vmCreateContexts = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateContexts")), List.class);
		List<VmCreateContext> contexts = new ArrayList<VmCreateContext>();
		for (JSONObject jsonObject : vmCreateContexts) {
			contexts.add(JSONObject.parseObject(jsonObject.toJSONString(), VmCreateContext.class));
		}
		VmCreateEvent event = new VmCreateEvent(this, vmCreateConf.getRegion(), null, null, null, params.get("orderNumber"), contexts);
		String ret = this.listenerManagerService.notifyListenersCreated((String)params.get("orderNumber"), ListenerTypeEnum.VmCreateListener, event);
		
		logger.info("创建云主机完成后回调listener，结果：{}", ret);
		
		tr.setResult(ret);
		if("success".equals(ret) || "true".equals(ret)) {
			//调用成功后移除监听器
			this.listenerManagerService.removeListener((String)params.get("orderNumber"));
			tr.setSuccess(true);
			tr.setParams(params);
		} else {
			tr.setSuccess(false);
		}
		
		return tr;
	}
	
}
