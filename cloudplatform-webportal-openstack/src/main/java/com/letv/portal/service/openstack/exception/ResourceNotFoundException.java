package com.letv.portal.service.openstack.exception;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends OpenStackException {

	public ResourceNotFoundException(String rcType, String userRcType, String id) {
		super(MessageFormat.format("{0} \"{1}\" is not found.", rcType, id),
				MessageFormat.format("{0}“{1}”找不到。", userRcType, id));
	}

}
