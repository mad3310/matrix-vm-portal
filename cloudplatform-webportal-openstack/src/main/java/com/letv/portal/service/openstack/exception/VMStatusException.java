package com.letv.portal.service.openstack.exception;

public class VMStatusException extends UserOperationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6445293197818536717L;

	public VMStatusException(String msg, String userMessage) {
		super(msg, userMessage);
	}

}
