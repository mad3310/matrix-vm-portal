package com.letv.portal.service.adminoplog.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.BeansException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.letv.common.exception.MatrixException;
import com.letv.common.util.SpringContextUtil;
import com.letv.portal.model.adminoplog.AoLogType;
import com.letv.portal.service.adminoplog.AoLog;
import com.letv.portal.service.adminoplog.ClassAoLog;
import com.letv.portal.service.adminoplog.ClassLogFormatter;
import com.letv.portal.service.adminoplog.IAdminOpLogService;
import com.letv.portal.service.adminoplog.MethodInvocation;
import com.letv.portal.service.adminoplog.MethodLogFormatter;

public class AdminOpLogAspect {

	private static final Logger logger = LoggerFactory
			.getLogger(AdminOpLogAspect.class);

	@Resource
	private IAdminOpLogService logService;

	private MethodLogFormatter methodLogFormatter;

	private ClassLogFormatter classLogFormatter;

	public void setMethodLogFormatter(MethodLogFormatter methodLogFormatter) {
		this.methodLogFormatter = methodLogFormatter;
	}

	public void setClassLogFormatter(ClassLogFormatter classLogFormatter) {
		this.classLogFormatter = classLogFormatter;
	}

	public void before(JoinPoint joinPoint) {
		try {
			// get method,class,annotation
			final MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) joinPoint;
			final Field methodInvocationField = methodPoint.getClass()
					.getDeclaredField("methodInvocation");
			methodInvocationField.setAccessible(true);
			final ProxyMethodInvocation proxyMethodInvocation = (ProxyMethodInvocation) methodInvocationField
					.get(methodPoint);
			final Method methodInvocationMethod = proxyMethodInvocation
					.getMethod();
			final Field methodField = methodInvocationMethod.getClass()
					.getDeclaredField("root");
			methodField.setAccessible(true);

			final Method method = (Method) methodField
					.get(methodInvocationMethod);
			final Class<?> klass = method.getDeclaringClass();

			final RequestMapping requestMapping = method
					.getAnnotation(RequestMapping.class);
			final AoLog methodAoLog = method.getAnnotation(AoLog.class);
			final ClassAoLog classAoLog = klass.getAnnotation(ClassAoLog.class);

			// log
			if (!isIgnore(methodAoLog, classAoLog, requestMapping)) {

				AoLogType logType = null;
				if (methodAoLog != null && methodAoLog.type() != null
						&& methodAoLog.type() != AoLogType.NULL) {
					logType = methodAoLog.type();
				}

				String module = "";
				if (methodAoLog != null && methodAoLog.module() != null
						&& !methodAoLog.module().isEmpty()) {
					module = methodAoLog.module();
				} else if (classAoLog != null && classAoLog.module() != null) {
					module = classAoLog.module();
				}

				String desc = "";
				if (methodAoLog != null && methodAoLog.desc() != null) {
					desc = methodAoLog.desc();
				}

				logService.add(generateLogContent(new MethodInvocation(
						methodAoLog, classAoLog, method, klass, joinPoint)),
						logType, module, desc);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logService.add(e.getMessage()!=null?e.getMessage():"", AoLogType.NULL, "", e.getLocalizedMessage()!=null?e.getLocalizedMessage():"");
			throw new MatrixException("后台错误", e);
		}
	}

	private String generateLogContent(MethodInvocation methodInvocation)
			throws Exception {
		return generateClassLog(generateMethodLog(methodInvocation),
				methodInvocation);
	}

	private String generateMethodLog(MethodInvocation methodInvocation)
			throws Exception {
		if (methodInvocation.getMethodAoLog() != null
				&& methodInvocation.getMethodAoLog().formatter() != null && !methodInvocation.getMethodAoLog().formatter().equals(MethodLogFormatter.class)) {
			MethodLogFormatter methodLogFormatter = null;
			try {
				methodLogFormatter = SpringContextUtil.getApplicationContext()
						.getBean(methodInvocation.getMethodAoLog().formatter());
			} catch (BeansException be) {
				methodLogFormatter = methodInvocation.getMethodAoLog()
						.formatter().newInstance();
			}
			return methodLogFormatter.format(methodInvocation);
		} else {
			return methodLogFormatter.format(methodInvocation);
		}
	}

	private String generateClassLog(String methodLog,
			MethodInvocation methodInvocation) throws Exception {
		if (methodInvocation.getClassAoLog() != null
				&& methodInvocation.getClassAoLog().formatter() != null && !methodInvocation.getClassAoLog().formatter().equals(ClassLogFormatter.class)) {
			ClassLogFormatter classLogFormatter = null;
			try {
				classLogFormatter = SpringContextUtil.getApplicationContext()
						.getBean(methodInvocation.getClassAoLog().formatter());
			} catch (BeansException be) {
				classLogFormatter = methodInvocation.getClassAoLog()
						.formatter().newInstance();
			}
			return classLogFormatter.format(methodLog, methodInvocation);
		} else {
			return classLogFormatter.format(methodLog, methodInvocation);
		}
	}

	private boolean isIgnore(AoLog methodAoLog, ClassAoLog classAoLog,
			RequestMapping requestMapping) {
		if (methodAoLog != null) {
			if (methodAoLog.ignore()) {
				return true;
			}
		}

		if (classAoLog != null) {
			if (classAoLog.ignore()) {
				return true;
			}
		}

		if (methodAoLog != null) {
			return methodAoLog.ignore();
		}

		if (requestMapping == null) {
			return true;
		} else {
			RequestMethod[] requestMethods = requestMapping.method();
			boolean isAllGetMethod = true;
			for (RequestMethod requestMethod : requestMethods) {
				if (requestMethod != RequestMethod.GET) {
					isAllGetMethod = false;
					break;
				}
			}
			if (isAllGetMethod) {
				return true;
			}
		}

		return false;
	}

}
