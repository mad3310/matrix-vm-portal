package com.letv.portal.proxy.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.exception.ValidateException;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IMclusterService;

@Component
public class DbProxyImpl extends BaseProxyImpl<DbModel> implements
		IDbProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(DbProxyImpl.class);
	
	@Autowired
	private IDbService dbService;
	@Autowired
	private IDbUserService dbUserService;
	@Autowired
	private IContainerService containerService;
	@Autowired
	private IMclusterProxy mclusterProxy;
	@Autowired
	private IMclusterService mclusterService;
	@Autowired
	private IBuildTaskService buildTaskService;
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Value("${db.auto.build.count}")
	private int DB_AUTO_BUILD_COUNT;
	
	@Override
	public IBaseService<DbModel> getService() {
		return dbService;
	}
	@Override
	public void auditAndBuild(Map<String, Object> params) {
		
		Integer status = (Integer) params.get("status");
		Long mclusterId = (Long) params.get("mclusterId");
		Long dbId = (Long) params.get("dbId");
		String mclusterName = (String) params.get("mclusterName");		
		String auditInfo = (String) params.get("auditInfo");
		Long hclusterId = (Long) params.get("hclusterId");
		
		if(status == null || dbId == null) {
			throw new ValidateException("参数不合法");
		}
		
		DbModel dbModel = new DbModel();
		dbModel.setId(dbId);
		dbModel.setStatus(status);
		
		MclusterModel mcluster = new MclusterModel();
		
		if(DbStatus.BUILDDING.getValue().equals(status)) {//审核成功
			//判断mclsuterId是否为空
			if(mclusterId == null) { //创建新的mcluster集群
				if(mclusterName == null) {
					throw new ValidateException("参数不合法");
				}
				mcluster.setMclusterName(mclusterName);
				DbModel db = this.dbService.selectById(dbId);
				mcluster.setHclusterId(hclusterId == null?db.getHclusterId():hclusterId);
				mcluster.setCreateUser(db.getCreateUser());
				this.mclusterProxy.insert(mcluster);
				dbModel.setMclusterId(mcluster.getId());
				this.dbService.updateBySelective(dbModel);
				this.buildTaskService.buildMcluster(mcluster,dbId);
			} else {
				dbModel.setMclusterId(mclusterId);
				this.dbService.updateBySelective(dbModel);
				this.buildTaskService.buildDb(dbId);
			}
		} else { //审核失败
			dbModel.setAuditInfo(auditInfo);
			this.dbService.updateBySelective(dbModel);
		}
			
	}
	@Override
	public void saveAndBuild(DbModel dbModel,boolean isCreateAdmin) {
		Long userId = sessionService.getSession().getUserId();
		dbModel.setCreateUser(userId);
		dbModel.setStatus(DbStatus.DEFAULT.getValue());
		dbModel.setDeleted(true);
		this.dbService.insert(dbModel);
		
		if(isCreateAdmin)
			this.dbUserService.createDefalutAdmin(dbModel.getId());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createUser", userId);
		
		List<DbModel> list = this.dbService.selectByMap(map);
		if(list.size() <= DB_AUTO_BUILD_COUNT) {
			//创建mcluster集群
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("dbId", dbModel.getId());
			StringBuffer mclusterName = new StringBuffer();
			mclusterName.append(userId).append("_").append(dbModel.getDbName());
			Boolean isExist= this.mclusterService.isExistByName(mclusterName.toString());
			int i = 0;
			while(!isExist) {
				isExist= this.mclusterService.isExistByName(mclusterName.toString() + i);
				i++;
			}
			if(i>0)
				mclusterName.append(i);
			params.put("mclusterName", mclusterName.toString());
			params.put("status", DbStatus.BUILDDING.getValue());
			params.put("hclusterId", dbModel.getHclusterId());
			
			this.auditAndBuild(params);
		} else {
			//邮件通知
			Map<String,Object> emailParams = new HashMap<String,Object>();
			//用户${createUser}于${createTime}申请数据库${dbName}，
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			emailParams.put("createUser", sessionService.getSession().getUserName());
			emailParams.put("createTime", sdf.format(new Date()));
			emailParams.put("dbName", dbModel.getDbName());
			MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",ERROR_MAIL_ADDRESS,"乐视云平台web-portal系统通知","auditDbNotice.ftl",emailParams);
			defaultEmailSender.sendMessage(mailMessage);
		}
	}
	
}
