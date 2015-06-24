package com.letv.portal.service.openstack.exception;

@SuppressWarnings("serial")
public class OpenStackException extends Exception {

	public OpenStackException(String msg) {
		super(msg);
	}

	public OpenStackException(Throwable t) {
		super(t);
	}

	public OpenStackException(String msg, Throwable t) {
		super(msg, t);
	}
}
