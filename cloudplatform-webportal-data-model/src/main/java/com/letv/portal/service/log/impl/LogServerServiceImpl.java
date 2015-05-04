package com.letv.portal.service.log.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.log.ILogServerDao;
import com.letv.portal.enumeration.LogStatus;
import com.letv.portal.model.log.LogCluster;
import com.letv.portal.model.log.LogContainer;
import com.letv.portal.model.log.LogServer;
import com.letv.portal.service.log.ILogClusterService;
import com.letv.portal.service.log.ILogContainerService;
import com.letv.portal.service.log.ILogServerService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("logServerService")
public class LogServerServiceImpl extends BaseServiceImpl<LogServer> implements ILogServerService{
	
	private final static Logger logger = LoggerFactory.getLogger(LogServerServiceImpl.class);
	
	@Resource
	private ILogServerDao logServerDao;
	@Autowired
	private ILogClusterService logClusterService;
	@Autowired
	private ILogContainerService logContainerService;
	
	public LogServerServiceImpl() {
		super(LogServer.class);
	}

	@Override
	public IBaseDao<LogServer> getDao() {
		return this.logServerDao;
	}
	
	@Override
	public void insert(LogServer t) {
		if(t == null)
			throw new ValidateException("参数不合法");
		t.setStatus(LogStatus.NORMAL.getValue());
		super.insert(t);
	}

	@Override
	public Map<String,Object> save(LogServer logServer) {
		logServer.setStatus(LogStatus.BUILDDING.getValue());
		
		StringBuffer clusterName = new StringBuffer();
		clusterName.append(logServer.getCreateUser()).append("_").append(logServer.getLogName());
		
		/*function 验证clusterName是否存在*/
		Boolean isExist= this.logClusterService.isExistByName(clusterName.toString());
		int i = 0;
		while(!isExist) {
			isExist= this.logClusterService.isExistByName(clusterName.toString() +(i+1));
			i++;
		}
		if(i>0)
			clusterName.append(i);
		
		LogCluster logCluster = new LogCluster();
		logCluster.setHclusterId(logServer.getHclusterId());
		logCluster.setClusterName(clusterName.toString());
		logCluster.setStatus(LogStatus.BUILDDING.getValue());
		logCluster.setCreateUser(logServer.getCreateUser());
		logCluster.setAdminUser("root");
		logCluster.setAdminPassword(clusterName.toString());
		
		this.logClusterService.insert(logCluster);
		
		logServer.setLogClusterId(logCluster.getId());
		
		this.logServerDao.insert(logServer);
		
		Map<String,Object> params = new HashMap<String,Object>();
    	params.put("logClusterId", logCluster.getId());
    	params.put("logId", logServer.getId());
    	params.put("serviceName", logServer.getLogName());
    	params.put("clusterName", logCluster.getClusterName());
		return params;
	}
	
	public <K, V> Page selectPageByParams(Page page, Map<K, V> params){
		page = super.selectPageByParams(page, params);
		List<LogServer> logServers = (List<LogServer>) page.getData();
		
		for(LogServer logServer : logServers){
			List<LogContainer> logContainers = this.logContainerService.selectByLogClusterId(logServer.getLogClusterId());
			logServer.setLogContainers(logContainers);
		}
		page.setData(logServers);
		return page;
	}
	
	public LogServer selectById(Long id){
		LogServer logServer = this.logServerDao.selectById(id);
		List<LogContainer> logContainers = this.logContainerService.selectByLogClusterId(logServer.getLogClusterId());
		logServer.setLogContainers(logContainers);
		return logServer;
	}
}
