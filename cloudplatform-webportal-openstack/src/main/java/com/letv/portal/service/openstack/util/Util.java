package com.letv.portal.service.openstack.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.letv.common.exception.MatrixException;
import com.letv.common.exception.ValidateException;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.letv.portal.service.openstack.exception.OpenStackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	public static void processBillingException(Exception ex) {
		logger.error(ex.getMessage(), ex);
		OpenStackServiceImpl.getOpenStackServiceGroup().getErrorEmailService().sendExceptionEmail(ex, "计费系统", null, "");
	}

	public static String generateRandomSessionId(){
		return UUID.randomUUID().toString();
	}

	private static final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void concurrentRunAndWait(Runnable currentThreadTask, Runnable... otherTasks) {
        ListenableFuture[] futures = new ListenableFuture[otherTasks.length];
        for (int i = 0; i < otherTasks.length; i++) {
            futures[i] = executorService.submit(otherTasks[i]);
        }

        currentThreadTask.run();

        try {
            Futures.successfulAsList(futures).get();
        } catch (Exception e) {
            throw new MatrixException("后台错误", e);
        }
    }

	public static void concurrentRun(Runnable... tasks) {
		for (Runnable task : tasks) {
			executorService.submit(task);
		}
	}

	public static void throwException(Exception ex) throws OpenStackException {
		if (ex instanceof ValidateException) {
			throw (ValidateException) ex;
		} else if (ex instanceof MatrixException) {
			throw (MatrixException) ex;
		} else if (ex instanceof OpenStackException) {
			throw (OpenStackException) ex;
		}
		throw new OpenStackException("后台错误", ex);
	}

	public static void throwMatrixException(Exception ex) throws MatrixException {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		} else if (ex instanceof OpenStackException) {
			throw ((OpenStackException) ex).matrixException();
		} else {
			throw new MatrixException("后台错误", ex);
		}
	}

	public static String generateRandomPassword(int length) {
		final String charactors = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();
		for (int count = 0; count < length; count++) {
			int index = random.nextInt(charactors.length());
			stringBuilder.append(charactors.charAt(index));
		}
		return stringBuilder.toString();
	}

	public static <T> T fromJson(String json,
								 @SuppressWarnings("rawtypes") TypeReference typeReference, boolean compatibleWithUnknownProperties)
			throws OpenStackException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if (compatibleWithUnknownProperties) {
				objectMapper.getDeserializationConfig().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			}
			return objectMapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			throw new OpenStackException("请求数据格式错误", e);
		} catch (JsonMappingException e) {
			throw new OpenStackException("请求数据格式错误", e);
		} catch (IOException e) {
			throw new OpenStackException("后台服务错误", e);
		}
	}

	public static <T> T fromJson(String json,
								 @SuppressWarnings("rawtypes") TypeReference typeReference) throws OpenStackException {
		return fromJson(json, typeReference, false);
	}

	public static void throwExceptionOfResponse(HttpResponse resp)
			throws OpenStackException {
		StringBuilder exceptionMessageBuilder = new StringBuilder();
		exceptionMessageBuilder.append("Error OpenStack http response<br>");

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			Map<String, Object> responseMap = new HashMap<String, Object>();

			StatusLine statusLine = resp.getStatusLine();
			if (statusLine != null) {
				Map<String, Object> responseStatusLineMap = new HashMap<String, Object>();
				responseStatusLineMap.put("protocol_version", statusLine
						.getProtocolVersion().toString());
				responseStatusLineMap.put("reason_phrase",
						statusLine.getReasonPhrase());
				responseStatusLineMap.put("status_code",
						statusLine.getStatusCode());
				responseMap.put("status_line", responseStatusLineMap);
			}

			Map<String, Object> responseHeaderMap = new HashMap<String, Object>();
			for (Header header : resp.getAllHeaders()) {
				responseHeaderMap.put(header.getName(), header.getValue());
			}
			responseMap.put("header", responseHeaderMap);

			if (resp.getEntity() != null) {
				responseMap.put("entity", httpEntityToString(resp.getEntity()));
			}

			String responseMapJson = objectMapper
					.writeValueAsString(responseMap);
			exceptionMessageBuilder.append(responseMapJson);
		} catch (Exception ex) {
			exceptionMessageBuilder.append(ex.getMessage());
			exceptionMessageBuilder.append("<br>");
			exceptionMessageBuilder.append(ExceptionUtils.getStackTrace(ex)
					.replaceAll("\\\n", "<br>"));
		}

		throw new OpenStackException(exceptionMessageBuilder.toString(),
				"后台服务异常");
	}

	private static String httpEntityToString(HttpEntity httpEntity)
			throws IllegalStateException, IOException {
		String contentEncoding = "UTF-8";
		Header contentEncodingHeader = httpEntity.getContentEncoding();

		if (contentEncodingHeader != null) {
			String contentEncodingHeaderValue = contentEncodingHeader
					.getValue();
			if (contentEncodingHeaderValue != null) {
				contentEncoding = contentEncodingHeaderValue;
			}
		}

		InputStream rcis = httpEntity.getContent();
		String str = "";
		try {
			str = IOUtils.toString(rcis, contentEncoding);
		} finally {
			rcis.close();
		}

		return str;
	}

	public static OpenStackSession session(SessionServiceImpl sessionService) {
		return (OpenStackSession) sessionService.getSession()
				.getOpenStackSession();
	}
}
