package com.letv.portal.service.openstack.exception;

import java.text.MessageFormat;

public class APINotAvailableException extends OpenStackException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2021917856831641742L;

	public APINotAvailableException(Class<?> apiClass) {
		super(
				MessageFormat.format("{0} is not available.",
						apiClass.getName()), "后台服务不可用");
	}

}
