package com.letv.portal.python.service.impl;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.exception.CommonException;
import com.letv.common.exception.MatrixException;
import com.letv.common.exception.PythonException;
import com.letv.common.exception.TaskExecuteException;
import com.letv.common.exception.ValidateException;
import com.letv.common.result.ApiResultObject;
import com.letv.common.util.JsonUtils;
import com.letv.portal.constant.Constant;
import com.letv.portal.dao.IMonitorDao;
import com.letv.portal.enumeration.BuildStatus;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.enumeration.DbUserRoleStatus;
import com.letv.portal.enumeration.DbUserStatus;
import com.letv.portal.enumeration.HostType;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.enumeration.MclusterType;
import com.letv.portal.enumeration.MonitorStatus;
import com.letv.portal.fixedPush.IFixedPushService;
import com.letv.portal.model.BuildModel;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.monitor.BaseMonitor;
import com.letv.portal.model.monitor.ClusterModel;
import com.letv.portal.model.monitor.ClusterMonitorModel;
import com.letv.portal.model.monitor.ContainerMonitorModel;
import com.letv.portal.model.monitor.DbMonitorModel;
import com.letv.portal.model.monitor.MonitorErrorModel;
import com.letv.portal.model.monitor.NodeModel;
import com.letv.portal.model.monitor.NodeModel.DetailModel;
import com.letv.portal.model.monitor.NodeMonitorModel;
import com.letv.portal.model.task.service.ITaskChainService;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IBuildService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IHclusterService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.IMonitorService;
import com.letv.portal.service.IUserService;
import com.letv.portal.zabbixPush.IZabbixPushService;
import com.mysql.jdbc.StringUtils;

@Service("buildTaskService")
public class BuildTaskServiceImpl implements IBuildTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(BuildTaskServiceImpl.class);
	
	@Value("${python_create_check_time}")
	private long PYTHON_CREATE_CHECK_TIME;
	@Value("${python_check_interval_time}")
	private long PYTHON_CHECK_INTERVAL_TIME;
	@Value("${python_create_interval_init_time}")
	private long PYTHON_CREATE_INTERVAL_INIT_TIME;
	@Value("${python_init_check_time}")
	private long PYTHON_INIT_CHECK_TIME;
	@Value("${python_init_check_interval_time}")
	private long PYTHON_INIT_CHECK_INTERVAL_TIME;
	
	@Autowired
	private IMclusterService mclusterService;
	@Autowired
	private IDbUserService dbUserService;
	@Autowired
	private IPythonService pythonService;
	@Autowired
	private IDbService dbService;
	@Autowired
	private IContainerService containerService;
	@Autowired
	private IBuildService buildService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IHostService hostService;
	@Autowired
	private IHclusterService hclusterService;
	@Autowired
	private IFixedPushService fixedPushService;
	@Autowired 
	private IZabbixPushService zabbixPushService;
	@Autowired 
	private IMonitorIndexService monitorIndexService;
	@Autowired
	private ITaskChainService taskChainService;
	@Autowired 
	private IMonitorService monitorService;
	
	@Value("${service.notice.email.to}")
	private String SERVICE_NOTICE_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	
	@Autowired 
	private IMonitorDao monitorDao;
	
	@Override
	@Async
	public void buildDb(Long dbId) {
		Integer status = null;
		String resultMsg = "";
		String detail = "";
		Map<String,Object> params = this.dbService.selectCreateParams(dbId,isSelectVip(dbId));
		try {
			ApiResultObject resultObject = this.pythonService.createDb((String)params.get("nodeIp"), (String)params.get("dbName"), (String)params.get("dbName"), null, (String)params.get("username"), (String)params.get("password"));
			
			if(analysisResult(transResult(resultObject.getResult()))) {
				resultMsg = "成功";
				status = DbStatus.NORMAL.getValue();
				List<ContainerModel> containers = this.containerService.selectByMclusterId(((BigInteger)params.get("mclusterId")).longValue());
				Map<String,Object> glbParams = new HashMap<String,Object>();
				List<String> urlPorts = new ArrayList<String>();
				for (ContainerModel container : containers) {
					if("mclusternode".equals(container.getType())) {
						urlPorts.add(container.getIpAddr() + ":3306");
					}
				}
				glbParams.put("User", "monitor");
				glbParams.put("Pass", params.get("sstPwd"));
				glbParams.put("Addr", "127.0.0.1");
				glbParams.put("Port", "3306");
				glbParams.put("Backend", urlPorts);
				
				Map<String,Object> emailParams = new HashMap<String,Object>();
				emailParams.put("dbName", params.get("dbName"));
				emailParams.put("glbStr", JsonUtils.writeObject(glbParams));
				this.email4User(emailParams,((BigInteger)params.get("createUser")).longValue(),"createDb.ftl");
			} else {
				resultMsg = "失败";
				status = DbStatus.BUILDFAIL.getValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg = "失败";
			detail = e.getMessage();
			status = DbStatus.BUILDFAIL.getValue();
		} finally {
			this.buildResultToMgr("DB数据库" + params.get("dbName") + "创建", resultMsg, detail, SERVICE_NOTICE_MAIL_ADDRESS);
			DbModel dbModel = new DbModel();
			dbModel.setId(dbId);
			dbModel.setStatus(status);
			this.dbService.updateBySelective(dbModel);
			if(DbStatus.NORMAL.getValue() == status) {
				List<DbUserModel> dbUsers = this.dbUserService.selectByDbId(dbId);
				if(dbUsers.isEmpty())
					return;
				StringBuffer ids = new StringBuffer();
				for (DbUserModel dbUser : dbUsers) {
					ids.append(dbUser.getId()).append(",");
				}
				buildUser(ids.substring(0, ids.length()-1));
			} 
		}
	}

	@Override
	public void buildUser(String ids) {
		this.buildUser(ids, "create");
	}
	@Override
	public void updateUser(String ids) {
		this.buildUser(ids,"update");
	}
	@Override
	public void deleteDbUser(String ids) {
		this.buildUser(ids,"delete");
	}
	private void buildUser(String ids,String type) {
		if(StringUtils.isNullOrEmpty(ids))
			throw new ValidateException("参数不合法");
		String[] str = ids.split(",");
	
		List<DbUserModel> buildResult = new ArrayList<DbUserModel>();
		List<DbUserModel> errorResult = new ArrayList<DbUserModel>();
		for (String id : str) {
			//查询所属db 所属mcluster 及container数据
			DbUserModel dbUserModel = this.dbUserService.selectById(Long.parseLong(id));
			if(dbUserModel == null) 
				throw new ValidateException("参数不合法");
			Map<String,Object> params = this.dbUserService.selectCreateParams(Long.parseLong(id),isSelectVip(dbUserModel.getDbId()));
			if(params == null || params.isEmpty()) 
				throw new ValidateException("参数不合法");
			ApiResultObject result = null;
			if("delete".equals(type)) {
				result = this.pythonService.deleteDbUser(dbUserModel, (String)params.get("dbName"), (String)params.get("nodeIp"), (String)params.get("username"), (String)params.get("password"));
			} else {
				result = this.pythonService.createDbUser(dbUserModel, (String)params.get("dbName"), (String)params.get("nodeIp"), (String)params.get("username"), (String)params.get("password"));
			}
			if(analysisResult(transResult(result.getResult()))) {
				//修改成功
				dbUserModel.setStatus(DbUserStatus.NORMAL.getValue());
				this.dbUserService.updateDbUser(dbUserModel);
			} else {
				errorResult.add(dbUserModel);
			}
			buildResult.add(dbUserModel);
		}
		if(!errorResult.isEmpty()) {
			for (DbUserModel dbUserModel : buildResult) {
				dbUserModel.setStatus(DbUserStatus.BUILDFAIL.getValue());
				this.dbUserService.updateDbUser(dbUserModel);
			}
			DbModel db = this.dbService.selectById(errorResult.get(0).getDbId());
			this.buildResultToMgr("DB数据库("+db.getDbName()+")用户" + errorResult.get(0).getUsername() + " "+type +"失败", "", "call python api failed", SERVICE_NOTICE_MAIL_ADDRESS);
		} else {
			this.sendEmail4DbUserBuild(buildResult,type);
		}
	}
	private void sendEmail4DbUserBuild(List<DbUserModel> buildResult,String type) {
		Map<String,Object> emailParams = new HashMap<String,Object>();
		StringBuffer ipAndRole = new StringBuffer();
		for (int i = 0; i < buildResult.size(); i++) {
			DbUserModel dbUser = buildResult.get(i);
			if(i == 0) {
				DbModel db = this.dbService.selectById(dbUser.getDbId());
				emailParams.put("type", type);
				emailParams.put("dbUserName", dbUser.getUsername());
				emailParams.put("dbUserPassword", dbUser.getPassword());
				emailParams.put("dbName", db.getDbName());
				emailParams.put("maxConcurrency", dbUser.getMaxConcurrency());
			}
			ipAndRole.append(dbUser.getAcceptIp()).append(":").append(getUserRole(dbUser.getType())).append("<br>");
		}
		emailParams.put("ip",ipAndRole.toString());
		this.email4User(emailParams,buildResult.get(0).getCreateUser(),"dbUser.ftl");
		this.email4User(emailParams,SERVICE_NOTICE_MAIL_ADDRESS,"dbUser4Manager.ftl");
	}
	private String getUserRole(Integer roleId) {
		if(DbUserRoleStatus.MANAGER.getValue().equals(roleId))
			return "管理员";
		if(DbUserRoleStatus.WR.getValue().equals(roleId))
			return "读写用户";
		if(DbUserRoleStatus.RO.getValue().equals(roleId))
			return "只读用户";
		return "";
	}

	private boolean analysisToFixedOrZabbix(Boolean sendFlag,int step,Date startTime,Long mclusterId,Long dbId,String type){
		BuildModel buildModel = new BuildModel();
		
		buildModel.setMclusterId(mclusterId);
		buildModel.setDbId(dbId);
		buildModel.setStep(step);
		buildModel.setStartTime(startTime);
		buildModel.setEndTime(new Date());
		buildModel.setStatus(BuildStatus.SUCCESS.getValue());
		if(!sendFlag) {
			//if failed then send email to system Manager,and go on.
			this.buildResultToMgr("mcluster集群", "相关系统推送异常", type + "推送异常，请运维人员重新推送！", SERVICE_NOTICE_MAIL_ADDRESS);
		}
		this.buildService.updateByStep(buildModel);
		BuildModel nextBuild = new BuildModel();
		nextBuild.setMclusterId(mclusterId);
		nextBuild.setStep(step+1);
		nextBuild.setStartTime(new Date());
		nextBuild.setStatus(BuildStatus.BUILDING.getValue());
		this.buildService.updateByStep(nextBuild);
		return true;
	}
	
	private boolean analysis(Map<String,Object> jsonResult,int step,Date startTime,Long mclusterId,Long dbId){
		Map<String,Object> meta = (Map)jsonResult.get("meta");
		BuildModel buildModel = new BuildModel();
		
		buildModel.setMclusterId(mclusterId);
		buildModel.setDbId(dbId);
		buildModel.setStep(step);
		buildModel.setStartTime(startTime);
		buildModel.setEndTime(new Date());
		
		boolean flag = true;
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			Map<String,Object> response = (Map)jsonResult.get("response");
			buildModel.setCode((String)response.get("code"));
			buildModel.setMsg((String) response.get("message"));
			buildModel.setStatus(BuildStatus.SUCCESS.getValue());
		} else {
			buildModel.setCode(String.valueOf(meta.get("code")));
			buildModel.setMsg((String)meta.get("errorDetail"));
			buildModel.setStatus(BuildStatus.FAIL.getValue());
			flag =  false;
			this.buildResultToMgr("mcluster集群", "失败", (String)meta.get("errorDetail"), SERVICE_NOTICE_MAIL_ADDRESS);
			MclusterModel mclusterModel = new MclusterModel();
			mclusterModel.setId(mclusterId);
			mclusterModel.setStatus(MclusterStatus.BUILDFAIL.getValue());
			this.mclusterService.audit(mclusterModel);
			if(dbId!=null) {
				DbModel dbModel = new DbModel();
				dbModel.setId(dbId);
				dbModel.setStatus(DbStatus.BUILDFAIL.getValue());
				this.dbService.updateBySelective(dbModel);
			}
		}
		this.buildService.updateByStep(buildModel);
		if(flag) {
			BuildModel nextBuild = new BuildModel();
			nextBuild.setMclusterId(mclusterId);
			nextBuild.setStep(step+1);
			nextBuild.setDbId(dbId);
			nextBuild.setStartTime(new Date());
			nextBuild.setStatus(BuildStatus.BUILDING.getValue());
			this.buildService.updateByStep(nextBuild);
		}
		return flag;
	}
	
	private Map<String,Object> transResult(String result){
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		if(StringUtils.isNullOrEmpty(result))
			return jsonResult;
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	private boolean analysisResult(Map result){
		boolean flag = false;
		Map meta = (Map) result.get("meta");
		if(null!=meta &&Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			flag = true;
		} 
		return flag;
	}
	
	private ContainerMonitorModel analysisResultMonitor(Map result){
		String type=MonitorStatus.NORMAL.getValue().toString();
		ContainerMonitorModel containerMonitorModel = new ContainerMonitorModel();
		Map<String, Object>  listNode= (Map<String, Object>) ((Map) result.get("response")).get("node");
		Map<String, Object>  listDb= (Map<String, Object>) ((Map) result.get("response")).get("db");
		List<NodeMonitorModel> nodeMoList = new ArrayList<NodeMonitorModel>();
		List<DbMonitorModel> dbMoList = new ArrayList<DbMonitorModel>();	
		for(Iterator it =  listNode.keySet().iterator();it.hasNext();){
			 Object keString = it.next();
			 Map<String, Object>  listsHashMap = (Map<String, Object>)listNode.get(keString);
			 NodeMonitorModel nodeMonitorModel = new NodeMonitorModel();
		     nodeMonitorModel.setMonitorName(keString.toString());
			 nodeMonitorModel.setMessage(listsHashMap.get("message")!=null?listsHashMap.get("message").toString():"");
			 nodeMonitorModel.setAlarm(listsHashMap.get("alarm")!=null?listsHashMap.get("alarm").toString():"");
			 if("sms:email".equals(listsHashMap.get("alarm").toString())){
				 type=MonitorStatus.GENERAL.getValue().toString();
			 }if("tel:sms:email".equals(listsHashMap.get("alarm").toString())){
				 type=MonitorStatus.SERIOUS.getValue().toString(); 
			 }
			 nodeMonitorModel.setErrorRecord(listsHashMap.get("error_record")!=null?listsHashMap.get("error_record").toString():"");
			 nodeMonitorModel.setCtime(listsHashMap.get("ctime")!=null?listsHashMap.get("ctime").toString():"");
			 nodeMoList.add(nodeMonitorModel);
		}	
		for(Iterator it =  listDb.keySet().iterator();it.hasNext();){
			 Object keString = it.next();
			 Map<String, Object>  listsHashMap = (Map<String, Object>)listDb.get(keString);
			 DbMonitorModel dbMonitorModel = new DbMonitorModel();
			 dbMonitorModel.setMonitorName(keString.toString());
			 dbMonitorModel.setMessage(listsHashMap.get("message")!=null?listsHashMap.get("message").toString():"");
			 dbMonitorModel.setAlarm(listsHashMap.get("alarm")!=null?listsHashMap.get("alarm").toString():"");
			 if("sms:email".equals(listsHashMap.get("alarm").toString())){
				 type=MonitorStatus.GENERAL.getValue().toString();
			 }if("tel:sms:email".equals(listsHashMap.get("alarm").toString())){
				 type=MonitorStatus.SERIOUS.getValue().toString(); 
			 }
			 dbMonitorModel.setErrorRecord(listsHashMap.get("error_record")!=null?listsHashMap.get("error_record").toString():"");
			 dbMonitorModel.setCtime(listsHashMap.get("ctime")!=null?listsHashMap.get("ctime").toString():"");
			 dbMoList.add(dbMonitorModel);
		}	
		containerMonitorModel.setNodeMoList(nodeMoList);
		containerMonitorModel.setDbMoList(dbMoList);
		containerMonitorModel.setStatus(type);
		return containerMonitorModel;
	}
	
	private ContainerMonitorModel analysisResultMonitorC(Map result){
		String type=MonitorStatus.NORMAL.getValue().toString();
		ContainerMonitorModel containerMonitorModel = new ContainerMonitorModel();
		Map<String, Object>  listNode= (Map<String, Object>) ((Map) result.get("response")).get("node");
		Map<String, Object>  listCluster= (Map<String, Object>) ((Map) result.get("response")).get("cluster");
		List<NodeMonitorModel> nodeMoList = new ArrayList<NodeMonitorModel>();
		List<ClusterMonitorModel> clMoList = new ArrayList<ClusterMonitorModel>();	
		for(Iterator it =  listNode.keySet().iterator();it.hasNext();){
			 Object keString = it.next();
			 Map<String, Object>  listsHashMap = (Map<String, Object>)listNode.get(keString);
			 NodeMonitorModel nodeMonitorModel = new NodeMonitorModel();
		     nodeMonitorModel.setMonitorName(keString.toString());
		     nodeMonitorModel.setAlarm(listsHashMap.get("alarm")!=null?listsHashMap.get("alarm").toString():"");
			 if("sms:email".equals(listsHashMap.get("alarm").toString())){
				 type=MonitorStatus.GENERAL.getValue().toString();
			 }if("tel:sms:email".equals(listsHashMap.get("alarm").toString())){
				 type=MonitorStatus.SERIOUS.getValue().toString(); 
			 }
			 nodeMonitorModel.setMessage(listsHashMap.get("message")!=null?listsHashMap.get("message").toString():"");
			 nodeMoList.add(nodeMonitorModel);
		}	
		for(Iterator it =  listCluster.keySet().iterator();it.hasNext();){
			 Object keString = it.next();
			 Map<String, Object>  listsHashMap = (Map<String, Object>)listCluster.get(keString);
			 ClusterMonitorModel clusterMonitorModel = new ClusterMonitorModel();
			 clusterMonitorModel.setMonitorName(keString.toString());
			 clusterMonitorModel.setMessage(listsHashMap.get("message")!=null?listsHashMap.get("message").toString():"");
			 clusterMonitorModel.setAlarm(listsHashMap.get("alarm")!=null?listsHashMap.get("alarm").toString():"");
			 if("sms:email".equals(listsHashMap.get("alarm").toString())){
				 type=MonitorStatus.GENERAL.getValue().toString();
			 }if("tel:sms:email".equals(listsHashMap.get("alarm").toString())){
				 type=MonitorStatus.SERIOUS.getValue().toString(); 
			 }
			 clMoList.add(clusterMonitorModel);
		}	
		containerMonitorModel.setNodeMoList(nodeMoList);
		containerMonitorModel.setClMoList(clMoList);
		containerMonitorModel.setStatus(type);
		return containerMonitorModel;
	}
	
	private boolean analysisCheckCreateResult(Map result){
		boolean flag = false;
		Map meta = (Map) result.get("meta");
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			if(Constant.PYTHON_API_RESULT_SUCCESS.equals(((Map)result.get("response")).get("code"))) {
				flag = true;
			}
		} 
		return flag;
	}
	private boolean analysisCheckInitResult(Map result){
		boolean flag = false;
		Map meta = (Map) result.get("meta");
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")))) {
			if(Constant.MCLUSTER_INIT_STATUS_RUNNING.equals(((Map)result.get("response")).get("message"))) {
				flag = true;
			}
		} 
		return flag;
	}
	
	public void buildResultToMgr(String buildType,String result,String detail,String to){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("buildType", buildType);
		map.put("buildResult", result);
		map.put("errorDetail", detail);
		MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统", StringUtils.isNullOrEmpty(to)?SERVICE_NOTICE_MAIL_ADDRESS:to,"乐视云平台web-portal系统通知","buildForMgr.ftl",map);
		try {
			defaultEmailSender.sendMessage(mailMessage);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private void email4User(Map<String,Object> params,Long to,String ftlName){
		UserModel user = this.userService.selectById(to);
		this.email4User(params, user.getEmail(), ftlName);
	}
	private void email4User(Map<String,Object> params,String to,String ftlName){
			MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",to,"乐视云平台web-portal系统通知",ftlName,params);
			mailMessage.setHtml(true);
			try {
				defaultEmailSender.sendMessage(mailMessage);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
	}
	
	@Override
	@Async
	public void removeMcluster(MclusterModel mcluster) {
		HostModel host = getHostByHclusterId(mcluster.getHclusterId());
		List<ContainerModel> list = this.containerService.selectContainerByMclusterId(mcluster.getId());
		ApiResultObject result = this.pythonService.removeMcluster(mcluster.getMclusterName(),host.getHostIp(),host.getName(),host.getPassword());		
		if(analysisResult(transResult(result.getResult()))) {
			this.pythonService.removeMcluster(mcluster.getMclusterName()+Constant.MCLUSTER_NODE_TYPE_VIP_SUFFIX,host.getHostIp(),host.getName(),host.getPassword());		
		} else {
			this.buildResultToMgr("mcluster 删除失败", mcluster.getMclusterName() + "：" +result.getResult(), result.getUrl(), this.SERVICE_NOTICE_MAIL_ADDRESS);
			return;
		}
		StringBuffer ips = new StringBuffer();
		for (ContainerModel containerModel : list) {
			ips.append(containerModel.getIpAddr()).append(",");
		}
		if(!this.zabbixPushService.deleteMutilContainerPushZabbixInfo(list)) {
			//send zabbix push failed to manager.
			this.buildResultToMgr("zabbix 删除推送失败，请手动操作", result.getResult(), ips.toString(), this.SERVICE_NOTICE_MAIL_ADDRESS);
		}
		if(!this.fixedPushService.deleteMutilContainerPushFixedInfo(list)) {
			//send fixed push failed to manager.
			this.buildResultToMgr("fixed 删除推送失败，请手动操作", result.getResult(), ips.toString(), this.SERVICE_NOTICE_MAIL_ADDRESS);
		}
	}

	@Override
	@Async
	public void startMcluster(MclusterModel mcluster) {
		HostModel host = getHostByHclusterId(mcluster.getHclusterId());
		ApiResultObject result = this.pythonService.startMcluster(mcluster.getMclusterName(),host.getHostIp(),host.getName(),host.getPassword());
		if(analysisResult(transResult(result.getResult()))) {
			this.pythonService.startMcluster(mcluster.getMclusterName()+Constant.MCLUSTER_NODE_TYPE_VIP_SUFFIX,host.getHostIp(),host.getName(),host.getPassword());
		}else {
			throw new PythonException("集群启动失败：" + result.getUrl());
		}
	}

	@Override
	@Async
	public void stopMcluster(MclusterModel mcluster) {
		HostModel host = getHostByHclusterId(mcluster.getHclusterId());
		ApiResultObject result = this.pythonService.stopMcluster(mcluster.getMclusterName(),host.getHostIp(),host.getName(),host.getPassword());
		if(analysisResult(transResult(result.getResult()))) {
			this.pythonService.stopMcluster(mcluster.getMclusterName()+Constant.MCLUSTER_NODE_TYPE_VIP_SUFFIX,host.getHostIp(),host.getName(),host.getPassword());
		} else {
			throw new PythonException("集群停止失败：" + result.getUrl());
		}
		
	}

	@Override
	@Async
	public void startContainer(ContainerModel container) {
		HostModel host = this.hostService.selectById(container.getHostId());
		ApiResultObject result = this.pythonService.startContainer(container.getContainerName(),host.getHostIp(),host.getName(),host.getPassword());
		if(!analysisResult(transResult(result.getResult())))  {
			throw new PythonException("container启动失败：" + result.getUrl());
		}
	}

	@Override
	@Async
	public void stopContainer(ContainerModel container) {
		HostModel host = this.hostService.selectById(container.getHostId());
		ApiResultObject result = this.pythonService.stopContainer(container.getContainerName(),host.getHostIp(),host.getName(),host.getPassword());
		if(!analysisResult(transResult(result.getResult())))  {
			throw new PythonException("container停止失败：" + result.getUrl());
		}
	}

	@Override
	@Async
	public void checkMclusterStatus(MclusterModel mcluster) {
		HostModel host = getHostByHclusterId(mcluster.getHclusterId());
		String result = this.pythonService.checkMclusterStatus(mcluster.getMclusterName(),host.getHostIp(),host.getName(),host.getPassword());
		Map map = this.transResult(result);
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(((Map)map.get("meta")).get("code")))) {
			Integer status = transStatus((String)((Map)map.get("response")).get("status"));
			mcluster.setStatus(status);
			this.mclusterService.updateBySelective(mcluster);
			if(status == MclusterStatus.NOTEXIT.getValue() || status == MclusterStatus.DESTROYED.getValue()) {
				this.mclusterService.delete(mcluster);
			}
		} else if(null !=result && result.contains("not existed")){
			this.mclusterService.delete(mcluster);
		}
		this.pythonService.checkMclusterStatus(mcluster.getMclusterName()+Constant.MCLUSTER_NODE_TYPE_VIP_SUFFIX,host.getHostIp(),host.getName(),host.getPassword());
	}

	@Override
	@Async
	public void checkContainerStatus(ContainerModel container) {
		HostModel host = this.hostService.selectById(container.getHostId());
		String result = this.pythonService.checkContainerStatus(container.getContainerName(),host.getHostIp(),host.getName(),host.getPassword());
		Map map = this.transResult(result);
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(((Map)map.get("meta")).get("code")))) {
			Integer status = transStatus((String)((Map)map.get("response")).get("status"));
			container.setStatus(status);
			this.containerService.updateBySelective(container);
		}
	}
	
	public Integer transStatus(String statusStr){
		// { "meta": {"code": 200}, "response": {"status": " starting / started / stopping / stopped / destroying / destroyed / not exist / failed", "message": ""  } }
		Integer status = null;
		if("starting".equals(statusStr)) {
			status = MclusterStatus.STARTING.getValue();
		} else if("started".equals(statusStr)) {
			status = MclusterStatus.RUNNING.getValue();
		} else if("stopping".equals(statusStr)) {
			status = MclusterStatus.STOPPING.getValue();
		} else if("stopped".equals(statusStr)) {
			status = MclusterStatus.STOPED.getValue();
		} else if("destroying".equals(statusStr)) {
			status = MclusterStatus.DESTROYING.getValue();
		} else if("destroyed".equals(statusStr)) {
			status = MclusterStatus.DESTROYED.getValue();
		} else if("not exist".equals(statusStr)) {
			status = MclusterStatus.NOTEXIT.getValue();
		} else if("failed".equals(statusStr)) {
			
		} else if("danger".equals(statusStr)) {
			status = MclusterStatus.DANGER.getValue();
		} else if("crisis".equals(statusStr)) {
			status = MclusterStatus.CRISIS.getValue();
		}
		return status;
	}

	public void createHost(HostModel hostModel){
		ApiResultObject result = this.pythonService.initHcluster(hostModel.getHostIp());
		if(StringUtils.isNullOrEmpty(result.getResult())) {
			throw new PythonException("create host faild by Container Manager's python API:" + result.getUrl());
		}
		result = this.pythonService.createHost(hostModel);
		if(StringUtils.isNullOrEmpty(result.getResult())) {
			throw new PythonException("create host faild by Container Manager's python API: " + result.getUrl());
		}
	}
	
	
	
	private HostModel getHostByHclusterId(Long hclusterId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("hclusterId", hclusterId);
		map.put("type", HostType.MASTER.getValue());
		return this.hostService.selectByMap(map).get(0);
	}

	@Override
	@Async
	public void checkMclusterCount() {
		List<HclusterModel> rdsHcluster = this.getRdsHcluster();
		for (HclusterModel hcluster : rdsHcluster) {
			List<HostModel> hosts = this.hostService.selectByHclusterId(hcluster.getId());
			if(null == hosts || hosts.isEmpty())
				continue;
			HostModel host = hosts.get(0);
			Map map = this.transResult( this.pythonService.checkMclusterCount(host.getHostIp(),host.getName(),host.getPassword()));
			if(null == map || map.isEmpty())
				continue;
			if(!Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(((Map<String,Object>)map.get("meta")).get("code")))) 
				continue;
			List<Map<String,Object>> data = (List<Map<String,Object>>) ((Map) map.get("response")).get("data");
			asyncMclusterCount(data,hcluster);
		}
	}
	private List<HclusterModel>getRdsHcluster() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", "rds");
		return this.hclusterService.selectByMap(params);
	}
	private void asyncMclusterCount(List<Map<String,Object>> data,HclusterModel hcluster) {
		for (Map<String,Object> mm : data) {
			String mclusterName = (String) mm.get("clusterName");
			if(StringUtils.isNullOrEmpty(mclusterName))
				continue;
			if(mclusterName.contains("_vip"))
				mclusterName = mclusterName.substring(0,mclusterName.lastIndexOf(Constant.MCLUSTER_NODE_TYPE_VIP_SUFFIX));
			List<MclusterModel> list = this.mclusterService.selectByName(mclusterName);
			if(null == list || list.isEmpty()) {
				this.addHandMcluster(mm,hcluster.getId());
				continue;
			} 
			MclusterModel mcluster = list.get(0);
			if(MclusterStatus.BUILDDING.getValue() == mcluster.getStatus() || MclusterStatus.BUILDFAIL.getValue() == mcluster.getStatus() || MclusterStatus.DEFAULT.getValue() == mcluster.getStatus()|| MclusterStatus.AUDITFAIL.getValue() == mcluster.getStatus())
				continue;
			if(transStatus((String) mm.get("status")) == MclusterStatus.NOTEXIT.getValue() || transStatus((String) mm.get("status")) == MclusterStatus.DESTROYED.getValue()) {
				this.mclusterService.delete(list.get(0));
				continue;
			}
			addOrUpdateContainer(mm,mcluster);
		}
	}
	private void addOrUpdateContainer(Map<String,Object> mm,MclusterModel mcluster) {
		List<Map<String,Object>> cms = (List<Map<String,Object>>) mm.get("nodeInfo");
		for (Map<String,Object> cm : cms) {
			ContainerModel container  = this.containerService.selectByName((String) cm.get("containerName"));
			if(null == container) {
				this.addHandContainer(cm, mcluster.getId());
				continue;
			} 
			if(!cm.get("hostIp").equals(container.getHostIp())) {
				container.setContainerName((String) cm.get("containerName"));
				container.setHostIp((String) cm.get("hostIp"));
				HostModel hostModel = this.hostService.selectByIp((String) cm.get("hostIp"));
				if(null != hostModel) {
					container.setHostId(hostModel.getId());
				}
				this.containerService.updateHostIpByName(container);
			}
		}
	}
	
	private void addHandMcluster(Map mm,Long hclusterId) {
		MclusterModel mcluster = new MclusterModel();
		mcluster.setMclusterName((String) mm.get("clusterName"));
		mcluster.setStatus(MclusterStatus.RUNNING.getValue());	
		mcluster.setAdminUser("root");
		mcluster.setAdminPassword((String) mm.get("clusterName"));
		mcluster.setType(MclusterType.HAND.getValue());
		mcluster.setHclusterId(hclusterId);
		mcluster.setDeleted(true);
		this.mclusterService.insert(mcluster);
		List<Map> cms = (List<Map>) mm.get("nodeInfo");
		for (Map cm : cms) {
			this.addHandContainer(cm,mcluster.getId());
		}
	}
	private void addHandContainer(Map cm,Long mclusterId) {
		ContainerModel container = new ContainerModel();
		try {
			BeanUtils.populate(container, cm);
			container.setMclusterId(mclusterId);
			container.setIpMask((String) cm.get("netMask"));
			container.setContainerName((String) cm.get("containerName"));
			container.setStatus(MclusterStatus.RUNNING.getValue());
			container.setHostIp((String) cm.get("hostIp"));
			HostModel hostModel = this.hostService.selectByIp((String) cm.get("hostIp"));
			if(null != hostModel) {
				container.setHostId(hostModel.getId());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		this.containerService.insert(container);
	}

	@SuppressWarnings("finally")
	public ContainerMonitorModel getMonitorData(ContainerModel container){
		ContainerMonitorModel containerMonitor = new ContainerMonitorModel();
		String mclusterName = null;
		String ip = container.getIpAddr();	
		mclusterName = container.getMcluster().getMclusterName();
		try {
			containerMonitor = analysisResultMonitorC(transResult(this.pythonService.getMclusterMonitor(ip)));
		} catch (Exception e) {
			containerMonitor.setStatus(String.valueOf(MonitorStatus.CRASH.getValue()));
		} finally {
			containerMonitor.setIp(ip);
			containerMonitor.setMclusterName(container.getMcluster().getMclusterName());
			containerMonitor.setHclusterName(container.getHost().getHostNameAlias());			
			return containerMonitor;
		}
	}
	
	@Override
	public BaseMonitor getMonitorData(String ip, Long monitorType) {
		BaseMonitor monitor = null;
			String result = "";
			if(monitorType == 1) {
				monitor = new ClusterModel();
				result = this.pythonService.getMclusterMonitor(ip);
			} else {
				monitor = new NodeModel();
				result = this.pythonService.getMclusterStatus(ip);
			}
			
			if(StringUtils.isNullOrEmpty(result)) {
				monitor.setResult(MonitorStatus.TIMEOUT.getValue());
				return monitor;
			} 
			
			if(monitorType == 1) {
					monitor = analysisClusterData(result);
			} else if(monitorType == 2) {
					monitor = analysisNodeData(result);
			} else if(monitorType == 3) {
					monitor = analysisDbData(result);
			}
			return monitor;
	}
	
	private ClusterModel analysisClusterData(String result){
		Map<String,Object> data = transResult(result);
		ClusterModel monitor = new ClusterModel();
		if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(((Map)(data.get("meta"))).get("code")))) {
			ObjectMapper resultMapper = new ObjectMapper();
			try {
				monitor = resultMapper.readValue(result, ClusterModel.class);
			} catch (Exception e) {
				logger.error("解析数据异常：" + e.getMessage());
				monitor.setResult(MonitorStatus.EXCEPTION.getValue());
				return monitor;
			}
			String[] lostIp = monitor.getResponse().getNode().getNode_size().getLost_ip();
			if(lostIp == null) {
				monitor.setResult(MonitorStatus.CRASH.getValue());
				if("nothing".equals(monitor.getResponse().getNode().getNode_size().getAlarm())) {
					monitor.setResult(MonitorStatus.NORMAL.getValue());
				}
			} else {
				monitor.setResult(lostIp.length);
			}
		}
		return monitor;
	}
	private NodeModel analysisDbData(String result){
		Map<String,Object> data = transResult(result);
		NodeModel monitor = new NodeModel();
			if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(((Map)(data.get("meta"))).get("code")))) {
				ObjectMapper resultMapper = new ObjectMapper();
				try {
					monitor = resultMapper.readValue(result, NodeModel.class);
				} catch (Exception e) {
					logger.error("解析数据异常：" + e.getMessage());
					monitor.setResult(MonitorStatus.EXCEPTION.getValue());
					return monitor;
				}
				Date now = new Date();
				//处理
				boolean timeout = false;
				int failCount = 0;
				
				DetailModel detailModel = monitor.getResponse().getDb().getCur_conns();
				failCount = compareFailCount(failCount,detailModel);
				timeout = isTimeout(now, detailModel);
				detailModel = monitor.getResponse().getDb().getExisted_db_anti_item();
				failCount = compareFailCount(failCount,detailModel);
				timeout = isTimeout(now, detailModel);
				detailModel = monitor.getResponse().getDb().getWrite_read_avaliable();
				failCount = compareFailCount(failCount,detailModel);
				timeout = isTimeout(now, detailModel);
				detailModel = monitor.getResponse().getDb().getWsrep_status();
				failCount = compareFailCount(failCount,detailModel);
				timeout = isTimeout(now, detailModel);
				monitor.setResult(failCount);
				if(timeout) {
					monitor.setResult(MonitorStatus.CRASH.getValue());
				}
			}
			return monitor;
	}
	private NodeModel analysisNodeData(String result){
		Map<String,Object> data = transResult(result);
		NodeModel monitor = new NodeModel();
			if(Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(((Map)(data.get("meta"))).get("code")))) {
				ObjectMapper resultMapper = new ObjectMapper();
				try {
					monitor = resultMapper.readValue(result, NodeModel.class);
				} catch (Exception e) {
					logger.error("解析数据异常：" + e.getMessage());
					monitor.setResult(MonitorStatus.EXCEPTION.getValue());
					return monitor;
				}
				Date now = new Date();
				//处理
				boolean timeout = false;
				int failCount = 0;
				
				DetailModel detailModel = monitor.getResponse().getNode().getLog_health();
				failCount = compareFailCount(failCount,detailModel);
				timeout = isTimeout(now, detailModel);
				detailModel = monitor.getResponse().getNode().getLog_error();
				failCount = compareFailCount(failCount,detailModel);
				timeout = isTimeout(now, detailModel);
				detailModel = monitor.getResponse().getNode().getStarted();
				failCount = compareFailCount(failCount,detailModel);
				timeout = isTimeout(now, detailModel);
				monitor.setResult(failCount);
				if(timeout) {
					monitor.setResult(MonitorStatus.CRASH.getValue());
				}
			}
			return monitor;
	}
	public boolean isTimeout(Date now,DetailModel detailModel) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long diff = 0;
		String ctime = detailModel.getCtime();
		if(org.apache.commons.lang.StringUtils.isEmpty(ctime))
			return true;
		try {
			diff = (now.getTime() - simpleDateFormat.parse(detailModel.getCtime()).getTime())/1000;
		} catch (ParseException e) {
			return true;
		}
		if(diff > 60)
			return true;
		return false;
	}
	public int compareFailCount(int failCount,DetailModel detailModel) {
		String message = detailModel.getMessage();
		int next = 0;
		
		String alarm = detailModel.getAlarm();
		if(null != alarm && alarm.contains("tel:sms:email"))
			next = 2;
		if(null != alarm && alarm.contains("sms:email"))
			next = 1;
		
		failCount = next - failCount>0?next:failCount;
		return failCount;
	}
	
	public List<ContainerMonitorModel> getMonitorData(List<ContainerModel> containerModels){
		List<ContainerMonitorModel> containerMonitorModels = new ArrayList<ContainerMonitorModel>();
		ContainerMonitorModel containerMonitorModel = new ContainerMonitorModel();
		ContainerMonitorModel containerMonitorModelC = new ContainerMonitorModel();
		String mclusterName = null;
		try {
			for(ContainerModel c:containerModels){
				String ip = c.getIpAddr();	
				containerMonitorModel.setIp(ip);
				containerMonitorModel.setMclusterName(c.getMcluster().getMclusterName());
				containerMonitorModel.setHclusterName(c.getHost().getHostNameAlias());
				
				mclusterName = c.getMcluster().getMclusterName();
				containerMonitorModel  = analysisResultMonitor(transResult(this.pythonService.getMclusterStatus(ip)));
				containerMonitorModelC = analysisResultMonitorC(transResult(this.pythonService.getMclusterMonitor(ip)));
				if(containerMonitorModelC.getStatus().equals(MonitorStatus.GENERAL.getValue().toString())){
					containerMonitorModel.setStatus(MonitorStatus.GENERAL.getValue().toString());
				}if(containerMonitorModelC.getStatus().equals(MonitorStatus.SERIOUS.getValue().toString())){
					containerMonitorModel.setStatus(MonitorStatus.SERIOUS.getValue().toString());
				}
				containerMonitorModel.getNodeMoList().add(containerMonitorModelC.getNodeMoList().get(0));	
				containerMonitorModel.setClMoList(containerMonitorModelC.getClMoList());
			}	
			logger.debug("获得集群监控数据");
		} catch (Exception e) {
			logger.debug("无法获得集群监控数据"+e.getMessage());
			containerMonitorModel.setStatus(MonitorStatus.CRASH.getValue().toString());
		} finally {
			containerMonitorModels.add(containerMonitorModel);
			return  containerMonitorModels;
		}
		
	}
	
	public ContainerMonitorModel getMonitorDetailNodeAndDbData(String ip,String mclusterName){		
		ContainerMonitorModel containerMonitorModel = null;
		try {
				containerMonitorModel = analysisResultMonitor(transResult(this.pythonService.getMclusterStatus(ip)));
				containerMonitorModel.setMclusterName(mclusterName);
		} catch (Exception e) {
			logger.debug("无法获得集群监控数据"+e.getMessage());
		}
		
		logger.debug("获得集群监控数据");
		return  containerMonitorModel;

	}	
	public ContainerMonitorModel getMonitorDetailClusterData(String ip){
		ContainerMonitorModel containerMonitorModel = null;
		try {
				containerMonitorModel = analysisResultMonitorC(transResult(this.pythonService.getMclusterMonitor(ip)));
		} catch (Exception e) {
			logger.debug("无法获得集群监控数据"+e.getMessage());
		}
		
		logger.debug("获得集群监控数据");
		return  containerMonitorModel;
	}
	
	@Override
	@Async
	public void getContainerServiceData(ContainerModel container, MonitorIndexModel index,Date date) {
		ApiResultObject apiResult = this.pythonService.getMonitorData(container.getIpAddr(), index.getDataFromApi());
		Map result = transResult(apiResult.getResult());
		if(analysisResult(result)) {
			Map<String,Object>  data= (Map<String, Object>) result.get("response");
			for(Iterator it =  data.keySet().iterator();it.hasNext();){
				 String key = (String) it.next();
				 MonitorDetailModel monitorDetail = new MonitorDetailModel();
				 monitorDetail.setDbName(index.getDetailTable());
				 monitorDetail.setDetailName(key);
				 monitorDetail.setMonitorDate(date);
				 monitorDetail.setDetailValue(Float.parseFloat(data.get(key).toString()));  
				 monitorDetail.setIp(container.getIpAddr());
				 this.monitorService.insert(monitorDetail);
			}
			logger.info("getContainerServiceData" + date + "-----------------" + new Date() + "--------" + index.getDetailTable());
		} else {
			MonitorErrorModel error = new MonitorErrorModel();
			error.setTableName(index.getDetailTable());
			error.setUrl(apiResult.getUrl());
			error.setResult(result.toString());
			this.monitorService.saveMonitorErrorInfo(error);
		}
		
	}

	@Override
	@Async
	public void getClusterServiceData(String clusterName, Long hclusterId,MonitorIndexModel index,Date date) {
		String ip = this.getHclusterMainIp(hclusterId);
		ApiResultObject apiResult = this.pythonService.getClusterMonitorData(ip, index.getDataFromApi().replace("{0}", clusterName));
		Map result = transResult(apiResult.getResult());
		if(analysisResult(result)) {
			List<Map<String,Object>>  data= (List<Map<String,Object>>) ((Map<String,Object>)result.get("response")).get("data");
			for (Map<String, Object> containerData : data) {
				Map<String,Object>  value = (Map<String, Object>) containerData.get("value");
				String containerName = (String) containerData.get("containerName");
				for(Iterator it =  value.keySet().iterator();it.hasNext();){
					String key = (String) it.next();
					MonitorDetailModel monitorDetail = new MonitorDetailModel();
					monitorDetail.setDbName(index.getDetailTable());
					monitorDetail.setDetailName(key);
					monitorDetail.setMonitorDate(date);
					monitorDetail.setDetailValue(Float.parseFloat(value.get(key).toString()));  
					monitorDetail.setIp(containerName);
					this.monitorService.insert(monitorDetail);
				}
			}
			logger.info("getContainerServiceData" + date + "-----------------" + new Date() + "--------" + index.getDetailTable());
		} else {
			MonitorErrorModel error = new MonitorErrorModel();
			error.setTableName(index.getDetailTable());
			error.setUrl(apiResult.getUrl());
			error.setResult(result.toString());
			this.monitorService.saveMonitorErrorInfo(error);
		}
	}
	
	private String getHclusterMainIp(Long hclusterId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("hclusterId", hclusterId);
		params.put("type", HostType.MASTER.getValue());
		List<HostModel> hosts = this.hostService.selectByMap(params);
		if(hosts == null || hosts.isEmpty())
			throw new ValidateException("hcluster's main host is null.");
		return hosts.get(0).getHostIp();
	}
	private boolean isSelectVip(Long dbId) {
		int step = this.taskChainService.getStepByDbId(dbId);
		if(step == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void getOSSServiceData(String url,String ip, MonitorIndexModel index, Date date) {
		ApiResultObject apiResult = this.pythonService.getOSSData(url, index.getDataFromApi().replace("{0}", ip));
		Map result = transResult(apiResult.getResult());
		if(analysisResult(result)) {
			Map<String,Object>  data= (Map<String,Object>) ((Map<String,Object>)result.get("response")).get("data");
			 String time = (String) ((Map<String,Object>)result.get("response")).get("time");
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 try {
				date = sdf.parse(time);
			} catch (ParseException e) {
				throw new ValidateException("api time error");
			}
			for(Iterator it =  data.keySet().iterator();it.hasNext();){
				String key = (String) it.next();
				MonitorDetailModel monitorDetail = new MonitorDetailModel();
				monitorDetail.setDbName(index.getDetailTable());
				monitorDetail.setDetailName(key);
				monitorDetail.setMonitorDate(date);
				monitorDetail.setDetailValue(Float.parseFloat(data.get(key).toString()));  
				monitorDetail.setIp(ip);
				this.monitorService.insert(monitorDetail);
			}
		} else {
			MonitorErrorModel error = new MonitorErrorModel();
			error.setTableName(index.getDetailTable());
			error.setUrl(apiResult.getUrl());
			error.setResult(result.toString());
			this.monitorService.saveMonitorErrorInfo(error);
		}
	}

	@Override
	@Async
	public void getMysqlMonitorServiceData(ContainerModel container,
			MonitorIndexModel index, Date date, boolean query) {
		Map<String, String> params = new HashMap<String, String>();
		String[] str = index.getMonitorPoint().split(",");
		for (String string : str) {
			params.put(string, "''");
		}
		Map<String,Object>  data = new HashMap<String, Object>();
		if(query==true) {
			ApiResultObject apiResult = this.pythonService.getMysqlMonitorData(container.getIpAddr(), index.getDataFromApi(), params);
			Map result = transResult(apiResult.getResult());
			if(analysisResult(result)) {
				data = (Map<String, Object>) result.get("response");
			} else {
				MonitorErrorModel error = new MonitorErrorModel();
				error.setTableName(index.getDetailTable());
				error.setUrl(apiResult.getUrl());
				error.setResult(result.toString());
				this.monitorService.saveMonitorErrorInfo(error);
			}
		}
		this.monitorService.insertMysqlMonitorData(container, data, date, query);
	}
	
	@Override
	@Async
	public void collectMysqlMonitorBaseData(ContainerModel container, MonitorIndexModel index,Date date) {
		Map<String, String> params = new HashMap<String, String>();
		String[] str = index.getMonitorPoint().split(",");
		for (String string : str) {
			params.put(string, "''");
		}
		ApiResultObject apiResult = this.pythonService.getMysqlMonitorData(container.getIpAddr(), index.getDataFromApi(), params);
		Map result = transResult(apiResult.getResult());
		if(analysisResult(result)) {
			Map<String,Object>  data= (Map<String, Object>) result.get("response");
			for(Iterator it =  data.keySet().iterator();it.hasNext();){
				 String key = (String) it.next();
				 MonitorDetailModel monitorDetail = new MonitorDetailModel();
				 monitorDetail.setDbName(index.getDetailTable());
				 monitorDetail.setDetailName(key);
				 monitorDetail.setMonitorDate(date);
				 monitorDetail.setDetailValue(Float.parseFloat(data.get(key).toString()));  
				 monitorDetail.setIp(container.getIpAddr());
				 this.monitorService.insert(monitorDetail);
			}
			logger.info("collectMysqlMonitorBaseData" + date + "-----------------" + new Date() + "--------" + index.getDetailTable());
		} else {
			//入库
			MonitorErrorModel error = new MonitorErrorModel();
			error.setTableName(index.getDetailTable());
			error.setUrl(apiResult.getUrl());
			error.setResult(result.toString());
			this.monitorService.saveMonitorErrorInfo(error);
			logger.info("collectMysqlMonitorBaseData verify failure, response is :" + result.toString());
		}
	}

	@Override
	@Async
	public void collectMysqlMonitorBaseSpaceData(String dbName,
			ContainerModel container, List<MonitorIndexModel> indexs, Date date) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String,Object>  dataAll = new HashMap<String, Object>();
		for (MonitorIndexModel index : indexs) {
			String[] str = index.getMonitorPoint().split(",");
			params.clear();
			for (String string : str) {
				params.put(string, dbName);
			}
			ApiResultObject apiResult = this.pythonService.getMysqlMonitorData(container.getIpAddr(), index.getDataFromApi(), params);
			Map result = transResult(apiResult.getResult());
			if(analysisResult(result)) {
				Map<String,Object> response = (Map<String, Object>) result.get("response");
				if(response.get(index.getMonitorPoint())!=null && response.get(index.getMonitorPoint()) instanceof Map) {
					Map<String,Object> data = (Map<String, Object>)response.get(index.getMonitorPoint());
					dataAll.putAll(data);
					for(Iterator it =  data.keySet().iterator();it.hasNext();){
						String key = (String) it.next();
						MonitorDetailModel monitorDetail = new MonitorDetailModel();
						monitorDetail.setDbName(index.getDetailTable());
						monitorDetail.setIp(container.getIpAddr());
						monitorDetail.setMonitorDate(date);
						Map<String,Object>  sizeAndComment = (Map<String, Object>) data.get(key);
						monitorDetail.setDetailName(dbName+"."+key);
						monitorDetail.setDetailValue(Float.parseFloat(sizeAndComment.get("total_kb").toString()));  
						this.monitorService.insert(monitorDetail);
					}
				} else {
					dataAll.putAll(response);
					MonitorDetailModel monitorDetail = new MonitorDetailModel();
					monitorDetail.setDbName(index.getDetailTable());
					monitorDetail.setIp(container.getIpAddr());
					monitorDetail.setMonitorDate(date);
					monitorDetail.setDetailName(dbName);
					monitorDetail.setDetailValue(Float.parseFloat(response.get(index.getMonitorPoint()).toString()));  
					this.monitorService.insert(monitorDetail);
				}
				logger.info("collectMysqlMonitorBaseSpaceData" + date + "-----------------" + new Date() + "--------" + index.getDetailTable());
			} else {
				MonitorErrorModel error = new MonitorErrorModel();
				error.setTableName(index.getDetailTable());
				error.setUrl(apiResult.getUrl());
				error.setResult(result.toString());
				this.monitorService.saveMonitorErrorInfo(error);
				logger.info("collectMysqlMonitorBaseSpaceData verify failure, response is :" + result.toString());
			}
		}
		if(dataAll.size()!=0) {
			//保存数据到页面展示表
			this.monitorService.insertMysqlMonitorSpaceData(dbName, container, dataAll, date);
		}
	}
	
}
