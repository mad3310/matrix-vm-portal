package com.letv.portal.task.log.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.exception.ValidateException;
import com.letv.portal.constant.Constant;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.common.ZookeeperInfo;
import com.letv.portal.model.log.LogCluster;
import com.letv.portal.model.log.LogContainer;
import com.letv.portal.model.log.LogServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.BaseTaskServiceImpl;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.common.IZookeeperInfoService;
import com.letv.portal.service.log.ILogClusterService;
import com.letv.portal.service.log.ILogContainerService;
import com.letv.portal.service.log.ILogServerService;

@Component("baseLogTaskService")
public class BaseTask4LogServiceImpl extends BaseTaskServiceImpl implements IBaseTaskService{

	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Autowired
	private IHostService hostService;
	@Autowired
	private ILogClusterService LogClusterService;
	@Autowired
	private ILogServerService LogServerService;
	@Autowired
	private ILogContainerService LogContainerService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IZookeeperInfoService zookeeperInfoService;
	
	private final static Logger logger = LoggerFactory.getLogger(BaseTask4LogServiceImpl.class);
	
	@Override
	public void beforExecute(Map<String, Object> params) {
		LogServer log = this.getLogServer(params);
		LogCluster cluster = this.getLogCluster(params);
		if(log.getStatus() != DbStatus.BUILDDING.getValue()) {
			log.setStatus(DbStatus.BUILDDING.getValue());
			this.LogServerService.updateBySelective(log);
		}
		if(log.getStatus() != MclusterStatus.BUILDDING.getValue()) {
			cluster.setStatus(MclusterStatus.BUILDDING.getValue());
			this.LogClusterService.updateBySelective(cluster);
		}
	}
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = new TaskResult();
		if(params == null || params.isEmpty() || params.get("logParams") == null || params.get("isCreateLog") == null) {
			tr.setResult("params is not valid.");
			tr.setSuccess(false);
		}
		tr.setParams(params);
		return tr;
	}

	@Override
	public void rollBack(TaskResult tr) {
		Map<String,Object> params = (Map<String, Object>) tr.getParams();
		//发送邮件
		this.buildResultToMgr("log服务创建", tr.isSuccess()?"创建成功":"创建失败", tr.getResult(), ERROR_MAIL_ADDRESS);
		//业务处理
		this.serviceOver(tr);
	}
	
	private void serviceOver(TaskResult tr) {
		Map<String, Object> params = (Map<String, Object>) tr.getParams();
		LogServer log = this.getLogServer(params);
		LogCluster cluster = this.getLogCluster(params);
		
		if(tr.isSuccess()) {
			log.setStatus(DbStatus.NORMAL.getValue());
			cluster.setStatus(MclusterStatus.RUNNING.getValue());
			Map<String, Object> emailParams = new HashMap<String,Object>();
			emailParams.put("logName", log.getLogName());
			this.email4User(emailParams, log.getCreateUser(),"log/createLog.ftl");
		} else {
			log.setStatus(DbStatus.BUILDFAIL.getValue());
			cluster.setStatus(MclusterStatus.BUILDFAIL.getValue());
		}
		this.LogServerService.updateBySelective(log);
		this.LogClusterService.updateBySelective(cluster);
	}

	@Override
	public void callBack(TaskResult tr) {
		
	}

	public LogServer getLogServer(Map<String, Object> params) {
		Map<String, Object> logParams = (Map<String, Object>) params.get("logParams");
		Long logId = getLongFromObject(logParams.get("logId"));
		if(logId == null)
			throw new ValidateException("params's logId is null");
		
		LogServer LogServer = this.LogServerService.selectById(logId);
		if(LogServer == null)
			throw new ValidateException("LogServer is null by logId:" + logId);
		
		return LogServer;
	}
	
	public LogCluster getLogCluster(Map<String, Object> params) {
		Map<String, Object> logParams = (Map<String, Object>) params.get("logParams");
		Long logClusterId = getLongFromObject(logParams.get("logClusterId"));
		if(logClusterId == null)
			throw new ValidateException("params's logClusterId is null");
		
		LogCluster logCluster = this.LogClusterService.selectById(logClusterId);
		if(logCluster == null)
			throw new ValidateException("logCluster is null by logClusterId:" + logClusterId);
		
		return logCluster;
	}
	
	public HostModel getHost(Long hclusterId) {
		if(hclusterId == null)
			throw new ValidateException("hclusterId is null :" + hclusterId);
		HostModel host = this.hostService.getHostByHclusterId(hclusterId);
		if(host == null)
			throw new ValidateException("host is null by hclusterIdId:" + hclusterId);
		
		return host;
	}
	public List<LogContainer> getContainers(Map<String, Object> params) {
		Map<String, Object> logParams = (Map<String, Object>) params.get("logParams");
		Long LogClusterId = getLongFromObject(logParams.get("logClusterId"));
		if(LogClusterId == null)
			throw new ValidateException("params's logClusterId is null");
		
		List<LogContainer> logContainers = this.LogContainerService.selectByLogClusterId(LogClusterId);
		if(logContainers == null || logContainers.isEmpty())
			throw new ValidateException("logContainers is null by logClusterId:" + LogClusterId);
		return logContainers;
	}
	
	public ZookeeperInfo getMinusedZk() {
		ZookeeperInfo zk = this.zookeeperInfoService.selectMinusedZk();
		if(zk == null)
			throw new ValidateException("zk is null");
		zk.setUsed(zk.getUsed()+1);
		this.zookeeperInfoService.updateBySelective(zk);
		return zk;
	}
}
