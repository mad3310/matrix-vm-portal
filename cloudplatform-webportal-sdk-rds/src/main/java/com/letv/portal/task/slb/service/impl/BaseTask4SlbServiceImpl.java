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
import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.model.gce.GceServer;
import com.letv.portal.model.slb.SlbCluster;
import com.letv.portal.model.slb.SlbContainer;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.slb.ISlbClusterService;
import com.letv.portal.service.slb.ISlbContainerService;
import com.letv.portal.service.slb.ISlbServerService;

@Component("baseSlbTaskService")
public class BaseTask4SlbServiceImpl implements IBaseTaskService{

	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
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
		//发送邮件
		this.buildResultToMgr("Slb服务创建", tr.isSuccess()?"创建成功":"创建失败", tr.getResult(), ERROR_MAIL_ADDRESS);
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
			emailParams.put("gceName", slb.getSlbName());
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

	@SuppressWarnings("unchecked")
	public TaskResult analyzeRestServiceResult(String result) throws Exception{
		TaskResult tr = new TaskResult();
		Map<String, Object> map = transToMap(result);
		if(map == null) {
			tr.setSuccess(false);
			tr.setResult("api connect failed");
			return tr;
		}
		Map<String,Object> meta = (Map<String, Object>) map.get("meta");
		
		boolean isSucess = Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")));
		tr.setSuccess(isSucess);
		if(isSucess) {
			Map<String,Object> response = (Map<String, Object>) map.get("response");
			tr.setResult((String) response.get("message"));
		} else {
			tr.setResult((String) meta.get("errorType") +":"+ (String) meta.get("errorDetail"));
		}
		return tr;
		
	}
	
	public void buildResultToMgr(String buildType,String result,String detail,String to){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("buildType", buildType);
		map.put("buildResult", result);
		map.put("errorDetail", detail);
		MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统", StringUtils.isEmpty(to)?ERROR_MAIL_ADDRESS:to,"乐视云平台web-portal系统通知","buildForMgr.ftl",map);
		defaultEmailSender.sendMessage(mailMessage);
	}
	public void email4User(Map<String,Object> params,Long to,String ftlName){
		UserModel user = this.userService.selectById(to);
		if(null != user) {
			MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",user.getEmail(),"乐视云平台web-portal系统通知",ftlName,params);
			mailMessage.setHtml(true);
			defaultEmailSender.sendMessage(mailMessage);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> transToMap(String params){
		if(StringUtils.isEmpty(params))
			return null;
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		try {
			jsonResult = resultMapper.readValue(params, Map.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	public String transToString(Object params){
		if(params == null)
			return null;
		ObjectMapper resultMapper = new ObjectMapper();
		String jsonResult = "";
		try {
			jsonResult = resultMapper.writeValueAsString(params);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	public Long getLongFromObject(Object o) {
		Long value = null;
		if(o instanceof String)
			value = Long.parseLong((String) o);
		if(o instanceof Integer)
			value = Long.parseLong(((Integer)o).toString());
		if(o instanceof Long)
			value = (Long) o;
		
		return value;
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
	
}
