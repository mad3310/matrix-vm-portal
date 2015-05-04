package com.letv.portal.task.gce.service.impl;

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
import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.model.gce.GceContainer;
import com.letv.portal.model.gce.GceServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.common.IZookeeperInfoService;
import com.letv.portal.service.gce.IGceClusterService;
import com.letv.portal.service.gce.IGceContainerService;
import com.letv.portal.service.gce.IGceServerService;

@Component("baseGceTaskService")
public class BaseTask4GceServiceImpl implements IBaseTaskService{

	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Autowired
	private IHostService hostService;
	@Autowired
	private IGceClusterService gceClusterService;
	@Autowired
	private IGceServerService gceServerService;
	@Autowired
	private IGceContainerService gceContainerService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IZookeeperInfoService zookeeperInfoService;
	
	private final static Logger logger = LoggerFactory.getLogger(BaseTask4GceServiceImpl.class);
	
	@Override
	public void beforExecute(Map<String, Object> params) {
		GceServer gce = this.getGceServer(params);
		GceCluster cluster = this.getGceCluster(params);
		if(gce.getStatus() != DbStatus.BUILDDING.getValue()) {
			gce.setStatus(DbStatus.BUILDDING.getValue());
			this.gceServerService.updateBySelective(gce);
		}
		if(gce.getStatus() != MclusterStatus.BUILDDING.getValue()) {
			cluster.setStatus(MclusterStatus.BUILDDING.getValue());
			this.gceClusterService.updateBySelective(cluster);
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
		Map<String,Object> params = (Map<String, Object>) tr.getParams();
		boolean isContinue = (Boolean) params.get("isContinue");
		if(!isContinue) {
			//发送邮件
			this.buildResultToMgr("Gce服务创建", tr.isSuccess()?"创建成功":"创建失败", tr.getResult(), ERROR_MAIL_ADDRESS);
		}
		//业务处理
		this.serviceOver(tr);
	}
	
	private void serviceOver(TaskResult tr) {
		Map<String, Object> params = (Map<String, Object>) tr.getParams();
		GceServer gce = this.getGceServer(params);
		GceCluster cluster = this.getGceCluster(params);
		
		if(tr.isSuccess()) {
			gce.setStatus(DbStatus.NORMAL.getValue());
			cluster.setStatus(MclusterStatus.RUNNING.getValue());
			Map<String, Object> emailParams = new HashMap<String,Object>();
			emailParams.put("gceName", gce.getGceName());
			this.email4User(emailParams, gce.getCreateUser(),"gce/createGce.ftl");
		} else {
			gce.setStatus(DbStatus.BUILDFAIL.getValue());
			cluster.setStatus(MclusterStatus.BUILDFAIL.getValue());
			
			Map<String,Object> nextParams = (Map<String, Object>) params.get("nextParams");
			if(!nextParams.isEmpty()) {
				GceServer nextGce = this.getGceServer(nextParams);
				GceCluster nextCluster = this.getGceCluster(nextParams);
				nextGce.setStatus(DbStatus.BUILDFAIL.getValue());
				nextCluster.setStatus(MclusterStatus.BUILDFAIL.getValue());
				this.gceServerService.updateBySelective(nextGce);
				this.gceClusterService.updateBySelective(nextCluster);
			}
		}
		this.gceServerService.updateBySelective(gce);
		this.gceClusterService.updateBySelective(cluster);
	}

	@Override
	public void callBack(TaskResult tr) {
		
	}

	@SuppressWarnings("unchecked")
	public TaskResult analyzeRestServiceResult(String result){
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
			tr.setParams(response);
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
	
	public GceServer getGceServer(Map<String, Object> params) {
		Long gceId = getLongFromObject(params.get("gceId"));
		if(gceId == null)
			throw new ValidateException("params's gceId is null");
		
		GceServer gceServer = this.gceServerService.selectById(gceId);
		if(gceServer == null)
			throw new ValidateException("gceServer is null by gceId:" + gceId);
		
		return gceServer;
	}
	
	public GceCluster getGceCluster(Map<String, Object> params) {
		Long gceClusterId = getLongFromObject(params.get("gceClusterId"));
		if(gceClusterId == null)
			throw new ValidateException("params's gceClusterId is null");
		
		GceCluster gceCluster = this.gceClusterService.selectById(gceClusterId);
		if(gceCluster == null)
			throw new ValidateException("gceCluster is null by gceClusterId:" + gceClusterId);
		
		return gceCluster;
	}
	
	public HostModel getHost(Long hclusterId) {
		if(hclusterId == null)
			throw new ValidateException("hclusterId is null :" + hclusterId);
		HostModel host = this.hostService.getHostByHclusterId(hclusterId);
		if(host == null)
			throw new ValidateException("host is null by hclusterIdId:" + hclusterId);
		
		return host;
	}
	public List<GceContainer> getContainers(Map<String, Object> params) {
		Long gceClusterId = getLongFromObject(params.get("gceClusterId"));
		if(gceClusterId == null)
			throw new ValidateException("params's gceClusterId is null");
		
		List<GceContainer> gceContainers = this.gceContainerService.selectByGceClusterId(gceClusterId);
		if(gceContainers == null || gceContainers.isEmpty())
			throw new ValidateException("gceCluster is null by gceClusterId:" + gceClusterId);
		return gceContainers;
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
