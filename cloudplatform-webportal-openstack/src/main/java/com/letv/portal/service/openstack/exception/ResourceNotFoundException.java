package com.letv.portal.service.openstack.exception;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends OpenStackException {

	public ResourceNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

	public ResourceNotFoundException(Throwable t) {
		super(t);
	}

}
