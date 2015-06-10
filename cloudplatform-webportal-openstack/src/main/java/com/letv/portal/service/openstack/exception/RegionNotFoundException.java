package com.letv.portal.service.openstack.exception;

@SuppressWarnings("serial")
public class RegionNotFoundException extends OpenStackException {

	public RegionNotFoundException(String msg) {
		super(msg);
	}

	public RegionNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

	public RegionNotFoundException(Throwable t) {
		super(t);
	}

}
