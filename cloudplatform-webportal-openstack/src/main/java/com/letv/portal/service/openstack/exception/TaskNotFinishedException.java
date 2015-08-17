package com.letv.portal.service.openstack.exception;

public class TaskNotFinishedException extends OpenStackException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2145426469507645119L;

	public TaskNotFinishedException() {
		super("Current task of vm is not finished.", "虚拟机的当前任务没有完成。");
	}

}
