package com.letv.portal.service.adminoplog;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;

public class MethodInvocation {
	private AoLog methodAoLog;
	private ClassAoLog classAoLog;
	private Method method;
	private Class<?> klass;
	private JoinPoint joinPoint;

	public MethodInvocation(AoLog methodAoLog, ClassAoLog classAoLog,
			Method method, Class<?> klass, JoinPoint joinPoint) {
		this.methodAoLog = methodAoLog;
		this.classAoLog = classAoLog;
		this.method = method;
		this.klass = klass;
		this.joinPoint = joinPoint;
	}

	public AoLog getMethodAoLog() {
		return methodAoLog;
	}

	public ClassAoLog getClassAoLog() {
		return classAoLog;
	}

	public Method getMethod() {
		return method;
	}

	public Class<?> getKlass() {
		return klass;
	}

	public JoinPoint getJoinPoint() {
		return joinPoint;
	}

}
