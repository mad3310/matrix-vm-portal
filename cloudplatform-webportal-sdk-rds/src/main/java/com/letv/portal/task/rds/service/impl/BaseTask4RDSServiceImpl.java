package com.letv.portal.task.rds.service.impl;

import java.util.HashMap;
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
import com.letv.portal.model.DbModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.BaseTaskServiceImpl;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IMclusterService;

@Component("baseRDSTaskService")
public class BaseTask4RDSServiceImpl extends BaseTaskServiceImpl implements IBaseTaskService{

	@Value("${service.notice.email.to}")
	private String SERVICE_NOTICE_MAIL_ADDRESS;
	@Autowired
	private IMclusterService mclusterService;
	@Autowired
	private IDbService dbService;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	private final static Logger logger = LoggerFactory.getLogger(BaseTask4RDSServiceImpl.class);
	
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
		//发送错误邮件
		this.buildResultToMgr("RDS服务("+serverName+")创建", tr.isSuccess()?"成功":"失败", tr.getResult(), SERVICE_NOTICE_MAIL_ADDRESS);
		//业务处理
		this.serviceOver(tr);
	}
	
	private void serviceOver(TaskResult tr) {
		Map<String, Object> params = (Map<String, Object>) tr.getParams();
		Long mclusterId = getLongFromObject(params.get("mclusterId"));
		Long dbId = getLongFromObject(params.get("dbId"));
		if(mclusterId == null)
			throw new ValidateException("params's mclusterId is null");
		//执行业务
		MclusterModel mclusterModel = this.mclusterService.selectById(mclusterId);
		if(mclusterModel == null)
			throw new ValidateException("mclusterModel is null by mclusterId:" + mclusterId);
		if(tr.isSuccess()) {
			mclusterModel.setStatus(MclusterStatus.RUNNING.getValue());
			this.mclusterService.audit(mclusterModel);
			logger.info("RDS service build success:" + mclusterModel.getMclusterName());
		} else {
			mclusterModel.setStatus(MclusterStatus.BUILDFAIL.getValue());
			this.mclusterService.audit(mclusterModel);
			logger.info("RDS service build failed:" + mclusterModel.getMclusterName());
			if(dbId==null)
				return;
			DbModel dbModel = this.dbService.selectById(dbId);
			if(dbModel.getStatus() != DbStatus.NORMAL.getValue()) {
				dbModel.setStatus(DbStatus.BUILDFAIL.getValue());
				this.dbService.updateBySelective(dbModel);
			}
		}
	}

	@Override
	public void callBack(TaskResult tr) {
	}

	@Override
	public void beforExecute(Map<String, Object> params) {
	}
}
