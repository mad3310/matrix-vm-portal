package com.letv.portal.service.openstack.util;

import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.impl.OpenStackSessionImpl;

public class Util {

	public static OpenStackSessionImpl session(SessionServiceImpl sessionService) {
		return (OpenStackSessionImpl) sessionService.getSession()
				.getOpenStackSession();
	}
}
