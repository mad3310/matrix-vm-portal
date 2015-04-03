package com.letv.portal.proxy.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.model.task.service.ITaskEngine;
import com.letv.portal.proxy.ISlbProxy;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.slb.ISlbServerService;

@Component
public class SlbProxyImpl extends BaseProxyImpl<SlbServer> implements
		ISlbProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(SlbProxyImpl.class);
	
	@Autowired
	private ISlbServerService slbServerService;
	@Autowired
	private ITaskEngine taskEngine;
	
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Value("${db.auto.build.count}")
	private int DB_AUTO_BUILD_COUNT;

	@Override
	public void saveAndBuild(SlbServer slbServer) {
		Map<String,Object> params = this.slbServerService.save(slbServer);
		this.build(params);
	}

	private void build(Map<String,Object> params) {
    	//this.taskEngine.run("SLB_BUY",params);
	}
	
	@Override
	public IBaseService<SlbServer> getService() {
		return slbServerService;
	}
	
}
