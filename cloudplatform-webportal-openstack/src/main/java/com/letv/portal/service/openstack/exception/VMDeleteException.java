package com.letv.portal.service.openstack.exception;

import java.text.MessageFormat;

public class VMDeleteException extends OpenStackException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8333207276254181692L;

	public VMDeleteException(String vmId) {
		super(MessageFormat.format(
				"VM \"{0}\" delete failed.", vmId),MessageFormat.format(
						"虚拟机“{0}”删除失败。", vmId));
	}

}
