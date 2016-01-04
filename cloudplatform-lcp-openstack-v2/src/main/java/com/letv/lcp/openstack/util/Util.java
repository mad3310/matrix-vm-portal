package com.letv.lcp.openstack.util;

import com.letv.common.session.SessionServiceImpl;
import com.letv.lcp.openstack.service.session.impl.OpenStackSessionImpl;

public class Util {

	public static OpenStackSessionImpl session(SessionServiceImpl sessionService) {
		return (OpenStackSessionImpl) sessionService.getSession()
				.getOpenStackSession();
	}
}
