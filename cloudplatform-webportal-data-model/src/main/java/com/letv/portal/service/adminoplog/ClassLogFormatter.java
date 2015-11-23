package com.letv.portal.service.adminoplog;

public interface ClassLogFormatter {
	String format(String methodLog, MethodInvocation invocation)
			throws Exception;
}
