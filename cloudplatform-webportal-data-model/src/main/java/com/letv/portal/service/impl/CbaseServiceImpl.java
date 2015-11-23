package com.letv.portal.service.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cbase.ICbaseClusterDao;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.service.ICbaseService;

/**
 * 
 * @author liyunhui
 *
 */

@Service("cbaseService")
public class CbaseServiceImpl extends BaseServiceImpl<CbaseClusterModel>
		implements ICbaseService {

	private final static Logger logger = LoggerFactory
			.getLogger(CbaseServiceImpl.class);

	@Resource
	private ICbaseClusterDao cbaseClusterDao;

	public CbaseServiceImpl() {
		super(CbaseClusterModel.class);
	}

	@Override
	public IBaseDao<CbaseClusterModel> getDao() {
		return this.cbaseClusterDao;
	}

	@Override
	public String getHello(String name) {
		// TODO Auto-generated method stub
		return "hello, "+name;
	}

	@Override
	public String getClusterDetail() {
		CloseableHttpClient client = HttpClients.createDefault();
		// 查看集群信息
		HttpGet request = new HttpGet("http://10.154.156.57:8091/pools");
		request.setHeader("Accept", "application/json");
		
		HttpHost targetHost = new HttpHost("10.154.156.57", 8091, "http");

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials("Administrator", "password"));

		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());

		// Add AuthCache to the execution context
		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		

		try {
			CloseableHttpResponse response = client.execute(targetHost,
					request, context);

			HttpEntity entity = response.getEntity();

			String responseBody = EntityUtils.toString(entity);
			return responseBody;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getNodeDetail() {
		CloseableHttpClient client = HttpClients.createDefault();
		// 查看节点信息
		HttpGet request = new HttpGet("http://10.154.156.57:8091/pools/nodes");
		request.setHeader("Accept", "application/json");

		HttpHost targetHost = new HttpHost("10.154.156.57", 8091, "http");

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials("Administrator", "password"));

		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());

		// Add AuthCache to the execution context
		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		
		
		try {
			CloseableHttpResponse response = client.execute(targetHost,
					request, context);

			HttpEntity entity = response.getEntity();

			String responseBody = EntityUtils.toString(entity);
			return responseBody;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getBucketDetail() {
		CloseableHttpClient client = HttpClients.createDefault();
		// 查看bucket信息
		HttpGet request = new HttpGet("http://10.154.156.57:8091/pools/defaults/buckets");
		request.setHeader("Accept", "application/json");
		
		HttpHost targetHost = new HttpHost("10.154.156.57", 8091, "http");

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials("Administrator", "password"));

		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());

		// Add AuthCache to the execution context
		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		
		

		try {
			CloseableHttpResponse response = client.execute(targetHost,
					request, context);

			HttpEntity entity = response.getEntity();

			String responseBody = EntityUtils.toString(entity);
			return responseBody;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}