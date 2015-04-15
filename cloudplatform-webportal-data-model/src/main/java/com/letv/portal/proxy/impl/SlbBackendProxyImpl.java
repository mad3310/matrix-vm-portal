package com.letv.portal.proxy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.portal.enumeration.SlbBackendStatus;
import com.letv.portal.model.slb.SlbBackendServer;
import com.letv.portal.model.task.service.ITaskEngine;
import com.letv.portal.proxy.ISlbBackendProxy;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.slb.ISlbBackendServerService;

@Component
public class SlbBackendProxyImpl extends BaseProxyImpl<SlbBackendServer> implements
		ISlbBackendProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(SlbBackendProxyImpl.class);
	
	@Autowired
	private ISlbBackendServerService slbBackendServerService;
	@Autowired
	private ITaskEngine taskEngine;
	
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Value("${db.auto.build.count}")
	private int DB_AUTO_BUILD_COUNT;

	@Override
	public void saveAndConfig(SlbBackendServer slbBackendServer) {
		Config(slbBackendServer);
		slbBackendServer.setStatus(SlbBackendStatus.NOWORK.getValue());
		this.slbBackendServerService.insert(slbBackendServer);
	}

	private void Config(SlbBackendServer slbBackendServer) {
		
	}
	
	@Override
	public IBaseService<SlbBackendServer> getService() {
		return slbBackendServerService;
	}
	
}
