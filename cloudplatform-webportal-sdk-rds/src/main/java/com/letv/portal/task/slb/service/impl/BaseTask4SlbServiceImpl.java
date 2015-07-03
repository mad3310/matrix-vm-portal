package com.letv.portal.task.slb.service.impl;

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
import com.letv.portal.model.slb.SlbCluster;
import com.letv.portal.model.slb.SlbContainer;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.BaseTaskServiceImpl;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.common.IZookeeperInfoService;
import com.letv.portal.service.slb.ISlbClusterService;
import com.letv.portal.service.slb.ISlbContainerService;
import com.letv.portal.service.slb.ISlbServerService;

@Component("baseSlbTaskService")
public class BaseTask4SlbServiceImpl extends BaseTaskServiceImpl implements IBaseTaskService{

	@Value("${service.notice.email.to}")
	private String SERVICE_NOTICE_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Autowired
	private IHostService hostService;
	@Autowired
	private ISlbClusterService slbClusterService;
	@Autowired
	private ISlbServerService slbServerService;
	@Autowired
	private ISlbContainerService slbContainerService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IZookeeperInfoService zookeeperInfoService;
	
	private final static Logger logger = LoggerFactory.getLogger(BaseTask4SlbServiceImpl.class);
	
	@Override
	public void beforExecute(Map<String, Object> params) {
		SlbServer slb = this.getServer(params);
		SlbCluster cluster = this.getCluster(params);
		if(slb.getStatus() != DbStatus.BUILDDING.getValue()) {
			slb.setStatus(DbStatus.BUILDDING.getValue());
			this.slbServerService.updateBySelective(slb);
		}
		if(slb.getStatus() != MclusterStatus.BUILDDING.getValue()) {
			cluster.setStatus(MclusterStatus.BUILDDING.getValue());
			this.slbClusterService.updateBySelective(cluster);
		}
	}
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = new TaskResult();
		if(params == null || params.isEmpty()) {
			tr.setResult("params is empty");
			tr.setSuccess(false);
		}
		tr.setParams(params);
		return tr;
	}

	@Override
	public void rollBack(TaskResult tr) {
		String serverName = "";
		if(tr.getParams() !=null)
			serverName =  (String) ((Map<String, Object>) tr.getParams()).get("serviceName");
		//发送邮件
		this.buildResultToMgr("Slb服务("+serverName+")创建", tr.isSuccess()?"成功":"失败", tr.getResult(), SERVICE_NOTICE_MAIL_ADDRESS);
		//业务处理
		this.serviceOver(tr);
	}
	
	private void serviceOver(TaskResult tr) {
		Map<String, Object> params = (Map<String, Object>) tr.getParams();
		SlbServer slb = this.getServer(params);
		SlbCluster cluster = this.getCluster(params);
		
		if(tr.isSuccess()) {
			slb.setStatus(DbStatus.NORMAL.getValue());
			cluster.setStatus(MclusterStatus.RUNNING.getValue());
			Map<String, Object> emailParams = new HashMap<String,Object>();
			emailParams.put("slbName", slb.getSlbName());
			this.email4User(emailParams, slb.getCreateUser(),"slb/createSlb.ftl");
		} else {
			slb.setStatus(DbStatus.BUILDFAIL.getValue());
			cluster.setStatus(MclusterStatus.BUILDFAIL.getValue());
		}
		this.slbServerService.updateBySelective(slb);
		this.slbClusterService.updateBySelective(cluster);
	}

	@Override
	public void callBack(TaskResult tr) {
		
	}

	public SlbServer getServer(Map<String, Object> params) {
		Long gceId = getLongFromObject(params.get("slbId"));
		if(gceId == null)
			throw new ValidateException("params's slbId is null");
		
		SlbServer slbServer = this.slbServerService.selectById(gceId);
		if(slbServer == null)
			throw new ValidateException("slbServer is null by gceId:" + gceId);
		
		return slbServer;
	}
	
	public SlbCluster getCluster(Map<String, Object> params) {
		Long slbClusterId = getLongFromObject(params.get("slbClusterId"));
		if(slbClusterId == null)
			throw new ValidateException("params's slbClusterId is null");
		
		SlbCluster slbCluster = this.slbClusterService.selectById(slbClusterId);
		if(slbCluster == null)
			throw new ValidateException("slbCluster is null by slbClusterId:" + slbClusterId);
		
		return slbCluster;
	}
	
	public HostModel getHost(Long hclusterId) {
		if(hclusterId == null)
			throw new ValidateException("hclusterId is null :" + hclusterId);
		HostModel host = this.hostService.getHostByHclusterId(hclusterId);
		if(host == null)
			throw new ValidateException("host is null by hclusterIdId:" + hclusterId);
		
		return host;
	}
	public List<SlbContainer> getContainers(Map<String, Object> params) {
		Long slbClusterId = getLongFromObject(params.get("slbClusterId"));
		if(slbClusterId == null)
			throw new ValidateException("params's slbClusterId is null");
		
		List<SlbContainer> gceContainers = this.slbContainerService.selectBySlbClusterId(slbClusterId);
		if(gceContainers == null || gceContainers.isEmpty())
			throw new ValidateException("slbCluster is null by slbClusterId:" + slbClusterId);
		return gceContainers;
	}
	
	public ZookeeperInfo getMinusedZk() {
		ZookeeperInfo zk = this.zookeeperInfoService.selectMinusedZk();
		if(zk == null)
			throw new ValidateException("build GCE service error:zk is null");
		zk.setUsed(zk.getUsed()+1);
		this.zookeeperInfoService.updateBySelective(zk);
		return zk;
	}
}
