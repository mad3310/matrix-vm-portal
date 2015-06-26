package com.letv.portal.service.openstack.exception;

@SuppressWarnings("serial")
public class PollingInterruptedException extends OpenStackException {
	public PollingInterruptedException(String msg, Throwable t) {
		super(msg, t);
	}

	public PollingInterruptedException(String msg) {
		super(msg);
	}

	public PollingInterruptedException(Throwable t) {
		super(t);
	}
}
