package com.letv.portal.proxy.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.portal.model.swift.SwiftServer;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.model.task.service.ITaskEngine;
import com.letv.portal.proxy.ISwiftServerProxy;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.slb.ISlbBackendServerService;
import com.letv.portal.service.swift.ISwiftServerService;

@Component
public class SwiftServerProxyImpl extends BaseProxyImpl<SwiftServer> implements ISwiftServerProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(SwiftServerProxyImpl.class);
	
	@Autowired
	private ISwiftServerService swiftServerService;
	
	@Autowired
	private ISlbBackendServerService slbBackendServerService;
	@Autowired
	private IBaseTaskService baseSlbTaskService;
	@Autowired
	private ITaskEngine taskEngine;
	
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Value("${db.auto.build.count}")
	private int DB_AUTO_BUILD_COUNT;

	@Override
	public void saveAndBuild(SwiftServer swift) {
		this.swiftServerService.insert(swift);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("swiftId", swift.getId());
		this.build(params);
	}

	private void build(Map<String,Object> params) {
    	this.taskEngine.run("OSS_BUY",params);
	}
	
	@Override
	public IBaseService<SwiftServer> getService() {
		return swiftServerService;
	}

}
