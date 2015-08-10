package com.letv.portal.controller.cloudvm;

import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackSession;

/**
 * Created by zhouxianguang on 2015/6/12.
 */
public class Util {
	public static OpenStackSession session(SessionServiceImpl sessionService) {
		return (OpenStackSession) sessionService.getSession()
				.getOpenStackSession();
	}

	public static String optPara(String para) {
		if (para != null && para.isEmpty()) {
			return null;
		}
		return para;
	}
}
