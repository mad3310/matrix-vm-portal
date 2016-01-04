package com.letv.lcp.openstack.exception;

public class PollingInterruptedException extends OpenStackException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4898097100480102767L;

	public PollingInterruptedException(Throwable t) {
		super("后台服务中断", t);
	}
}
