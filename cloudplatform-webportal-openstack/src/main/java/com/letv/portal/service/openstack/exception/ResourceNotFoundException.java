package com.letv.portal.service.openstack.exception;

import java.text.MessageFormat;

public class ResourceNotFoundException extends OpenStackException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5162654075606572632L;

	public ResourceNotFoundException(String rcType, String userRcType, String id) {
		super(MessageFormat.format("{0} \"{1}\" is not found.", rcType, id),
				MessageFormat.format("{0}“{1}”找不到。", userRcType, id));
	}

}
