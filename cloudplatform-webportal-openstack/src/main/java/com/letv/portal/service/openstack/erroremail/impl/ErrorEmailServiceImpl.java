package com.letv.portal.service.openstack.erroremail.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

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

}
