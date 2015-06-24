package com.letv.portal.service.openstack.exception;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class APINotAvailableException extends OpenStackException {
	
	public APINotAvailableException(Class<?> apiClass){
		super(MessageFormat.format("{0} is not available.", apiClass.getName()));
	}

	public APINotAvailableException(String msg, Throwable t) {
		super(msg, t);
	}

	public APINotAvailableException(String msg) {
		super(msg);
	}

	public APINotAvailableException(Throwable t) {
		super(t);
	}

}
