package com.letv.portal.service.openstack.exception;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class APINotAvailableException extends OpenStackException {

	public APINotAvailableException(Class<?> apiClass) {
		super(
				MessageFormat.format("{0} is not available.",
						apiClass.getName()), "后台服务不可用");
	}

}
