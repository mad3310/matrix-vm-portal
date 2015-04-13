package com.letv.portal.proxy.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.exception.TaskExecuteException;
import com.letv.common.exception.ValidateException;
import com.letv.portal.enumeration.SlbStatus;
import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.model.gce.GceContainer;
import com.letv.portal.model.gce.GceServer;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.model.task.service.ITaskEngine;
import com.letv.portal.proxy.IGceProxy;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.gce.IGceClusterService;
import com.letv.portal.service.gce.IGceContainerService;
import com.letv.portal.service.gce.IGceServerService;

@Component
public class GceProxyImpl extends BaseProxyImpl<GceServer> implements
		IGceProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(GceProxyImpl.class);
	
	@Autowired
	private IGceServerService gceServerService;
	@Autowired
	private IGceClusterService gceClusterService;
	@Autowired
	private IGceContainerService gceContainerService;
	@Autowired
	private ITaskEngine taskEngine;
	
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Value("${db.auto.build.count}")
	private int DB_AUTO_BUILD_COUNT;

	@Override
	public void saveAndBuild(GceServer gceServer) {
		if(gceServer == null)
			throw new ValidateException("参数不合法");
		Map<String,Object> params = this.gceServerService.save(gceServer);
		Map<String,Object> nextParams = new HashMap<String,Object>();
		params.put("isConfig", false);
		if("jetty".equals(gceServer.getType())) {
			gceServer.setType("nginx");
			gceServer.setGceName("n4j_" + gceServer.getGceName());
			nextParams = this.gceServerService.save(gceServer);
			nextParams.put("isContinue", false);
			nextParams.put("isConfig", true);
			nextParams.put("pGceId", params.get("gceId"));
			nextParams.put("pGceClusterId", params.get("gceClusterId"));
			
			params.put("isContinue", true);
			params.put("nextParams", nextParams);
		} else {
			params.put("isContinue", false);
		}
		this.build(params);
	}

	private void build(Map<String,Object> params) {
    	this.taskEngine.run("GCE_BUY",params);
	}
	
	@Override
	public IBaseService<GceServer> getService() {
		return gceServerService;
	}
	
	@Override
	@Async
	public void restart(Long id) {
		GceServer gce = this.selectById(id);
		gce.setStatus(SlbStatus.STARTING.getValue());
		this.gceServerService.updateBySelective(gce);
		
		GceCluster cluster = this.gceClusterService.selectById(gce.getGceClusterId());
		List<GceContainer> containers = this.gceContainerService.selectByGceClusterId(cluster.getId());
		
	}
	@Override
	@Async
	public void start(Long id) {
		GceServer gce = this.selectById(id);
		gce.setStatus(SlbStatus.STARTING.getValue());
		this.gceServerService.updateBySelective(gce);
		
		GceCluster cluster = this.gceClusterService.selectById(gce.getGceClusterId());
		List<GceContainer> containers = this.gceContainerService.selectByGceClusterId(cluster.getId());
	}
	@Override
	@Async
	public void stop(Long id) {
		GceServer gce = this.selectById(id);
		gce.setStatus(SlbStatus.STARTING.getValue());
		this.gceServerService.updateBySelective(gce);
		
		GceCluster cluster = this.gceClusterService.selectById(gce.getGceClusterId());
		List<GceContainer> containers = this.gceContainerService.selectByGceClusterId(cluster.getId());
	}
	
}
