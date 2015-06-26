package com.letv.portal.service.openstack.exception;

@SuppressWarnings("serial")
public class TaskNotFinishedException extends OpenStackException {

	public TaskNotFinishedException() {
		super("Current task of vm is not finished.", "虚拟机的当前任务没有完成。");
	}

}
