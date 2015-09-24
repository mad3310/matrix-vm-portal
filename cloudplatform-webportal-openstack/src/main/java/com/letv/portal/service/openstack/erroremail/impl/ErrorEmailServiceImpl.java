package com.letv.portal.service.openstack.erroremail.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.letv.common.util.ExceptionUtils;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.email.bean.MailMessage;
import com.letv.common.email.impl.DefaultEmailSender;
import com.letv.portal.service.openstack.erroremail.ErrorEmailService;

@Service
public class ErrorEmailServiceImpl implements ErrorEmailService {

	@Value("${error.email.from}")
	private String errorEmailFrom;

	@Value("${error.email.to}")
	private String errorEmailTo;

	@Value("${error.email.enabled}")
	private String errorEmailEnabledStr;
	private boolean errorEmailEnabled;
	
	@Autowired
	private DefaultEmailSender defaultEmailSender;

	@PostConstruct
	public void init() {
		errorEmailEnabled = Boolean.parseBoolean(errorEmailEnabledStr);
	}

	@Override
	public void sendErrorEmail(Map<String, Object> mailMessageModel) {
		if (errorEmailEnabled) {
			MailMessage mailMessage = new MailMessage(errorEmailFrom,
					errorEmailTo, "异常错误发生", "erroremail.ftl", mailMessageModel);
			mailMessage.setHtml(true);
			defaultEmailSender.sendMessage(mailMessage);
		}
	}

	@Override
	public void sendExceptionEmail(Exception exception, String function, long userId, String contextMessage) {
		Map<String, Object> mailMessageModel = new HashMap<String, Object>();
		mailMessageModel.put("requestUrl", "功能: " + function);
		mailMessageModel.put("exceptionId", "用户ID：" + userId);
		mailMessageModel.put("exceptionParams", "上下文信息：" + contextMessage);
		String exceptionMessage = exception.getMessage();
		if (exception instanceof OpenStackException) {
			exceptionMessage += (" " + ((OpenStackException) exception)
					.getUserMessage());
		}
		mailMessageModel.put("exceptionMessage", exceptionMessage);
		mailMessageModel.put("exceptionContent",
				ExceptionUtils.getRootCauseStackTrace(exception));

		this.sendErrorEmail(mailMessageModel);
	}

}
