package com.letv.portal.service.adminoplog.impl;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.letv.common.model.BaseModel;
import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.service.adminoplog.MethodInvocation;
import com.letv.portal.service.adminoplog.MethodLogFormatter;

public class DefaultMethodLogFormater implements MethodLogFormatter {

	private int eventMaxLength;

	public void setEventMaxLength(int eventMaxLength) {
		this.eventMaxLength = eventMaxLength;
	}
	
	public int getEventMaxLength() {
		return eventMaxLength;
	}

	@Override
	public String format(MethodInvocation invocation) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		String methodString = methodToString(invocation.getMethod());
		stringBuilder.append(methodString);
		stringBuilder.append(" ");
		stringBuilder.append(argsToString(invocation.getJoinPoint().getArgs()));
		String log = stringBuilder.toString();
		if (log.length() <= eventMaxLength) {
			return log;
		} else {
			if (methodString.length() <= eventMaxLength) {
				return methodString;
			} else {
				return methodToSimpleString(invocation.getMethod());
			}
		}
	}

	protected String methodToSimpleString(Method method) {
		StringBuilder sb = new StringBuilder();
		sb.append(method.getDeclaringClass().getSimpleName()).append(".")
				.append(method.getName());
		return sb.toString();
	}

	protected String methodToString(Method method) {
		StringBuilder sb = new StringBuilder();
		sb.append(getTypeName(method.getDeclaringClass())).append('.');
		sb.append(method.getName()).append('(');
		Class<?>[] params = method.getParameterTypes(); // avoid clone
		for (int j = 0; j < params.length; j++) {
			sb.append(getTypeName(params[j]));
			if (j < (params.length - 1))
				sb.append(',');
		}
		sb.append(')');
		return sb.toString();
	}

	protected String getTypeName(Class<?> type) {
		if (type.isArray()) {
			try {
				Class<?> cl = type;
				int dimensions = 0;
				while (cl.isArray()) {
					dimensions++;
					cl = cl.getComponentType();
				}
				StringBuffer sb = new StringBuffer();
				sb.append(cl.getName());
				for (int i = 0; i < dimensions; i++) {
					sb.append("[]");
				}
				return sb.toString();
			} catch (Throwable e) { /* FALLTHRU */
			}
		}
		return type.getName();
	}

	protected String argsToString(Object[] args)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		boolean isFirstArg = true;
		for (Object arg : args) {
			if (isFirstArg) {
				isFirstArg = false;
			} else {
				stringBuilder.append(",");
			}

			if (arg instanceof Page) {
				Page page = (Page) arg;
				stringBuilder.append("{");
				stringBuilder.append("currentPage:");
				stringBuilder.append(page.getCurrentPage());
				stringBuilder.append(",recordsPerPage:");
				stringBuilder.append(page.getRecordsPerPage());
				stringBuilder.append("}");
			} else if (arg instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) arg;
				stringBuilder.append(objectMapper.writeValueAsString(request
						.getParameterMap()));
			} else if (arg instanceof BaseModel) {
				stringBuilder.append(objectMapper.writeValueAsString(arg));
			} else if (arg instanceof ResultObject) {
				stringBuilder.append("ResultObject");
			} else {
				stringBuilder.append(arg.toString());
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
