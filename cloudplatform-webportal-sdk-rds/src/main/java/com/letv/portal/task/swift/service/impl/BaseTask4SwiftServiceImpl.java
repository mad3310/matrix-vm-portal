package com.letv.portal.task.swift.service.impl;

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
import com.letv.portal.model.swift.SwiftServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.BaseTaskServiceImpl;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.swift.ISwiftServerService;
import com.letv.portal.task.log.service.impl.BaseTask4LogServiceImpl;

@Component("baseSwiftTaskService")
public class BaseTask4SwiftServiceImpl extends BaseTaskServiceImpl implements IBaseTaskService{

	@Value("${service.notice.email.to}")
	private String SERVICE_NOTICE_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Autowired
	private IHostService hostService;
	@Autowired
	private ISwiftServerService swiftServerService;
	@Autowired
	private IUserService userService;
	
	private final static Logger logger = LoggerFactory.getLogger(BaseTask4LogServiceImpl.class);
	
	@Override
	public void beforExecute(Map<String, Object> params) {
		SwiftServer swift = this.getServer(params);
		if(swift.getStatus() != DbStatus.BUILDDING.getValue()) {
			swift.setStatus(DbStatus.BUILDDING.getValue());
			this.swiftServerService.updateBySelective(swift);
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
		this.buildResultToMgr("Swift服务("+serverName+")创建", tr.isSuccess()?"成功":"失败", tr.getResult(), SERVICE_NOTICE_MAIL_ADDRESS);
		//业务处理
		this.serviceOver(tr);
	}
	
	private void serviceOver(TaskResult tr) {
		Map<String, Object> params = (Map<String, Object>) tr.getParams();
		SwiftServer swift = this.getServer(params);
		
		if(tr.isSuccess()) {
			swift.setStatus(DbStatus.NORMAL.getValue());
			Map<String, Object> emailParams = new HashMap<String,Object>();
			emailParams.put("swiftName", swift.getName());
			emailParams.put("storeSize", swift.getStoreSize() + "GB");
			emailParams.put("visibilityLevel", swift.getVisibilityLevel());
			emailParams.put("area", "北京");
			emailParams.put("hclusterNameAlias", swift.getHcluster().getHclusterNameAlias());
			emailParams.put("createTime", swift.getCreateTime());
			this.email4User(emailParams, swift.getCreateUser(),"swift/createSwift.ftl");
		} else {
			swift.setStatus(DbStatus.BUILDFAIL.getValue());
		}
		this.swiftServerService.updateBySelective(swift);
	}

	@Override
	public void callBack(TaskResult tr) {
		
	}

	public SwiftServer getServer(Map<String, Object> params) {
		Long swiftId = getLongFromObject(params.get("swiftId"));
		if(swiftId == null)
			throw new ValidateException("params's swiftId is null");
		
		SwiftServer swift = this.swiftServerService.selectById(swiftId);
		if(swift == null)
			throw new ValidateException("SwiftServer is null by swiftId:" + swiftId);
		
		return swift;
	}
	
	public HostModel getHost(Long hclusterId) {
		if(hclusterId == null)
			throw new ValidateException("hclusterId is null :" + hclusterId);
		HostModel host = this.hostService.getHostByHclusterId(hclusterId);
		if(host == null)
			throw new ValidateException("host is null by hclusterIdId:" + hclusterId);
		
		return host;
	}

}
