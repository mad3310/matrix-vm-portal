package com.letv.portal.controller.cloudvm;

import com.letv.common.session.SessionServiceImpl;
import com.letv.lcp.openstack.service.session.IOpenStackSession;

/**
 * Created by zhouxianguang on 2015/6/12.
 */
public class Util {
	public static IOpenStackSession session(SessionServiceImpl sessionService) {
		return (IOpenStackSession) sessionService.getSession()
				.getOpenStackSession();
	}

	public static String optPara(String para) {
		if (para != null && para.isEmpty()) {
			return null;
		}
		return para;
	}

	public static long userId(SessionServiceImpl sessionService){
		return sessionService.getSession().getUserId();
	}
}
