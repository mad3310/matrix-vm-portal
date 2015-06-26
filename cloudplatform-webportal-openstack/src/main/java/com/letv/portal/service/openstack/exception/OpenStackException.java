package com.letv.portal.service.openstack.exception;

import com.letv.common.exception.MatrixException;

@SuppressWarnings("serial")
public class OpenStackException extends Exception {

	private String userMessage;

	public OpenStackException(String msg, String userMessage) {
		super(msg);
		this.userMessage = userMessage;
	}

	public OpenStackException(String userMessage, Throwable t) {
		super(t);
		this.userMessage = userMessage;
	}

	public OpenStackException(String msg, String userMessage, Throwable t) {
		super(msg, t);
		this.userMessage = userMessage;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public MatrixException matrixException() {
		return new MatrixException(userMessage, this);
	}
}
