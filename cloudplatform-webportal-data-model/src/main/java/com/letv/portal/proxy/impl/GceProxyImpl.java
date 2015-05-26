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
import com.letv.portal.enumeration.GceType;
import com.letv.portal.enumeration.SlbStatus;
import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.model.gce.GceContainer;
import com.letv.portal.model.gce.GceServer;
import com.letv.portal.model.log.LogServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.model.task.service.ITaskEngine;
import com.letv.portal.proxy.IGceProxy;
import com.letv.portal.python.service.IGcePythonService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.gce.IGceClusterService;
import com.letv.portal.service.gce.IGceContainerService;
import com.letv.portal.service.gce.IGceServerService;
import com.letv.portal.service.log.ILogServerService;

@Component
public class GceProxyImpl extends BaseProxyImpl<GceServer> implements
		IGceProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(GceProxyImpl.class);
	
	@Autowired
	private IGceServerService gceServerService;
	@Autowired
	private IGcePythonService gcePythonService;
	@Autowired
	private IGceClusterService gceClusterService;
	@Autowired
	private IGceContainerService gceContainerService;
	@Autowired
	private ILogServerService logServerService;
	@Autowired
	private IBaseTaskService baseGceTaskService;
	@Autowired
	private ITaskEngine taskEngine;
	
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Value("${db.auto.build.count}")
	private int DB_AUTO_BUILD_COUNT;
	@Value("${nginx4jetty.code}")
	private String NGINX4JETTY_CODE;
	@Override
	public void saveAndBuild(GceServer gceServer) {
		if(gceServer == null)
			throw new ValidateException("参数不合法");
		
		Map<String,Object> params = this.gceServerService.save(gceServer);
		Map<String,Object> nextParams = new HashMap<String,Object>();
		
		//create logstash
		/*LogServer log = new LogServer();
		log.setLogName(gceServer.getGceName());
		log.setHclusterId(gceServer.getHclusterId());
		log.setCreateUser(gceServer.getCreateUser());
		log.setType("logstash");
		Map<String,Object> logParams = this.logServerService.save(log);
		params.put("logParams", logParams);
		params.put("isCreateLog", true);*/
		params.put("isConfig", false);
		
		if(GceType.JETTY.equals(gceServer.getType())) {
			gceServer.setType(GceType.NGINX_PROXY);
			gceServer.setGceName(NGINX4JETTY_CODE+"_" + gceServer.getGceName());
			gceServer.setGceImageName("");
			nextParams = this.gceServerService.save(gceServer);
			nextParams.put("isContinue", false);
			nextParams.put("isConfig", true);
			nextParams.put("pGceId", params.get("gceId"));
			nextParams.put("pGceClusterId", params.get("gceClusterId"));
			/*nextParams.put("logIp", log.getIp());
			nextParams.put("logParams", logParams);
			nextParams.put("isCreateLog", false);*/
			params.put("isContinue", true);
			params.put("nextParams", nextParams);
		} else {
			params.put("isContinue", false);
		}
		this.build(params);
	}

	public void delete(GceServer gceServer) {
		GceServer gceProxyServer=this.gceServerService. selectProxyServerByGce(gceServer);
		if(gceProxyServer != null){
			this.delete(gceProxyServer);
		}
		
		this.gceServerService.delete(gceServer);
	}
	
	private void build(Map<String,Object> params) {
    	this.taskEngine.run("GCE_BUY",params);
//    	this.taskEngine.run("GCE_BUY_EXT",params);
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
		this.restart(gce,cluster,containers);
		this.checkStatus(gce, cluster, containers,"STARTED","GCE服务重启失败");
	}
	@Override
	@Async
	public void start(Long id) {
		GceServer gce = this.selectById(id);
		gce.setStatus(SlbStatus.STARTING.getValue());
		this.gceServerService.updateBySelective(gce);
		
		GceCluster cluster = this.gceClusterService.selectById(gce.getGceClusterId());
		List<GceContainer> containers = this.gceContainerService.selectByGceClusterId(cluster.getId());
		this.start(gce,cluster,containers);
		this.checkStatus(gce, cluster, containers,"STARTED","GCE服务启动失败");
	}
	@Override
	@Async
	public void stop(Long id) {
		GceServer gce = this.selectById(id);
		gce.setStatus(SlbStatus.STOPPING.getValue());
		this.gceServerService.updateBySelective(gce);
		
		GceCluster cluster = this.gceClusterService.selectById(gce.getGceClusterId());
		List<GceContainer> containers = this.gceContainerService.selectByGceClusterId(cluster.getId());
		this.stop(gce,cluster,containers);
		this.checkStatus(gce, cluster, containers,"STOP","GCE服务停止失败");
	}
	
	private boolean restart(GceServer slb,GceCluster cluster,List<GceContainer> containers) {
		String result = this.gcePythonService.restart(null,containers.get(0).getHostIp(),containers.get(0).getMgrBindHostPort(),cluster.getAdminUser(), cluster.getAdminPassword());
		TaskResult tr = this.baseGceTaskService.analyzeRestServiceResult(result);
		if(!tr.isSuccess()) {
			slb.setStatus(SlbStatus.ABNORMAL.getValue());
			this.gceServerService.updateBySelective(slb);
			throw new TaskExecuteException("SLB service restart error:" + tr.getResult());
		}
		return tr.isSuccess();
	}
	private boolean stop(GceServer slb,GceCluster cluster,List<GceContainer> containers) {
		String result = this.gcePythonService.stop(null,containers.get(0).getHostIp(),containers.get(0).getMgrBindHostPort(),cluster.getAdminUser(), cluster.getAdminPassword());
		TaskResult tr = this.baseGceTaskService.analyzeRestServiceResult(result);
		if(!tr.isSuccess()) {
			slb.setStatus(SlbStatus.ABNORMAL.getValue());
			this.gceServerService.updateBySelective(slb);
			throw new TaskExecuteException("GCE service stop error:" + tr.getResult());
		}
		return tr.isSuccess();
	}
	private boolean start(GceServer gce,GceCluster cluster,List<GceContainer> containers) {
		String result = this.gcePythonService.start(null,containers.get(0).getHostIp(),containers.get(0).getMgrBindHostPort(), cluster.getAdminUser(), cluster.getAdminPassword());
		TaskResult tr = this.baseGceTaskService.analyzeRestServiceResult(result);
		if(!tr.isSuccess()) {
			gce.setStatus(SlbStatus.ABNORMAL.getValue());
			this.gceServerService.updateBySelective(gce);
			throw new TaskExecuteException("GCE service start error:" + tr.getResult());
		}
		return tr.isSuccess();
	}
	private String checkStatus(GceServer gce,GceCluster cluster,List<GceContainer> containers) {
		 TaskResult tr = new TaskResult();
		String result = this.gcePythonService.checkStatus(containers.get(0).getHostIp(),containers.get(0).getMgrBindHostPort(), cluster.getAdminUser(), cluster.getAdminPassword());
		tr = this.baseGceTaskService.analyzeRestServiceResult(result);
		if(!tr.isSuccess()) {
			gce.setStatus(SlbStatus.ABNORMAL.getValue());
			this.gceServerService.updateBySelective(gce);
			throw new TaskExecuteException("GCE service check start error:" + tr.getResult());
		}
		Map<String,Object> params = (Map<String, Object>) tr.getParams();
		return (String) ((Map<String,Object>)params.get("data")).get("status");
	}
	private void checkStatus(GceServer gce,GceCluster cluster,List<GceContainer> containers,String expectStatus,String exception) {
		String status = "";
		for (int i = 0; i < 3; i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			status = this.checkStatus(gce,cluster,containers);
			if(expectStatus.equals(status))
				break;
		}
		if("".equals(status))
			throw new TaskExecuteException(exception);
		if("STARTED".equals(status))
			gce.setStatus(SlbStatus.NORMAL.getValue());
		if("STOP".equals(status))
			gce.setStatus(SlbStatus.STOPED.getValue());
		this.gceServerService.updateBySelective(gce);
	}
}
