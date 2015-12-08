package com.letv.portal.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.email.bean.MailMessage;
import com.letv.common.email.impl.DefaultEmailSender;

@Service("exceptionEmailServiceUtil")
public class ExceptionEmailServiceUtil {

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

	public void sendErrorEmail(String exceptionMessage, String exceptionContent) {
		Map<String, Object> mailMessageModel = new HashMap<String, Object>();
		mailMessageModel.put("exceptionMessage", exceptionMessage);
		mailMessageModel.put("exceptionContent", exceptionContent);
		if (errorEmailEnabled) {
			MailMessage mailMessage = new MailMessage(errorEmailFrom,
					errorEmailTo, "异常错误发生", "erroremail.ftl", mailMessageModel);
			mailMessage.setHtml(false);
			defaultEmailSender.sendMessage(mailMessage);
		}
	}


}
