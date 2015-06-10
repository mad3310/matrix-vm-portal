package com.letv.portal.service.openstack.exception;

@Deprecated
@SuppressWarnings("serial")
public class RegionMismatchException extends OpenStackException {

	public RegionMismatchException(String msg) {
		super(msg);
	}

	public RegionMismatchException(String msg, Throwable t) {
		super(msg, t);
	}

	public RegionMismatchException(Throwable t) {
		super(t);
	}

}
