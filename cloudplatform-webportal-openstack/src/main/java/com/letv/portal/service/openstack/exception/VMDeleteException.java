package com.letv.portal.service.openstack.exception;

@SuppressWarnings("serial")
public class VMDeleteException extends OpenStackException {

	public VMDeleteException(String msg, Throwable t) {
		super(msg, t);
	}

	public VMDeleteException(String msg) {
		super(msg);
	}

	public VMDeleteException(Throwable t) {
		super(t);
	}

}
