package com.letv.portal.service.openstack.exception;

@SuppressWarnings("serial")
public class TaskNotFinishedException extends OpenStackException {

	public TaskNotFinishedException() {
		super("Current task of vm is not finished.");
	}

	public TaskNotFinishedException(String msg, Throwable t) {
		super(msg, t);
	}

	public TaskNotFinishedException(String msg) {
		super(msg);
	}

	public TaskNotFinishedException(Throwable t) {
		super(t);
	}

}
