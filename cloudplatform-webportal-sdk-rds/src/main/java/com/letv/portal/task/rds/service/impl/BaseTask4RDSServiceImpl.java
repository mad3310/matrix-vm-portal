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
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IMclusterService;

@Component("baseTaskService")
public class BaseTask4RDSServiceImpl implements IBaseTaskService{

	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
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
		//发送错误邮件
		this.buildResultToMgr("RDS服务创建", tr.isSuccess()?"创建成功":"创建失败", tr.getResult(), ERROR_MAIL_ADDRESS);
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

	/*
	public TaskResult analyzeRestServiceResult(String result) throws Exception{
		TaskResult tr = new TaskResult();
		RestServiceResult rsr = getRestServiceResult(result);
		if(rsr == null) {
			tr.setSuccess(false);
			tr.setResult("api connect failed");
			return tr;
		}
		
		boolean isSucess = Constant.PYTHON_API_RESPONSE_SUCCESS.equals(rsr.getMeta().getCode());
		tr.setSuccess(isSucess);
		if(isSucess) {
			tr.setResult(rsr.getResponse().getMessage());
		} else {
			tr.setResult(rsr.getResponse().getErrorDetail());
		}
		return tr;
		
	}
	
	public RestServiceResult getRestServiceResult(String result) throws Exception{
		if(StringUtils.isEmpty(result))
			return null;
		ObjectMapper resultMapper = new ObjectMapper();
		RestServiceResult rsr = resultMapper.readValue(result, RestServiceResult.class);
		return rsr;
	}
	*/
	
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
}
