package com.letv.portal.proxy.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.portal.model.slb.SlbBackendServer;
import com.letv.portal.model.slb.SlbCluster;
import com.letv.portal.model.slb.SlbConfig;
import com.letv.portal.model.slb.SlbContainer;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.model.task.service.ITaskEngine;
import com.letv.portal.proxy.ISlbProxy;
import com.letv.portal.python.service.ISlbPythonService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.slb.ISlbBackendServerService;
import com.letv.portal.service.slb.ISlbClusterService;
import com.letv.portal.service.slb.ISlbConfigService;
import com.letv.portal.service.slb.ISlbContainerService;
import com.letv.portal.service.slb.ISlbServerService;

@Component
public class SlbProxyImpl extends BaseProxyImpl<SlbServer> implements
		ISlbProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(SlbProxyImpl.class);
	
	@Autowired
	private ISlbServerService slbServerService;
	@Autowired
	private ISlbClusterService slbClusterService;
	@Autowired
	private ISlbContainerService slbContainerService;
	@Autowired
	private ISlbPythonService slbPythonService;
	@Autowired
	private ISlbConfigService slbConfigService;
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
	public void saveAndBuild(SlbServer slbServer) {
		Map<String,Object> params = this.slbServerService.save(slbServer);
		this.build(params);
	}

	private void build(Map<String,Object> params) {
    	this.taskEngine.run("SLB_BUY",params);
	}
	
	@Override
	public IBaseService<SlbServer> getService() {
		return slbServerService;
	}

	@Override
	public void restart(Long id) {
		SlbServer slb = this.selectById(id);
		SlbCluster cluster = this.slbClusterService.selectById(slb.getSlbClusterId());
		List<SlbContainer> containers = this.slbContainerService.selectBySlbClusterId(cluster.getId());
		SlbContainer contaienr = containers.get(0);

		List<SlbConfig> configs = this.slbConfigService.selectBySlbServerId(id);
		
		List<SlbBackendServer> backendServers = this.slbBackendServerService.selectBySlbServerId(id);
		
		for (SlbBackendServer slbBackendServer : backendServers) {
			
		}
		
		String result =this.slbPythonService.commitProxyConfig(null,contaienr.getIpAddr(),cluster.getAdminUser(),cluster.getAdminPassword());
		result = this.slbPythonService.start(null,contaienr.getIpAddr(),cluster.getAdminUser(),cluster.getAdminPassword());
		result = this.slbPythonService.checkStart(contaienr.getIpAddr(),cluster.getAdminUser(),cluster.getAdminPassword());
		
	}
	
}
