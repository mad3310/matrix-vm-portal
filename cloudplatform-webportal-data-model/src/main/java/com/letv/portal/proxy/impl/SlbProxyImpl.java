package com.letv.portal.proxy.impl;

import java.util.ArrayList;
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
import com.letv.portal.enumeration.SlbStatus;
import com.letv.portal.model.slb.SlbBackendServer;
import com.letv.portal.model.slb.SlbCluster;
import com.letv.portal.model.slb.SlbConfig;
import com.letv.portal.model.slb.SlbContainer;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
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
	@Async
	public void restart(Long id) {
		SlbServer slb = this.selectById(id);
		slb.setStatus(SlbStatus.STARTING.getValue());
		this.slbServerService.updateBySelective(slb);
		
		SlbCluster cluster = this.slbClusterService.selectById(slb.getSlbClusterId());
		List<SlbContainer> containers = this.slbContainerService.selectBySlbClusterId(cluster.getId());
		
		this.commitProxyConfig(slb,cluster,containers);
//		this.stop(slb,cluster,containers);
		this.start(slb,cluster,containers);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		this.checkStart(slb,cluster,containers);
		
		slb.setStatus(SlbStatus.NORMAL.getValue());
		this.slbServerService.updateBySelective(slb);
	}
	
	private boolean commitProxyConfig(SlbServer slb,SlbCluster cluster,List<SlbContainer> containers) {
		List<Map<String,String>> params = getProxyConfig(slb);
		
		TaskResult tr = new TaskResult();
		for (int i = 0; i < containers.size(); i++) {
			SlbContainer container = containers.get(i);
			for (Map<String,String> param : params) {
				if(i > 0)
					param.put("state", "BACKUP");
				String result = this.slbPythonService.commitProxyConfig(param, container.getIpAddr(), cluster.getAdminUser(), cluster.getAdminPassword());
				tr = this.baseSlbTaskService.analyzeRestServiceResult(result);
				if(!tr.isSuccess()) {
					slb.setStatus(SlbStatus.BUILDFAIL.getValue());
					this.slbServerService.updateBySelective(slb);
					throw new TaskExecuteException("SLB service commit porxyConfig error:" + tr.getResult());
				}
			}
		}
		return tr.isSuccess();
	}
	private boolean stop(SlbServer slb,SlbCluster cluster,List<SlbContainer> containers) {
		String result = this.slbPythonService.stop(null,containers.get(0).getIpAddr(), cluster.getAdminUser(), cluster.getAdminPassword());
		TaskResult tr = this.baseSlbTaskService.analyzeRestServiceResult(result);
		if(!tr.isSuccess()) {
			slb.setStatus(SlbStatus.BUILDFAIL.getValue());
			this.slbServerService.updateBySelective(slb);
			throw new TaskExecuteException("SLB service stop error:" + tr.getResult());
		}
		return tr.isSuccess();
	}
	private boolean start(SlbServer slb,SlbCluster cluster,List<SlbContainer> containers) {
		String result = this.slbPythonService.start(null,containers.get(0).getIpAddr(), cluster.getAdminUser(), cluster.getAdminPassword());
		TaskResult tr = this.baseSlbTaskService.analyzeRestServiceResult(result);
		if(!tr.isSuccess()) {
			slb.setStatus(SlbStatus.BUILDFAIL.getValue());
			this.slbServerService.updateBySelective(slb);
			throw new TaskExecuteException("SLB service start error:" + tr.getResult());
		}
		return tr.isSuccess();
	}
	private boolean checkStart(SlbServer slb,SlbCluster cluster,List<SlbContainer> containers) {
		String result = this.slbPythonService.checkStart(containers.get(0).getIpAddr(), cluster.getAdminUser(), cluster.getAdminPassword());
		TaskResult tr = this.baseSlbTaskService.analyzeRestServiceResult(result);
		if(!tr.isSuccess()) {
			slb.setStatus(SlbStatus.BUILDFAIL.getValue());
			this.slbServerService.updateBySelective(slb);
			throw new TaskExecuteException("SLB service check start error:" + tr.getResult());
			
		}
			
		return tr.isSuccess();
	}
	
	private List<Map<String,String>> getProxyConfig(SlbServer slb) {
		List<SlbConfig> configs = this.slbConfigService.selectBySlbServerId(slb.getId());
		
		List<Map<String,String>> params = new ArrayList<Map<String,String>>();
		List<SlbBackendServer> backendServers = null;
		
		for (SlbConfig config : configs) {
			backendServers = this.slbBackendServerService.selectBySlbConfigId(config.getId());
			if(backendServers !=null && !backendServers.isEmpty()) {
				Map<String,String> param = new HashMap<String,String>();
				param.put("service", config.getAgentType().toString().toLowerCase());
				param.put("port", config.getFrontPort());
				param.put("vip", slb.getIp());
				param.put("addr", "0.0.0.0");
				param.put("state", "MASTER");
				StringBuffer sb = new StringBuffer();
				for (SlbBackendServer backendServer : backendServers) {
					sb.append(backendServer.getServerIp()).append(":").append(backendServer.getPort()).append(",");
				}
				param.put("iplist_port", sb.length()>0?sb.substring(0, sb.length()-1):sb.toString());
				params.add(param);
			}
			
		}
		return params;
	}
	
}
