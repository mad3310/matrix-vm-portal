package com.letv.portal.service.openstack.util;

import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackSessionImpl;
import org.codehaus.jackson.type.TypeReference;

import java.util.Random;
import java.util.UUID;

public class Util {

	public static OpenStackSessionImpl session(SessionServiceImpl sessionService) {
		return (OpenStackSessionImpl) sessionService.getSession()
				.getOpenStackSession();
	}
}
