package com.letv.portal.service.openstack.exception;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class VMDeleteException extends OpenStackException {

	public VMDeleteException(String vmId) {
		super(MessageFormat.format(
				"VM \"{0}\" delete failed.", vmId),MessageFormat.format(
						"虚拟机“{0}”删除失败。", vmId));
	}

}
