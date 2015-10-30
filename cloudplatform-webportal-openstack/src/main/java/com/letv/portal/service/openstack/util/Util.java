package com.letv.portal.service.openstack.util;

import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.exception.OpenStackException;
import org.codehaus.jackson.type.TypeReference;

import java.util.Random;
import java.util.UUID;

public class Util {

	public static OpenStackSession session(SessionServiceImpl sessionService) {
		return (OpenStackSession) sessionService.getSession()
				.getOpenStackSession();
	}
}
