package com.letv.portal.service.openstack.exception;

import java.text.MessageFormat;

public class RegionNotFoundException extends OpenStackException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -887171912467136040L;

	public RegionNotFoundException(String region) {
		super(MessageFormat.format("Region \"{0}\" is not found.", region),MessageFormat.format("区域“{0}”找不到。", region));
	}

}
