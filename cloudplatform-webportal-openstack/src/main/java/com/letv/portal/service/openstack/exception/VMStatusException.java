package com.letv.portal.service.openstack.exception;

@SuppressWarnings("serial")
public class VMStatusException extends OpenStackException {

	public VMStatusException(String msg, Throwable t) {
		super(msg, t);
	}

	public VMStatusException(String msg) {
		super(msg);
	}

	public VMStatusException(Throwable t) {
		super(t);
	}

}
