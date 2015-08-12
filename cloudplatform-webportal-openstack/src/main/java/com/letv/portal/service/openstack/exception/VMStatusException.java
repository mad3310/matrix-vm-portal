package com.letv.portal.service.openstack.exception;

@SuppressWarnings("serial")
public class VMStatusException extends OpenStackException {

	public VMStatusException(String msg, String userMessage) {
		super(msg, userMessage);
	}

}
