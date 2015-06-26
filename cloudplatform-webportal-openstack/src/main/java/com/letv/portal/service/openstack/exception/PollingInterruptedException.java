package com.letv.portal.service.openstack.exception;

@SuppressWarnings("serial")
public class PollingInterruptedException extends OpenStackException {

	public PollingInterruptedException(Throwable t) {
		super("后台服务中断", t);
	}
}
