package com.letv.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.StringUtils;

/**
 * Program Name: HttpClient <br>
 * Description: httpclient封装<br>
 * 
 * @author name: liuhao1 <br>
 *         Written Date: 2014年9月11日 <br>
 *         Modified By: <br>
 *         Modified Date: <br>
 */
@SuppressWarnings("deprecation")
public class HttpClient {
	private final static Logger logger = LoggerFactory
			.getLogger(HttpClient.class);

	public static String post(String url, Map<String, String> params) {
		return post(url, params, null, null);
	}

	public static String postCbaseManager(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = getHttpclient(null, null);
		String body = null;

		logger.info("create httppost:" + url);
		HttpPost post = postForm(url, params);

		HttpResponse response = sendRequest(httpclient, post);
		logger.info("get response from http server..");
		if (response == null) {
			logger.info("get response from http server.. faild");
			return null;
		}
		HttpEntity entity = response.getEntity();

		logger.info("response status: " + response.getStatusLine());

		try {
			logger.info(EntityUtils.toString(entity));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		body = Integer.toString(response.getStatusLine().getStatusCode());

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String postCbaseManager(String url, String ip, String port,
			Map<String, String> params, String username, String password) {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		String body = null;

		HttpHost targetHost = new HttpHost(ip, Integer.valueOf(port), "http");

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(username, password));

		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());

		// Add AuthCache to the execution context
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);

		logger.info("create httppost:" + url);
		HttpPost post = postForm(url, params);

		HttpResponse response = sendRequest(httpclient, targetHost, post,
				context);
		logger.info("get response from http server..");
		if (response == null) {
			logger.info("get response from http server.. faild");
			return null;
		}

		HttpEntity entity = response.getEntity();

		logger.info("response status: " + response.getStatusLine());

		try {
			logger.info(EntityUtils.toString(entity));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		body = Integer.toString(response.getStatusLine().getStatusCode());

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String post(String url, Map<String, String> params,
			String username, String password) {

		DefaultHttpClient httpclient = getHttpclient(username, password);
		String body = null;

		logger.info("create httppost:" + url);
		HttpPost post = postForm(url, params);

		body = invoke(httpclient, post);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String post(String url, Map<String, String> params,
			int connectionTimeout, int soTimeout, String username,
			String password) {

		DefaultHttpClient httpclient = getHttpclient(connectionTimeout,
				soTimeout, username, password);
		String body = null;

		logger.info("create httppost:" + url);
		HttpPost post = postForm(url, params);

		body = invoke(httpclient, post);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String postObject(String url, Object obj) {
		return postObject(url, obj, null, null);
	}

	public static String postObject(String url, Object obj, String username,
			String password) {
		DefaultHttpClient httpclient = getHttpclient(username, password);
		String body = null;
		logger.info("create httppost:" + url);
		HttpPost httppost = new HttpPost(url);
		try {
			httppost.setHeader("Content-Type", "application/json");
			httppost.setEntity(new StringEntity(obj.toString()));
			body = invoke(httpclient, httppost);
			httpclient.getConnectionManager().shutdown();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return body;
	}

	public static String get(String url) {
		return get(url, null, null);
	}

	public static String get(String url, String username, String password) {
		DefaultHttpClient httpclient = getHttpclient(username, password);
		String body = null;

		logger.info("create httpget:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}
	
	public static String getCbaseManager(String url, String username, String password) {
		DefaultHttpClient httpclient = getHttpclient(username, password);
		String body = null;

		logger.info("create httpget:" + url);
		HttpGet get = new HttpGet(url);
		
		HttpResponse response = sendRequest(httpclient, get);
		
		logger.info("get response from http server..");
		if (response == null) {
			logger.info("get response from http server.. faild");
			return null;
		}
		HttpEntity entity = response.getEntity();

		logger.info("response status: " + response.getStatusLine());

		try {
			body = EntityUtils.toString(entity);
			logger.info(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	private static String invoke(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}

	private static String paseResponse(HttpResponse response) {
		logger.info("get response from http server..");
		if (response == null) {
			logger.info("get response from http server.. faild");
			return null;
		}
		HttpEntity entity = response.getEntity();

		logger.info("response status: " + response.getStatusLine());
		String charset = EntityUtils.getContentCharSet(entity);
		logger.info(charset);

		String body = null;
		try {
			body = EntityUtils.toString(entity);
			logger.info(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}

	private static HttpResponse sendRequest(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {
		logger.info("execute post...");
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return response;
	}

	private static CloseableHttpResponse sendRequest(
			CloseableHttpClient httpclient, HttpHost targetHost, HttpPost post,
			HttpClientContext context) {
		logger.info("execute post...");
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(targetHost, post, context);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public static String detele(String url, String username, String password) {
		DefaultHttpClient httpclient = getHttpclient(username, password);
		String body = null;
		logger.info("create httpdelete:" + url);
		HttpDelete delete = new HttpDelete(url);
		body = invoke(httpclient, delete);
		httpclient.getConnectionManager().shutdown();
		return body;
	}
	public static String detele(String url, String username, String password,int connectionTimeout, int soTimeout) {
		DefaultHttpClient httpclient = getHttpclient(connectionTimeout,soTimeout,username, password);
		String body = null;
		logger.info("create httpdelete:" + url);
		HttpDelete delete = new HttpDelete(url);
		body = invoke(httpclient, delete);
		httpclient.getConnectionManager().shutdown();
		return body;
	}

	private static HttpPost postForm(String url, Map<String, String> params) {
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		if (params != null && !params.isEmpty()) {
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
				logger.info("param-->" + key + ":" + params.get(key));
			}
		}

		try {
			logger.info("set utf-8 form entity to httppost");
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return httpost;
	}

	public static String get(String url, boolean isMonitor) {
		return get(url, isMonitor, null, null);
	}

	public static String get(String url, boolean isMonitor, String username,
			String password) {
		DefaultHttpClient httpclient = getHttpclient(isMonitor, username,
				password);
		String body = null;

		logger.info("create httpget:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}
	public static HttpResponse getResponse(String url,int connectionTimeout, int soTimeout) {
		
		DefaultHttpClient httpclient = getHttpclient(connectionTimeout,
				soTimeout, null, null);

		logger.info("create httpget:" + url);
		HttpGet get = new HttpGet(url);
		HttpResponse response = sendRequest(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return response;
	}

	private static DefaultHttpClient getHttpclient(Boolean isMonitor,
			String username, String password) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		if (!StringUtils.isNullOrEmpty(username)) {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			UsernamePasswordCredentials usernamePassword = new UsernamePasswordCredentials(
					username, password);
			credsProvider.setCredentials(AuthScope.ANY, usernamePassword);
			httpclient.setCredentialsProvider(credsProvider);
		}
		/*
		 * 设置超时时间
		 */
		HttpParams params = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 1000);
		HttpConnectionParams.setSoTimeout(params, 1000);

		/*
		 * 设置重试策略
		 */
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				/*
				 * 不进行重试
				 */
				/*
				 * if (executionCount >= 2) { // 如果超过最大重试次数，那么就不要继续了 return
				 * false; } if (exception instanceof NoHttpResponseException) {
				 * // 如果服务器丢掉了连接，那么就重试 return true; } if (exception instanceof
				 * SSLHandshakeException) { // 不要重试SSL握手异常 return false; }
				 * HttpRequest request = (HttpRequest) context
				 * .getAttribute(ExecutionContext.HTTP_REQUEST); boolean
				 * idempotent = !(request instanceof
				 * HttpEntityEnclosingRequest); if (idempotent) { //
				 * 如果请求被认为是幂等的，那么就重试 return true; }
				 */
				return false;
			}
		};
		httpclient.setHttpRequestRetryHandler(myRetryHandler);

		return httpclient;
	}

	public static String get(String url, int connectionTimeout, int soTimeout) {
		return get(url, connectionTimeout, soTimeout, null, null);
	}

	public static String get(String url, int connectionTimeout, int soTimeout,
			String username, String password) {
		DefaultHttpClient httpclient = getHttpclient(connectionTimeout,
				soTimeout, username, password);
		String body = null;

		logger.info("create httpget:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	private static DefaultHttpClient getHttpclient(int connectionTimeout,
			int soTimeout, String username, String password) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		if (!StringUtils.isNullOrEmpty(username)) {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			UsernamePasswordCredentials usernamePassword = new UsernamePasswordCredentials(
					username, password);
			credsProvider.setCredentials(AuthScope.ANY, usernamePassword);
			httpclient.setCredentialsProvider(credsProvider);
		}
		/*
		 * 设置超时时间
		 */
		HttpParams params = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
		HttpConnectionParams.setSoTimeout(params, soTimeout);

		/*
		 * 设置重试策略
		 */
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				/*
				 * 不进行重试
				 */
				/*
				 * if (executionCount >= 2) { // 如果超过最大重试次数，那么就不要继续了 return
				 * false; } if (exception instanceof NoHttpResponseException) {
				 * // 如果服务器丢掉了连接，那么就重试 return true; } if (exception instanceof
				 * SSLHandshakeException) { // 不要重试SSL握手异常 return false; }
				 * HttpRequest request = (HttpRequest) context
				 * .getAttribute(ExecutionContext.HTTP_REQUEST); boolean
				 * idempotent = !(request instanceof
				 * HttpEntityEnclosingRequest); if (idempotent) { //
				 * 如果请求被认为是幂等的，那么就重试 return true; }
				 */
				return false;
			}
		};
		httpclient.setHttpRequestRetryHandler(myRetryHandler);

		return httpclient;
	}

	private static DefaultHttpClient getHttpclient(String username,
			String password) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		if (!StringUtils.isNullOrEmpty(username)) {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			UsernamePasswordCredentials usernamePassword = new UsernamePasswordCredentials(
					username, password);
			credsProvider.setCredentials(AuthScope.ANY, usernamePassword);
			httpclient.setCredentialsProvider(credsProvider);
		}

		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {

				return false;
			}
		};
		httpclient.setHttpRequestRetryHandler(myRetryHandler);

		return httpclient;
	}

}
