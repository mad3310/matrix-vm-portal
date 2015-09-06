package com.letv.portal.task.test.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.BaseTaskServiceImpl;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.swift.ISwiftServerService;

@Component("baseTask4TestService")
public class BaseTask4TestServiceImpl extends BaseTaskServiceImpl implements IBaseTaskService{

	@Value("${service.notice.email.to}")
	private String SERVICE_NOTICE_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Autowired
	private IHostService hostService;
	@Autowired
	private ISwiftServerService swiftServerService;
	@Autowired
	private IUserService userService;
	
	private final static Logger logger = LoggerFactory.getLogger(BaseTask4TestServiceImpl.class);
	
	@Override
	public void beforExecute(Map<String, Object> params) {
	}
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = new TaskResult();
		/*if(params == null || params.isEmpty()) {
			tr.setResult("params is empty");
			tr.setSuccess(false);
		}*/
		tr.setParams(params);
		return tr;
	}

	@Override
	public void rollBack(TaskResult tr) {
		//发送邮件
		this.buildResultToMgr("Test服务()创建", tr.isSuccess()?"成功":"失败", tr.getResult(), SERVICE_NOTICE_MAIL_ADDRESS);
	}

	@Override
	public void callBack(TaskResult tr) {
		
	}

}
