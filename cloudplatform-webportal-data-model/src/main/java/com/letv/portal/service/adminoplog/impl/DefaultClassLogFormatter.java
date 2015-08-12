package com.letv.portal.service.adminoplog.impl;

import com.letv.portal.service.adminoplog.ClassLogFormatter;
import com.letv.portal.service.adminoplog.MethodInvocation;

public class DefaultClassLogFormatter implements ClassLogFormatter {

	@Override
	public String format(String methodLog, MethodInvocation invocation)
			throws Exception {
		return methodLog;
	}

}
