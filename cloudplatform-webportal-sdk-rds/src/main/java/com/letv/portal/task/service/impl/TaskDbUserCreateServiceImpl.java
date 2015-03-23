package com.letv.portal.task.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.exception.ValidateException;
import com.letv.portal.dao.IMonitorDao;
import com.letv.portal.enumeration.DbUserStatus;
import com.letv.portal.fixedPush.IFixedPushService;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.BaseTask4RDSServiceImpl;
import com.letv.portal.model.task.service.IBaseTaskService;
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

@Service("taskDbUserCreateService")
public class TaskDbUserCreateServiceImpl extends BaseTask4RDSServiceImpl implements IBaseTaskService{
	
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
	private IMonitorService monitorService;
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	
	@Autowired 
	private IMonitorDao monitorDao;
	
	
	private final static Logger logger = LoggerFactory.getLogger(TaskDbUserCreateServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;
		
		//执行业务
		Long dbId = getLongFromObject(params.get("dbId"));
		if(dbId == null)
			throw new ValidateException("params's dbId is null");
		
		List<DbUserModel> dbUsers = this.dbUserService.selectByDbId(dbId);
		for (DbUserModel dbUser : dbUsers) {
			Map<String,Object> createUserParams = this.dbUserService.selectCreateParams(dbUser.getId(),false);
			if(createUserParams == null || createUserParams.isEmpty())
				throw new ValidateException("create params is null by dbUserId");
				
			String result = this.pythonService.createDbUser(dbUser, (String)createUserParams.get("dbName"), (String)createUserParams.get("nodeIp"), (String)createUserParams.get("username"), (String)createUserParams.get("password"));
			tr = analyzeRestServiceResult(result);
					
			if(tr.isSuccess()) {
				dbUser.setStatus(DbUserStatus.NORMAL.getValue());
				this.dbUserService.updateStatus(dbUser);
				
				String userPwd = (String) ((Map) transToMap(result).get("response")).get("user_password");
				Map<String,Object> emailParams = new HashMap<String,Object>();
				emailParams.put("dbUserName", dbUser.getUsername());
				emailParams.put("dbUserPassword", dbUser.getPassword());
				emailParams.put("ip", dbUser.getAcceptIp());
				emailParams.put("dbName", createUserParams.get("dbName"));
				emailParams.put("maxConcurrency", dbUser.getMaxConcurrency());
				this.email4User(emailParams,((BigInteger)createUserParams.get("createUser")).longValue(),"createDbUser.ftl");
			} 
		}
		tr.setParams(params);
		return tr;
	}
	
	private boolean isSelectVip(Long dbId) {
		int step = this.buildService.getStepByDbId(dbId);
		if(step == 0) {
			return true;
		}
		return false;
	}
	
	public void email4User(Map<String,Object> params,Long to,String ftlName){
		UserModel user = this.userService.selectById(to);
		if(null != user) {
			MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",user.getEmail(),"乐视云平台web-portal系统通知",ftlName,params);
			mailMessage.setHtml(true);
			try {
				defaultEmailSender.sendMessage(mailMessage);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			
		}
	}
	
}
