package com.letv.portal.service.adminoplog;

public interface MethodLogFormatter {
	String format(MethodInvocation invocation) throws Exception;
}
