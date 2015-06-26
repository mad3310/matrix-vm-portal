package com.letv.portal.service.openstack.exception;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class RegionNotFoundException extends OpenStackException {

	public RegionNotFoundException(String region) {
		super(MessageFormat.format("Region \"{0}\" is not found.", region),MessageFormat.format("区域“{0}”找不到。", region));
	}

}
