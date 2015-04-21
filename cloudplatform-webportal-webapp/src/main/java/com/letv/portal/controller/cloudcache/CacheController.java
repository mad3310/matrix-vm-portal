package com.letv.portal.controller.cloudcache;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpUtil;
import com.letv.common.util.StringUtil;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.proxy.ICbaseProxy;
import com.letv.portal.service.cbase.ICbaseBucketService;

@Controller
@RequestMapping("/cache")
public class CacheController {

	@Autowired(required = false)
	private SessionServiceImpl sessionService;
	@Autowired
	private ICbaseBucketService cbaseBucketService;
	@Autowired
	private ICbaseProxy cbaseProxy;

	private final static Logger logger = LoggerFactory
			.getLogger(CacheController.class);

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResultObject list(Page page,
			HttpServletRequest request, ResultObject result) {
		Map<String, Object> params = HttpUtil.requestParam2Map(request);
		params.put("createUser", sessionService.getSession().getUserId());
		
		String cacheName = (String) params.get("cacheName");
		if (!StringUtils.isEmpty(cacheName))
			params.put("cacheName", StringUtil.transSqlCharacter(cacheName));
		
		result.setData(this.cbaseBucketService.findPagebyParams(params, page));
		return result;
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResultObject save(CbaseBucketModel cbaseBucketModel,
			ResultObject result) {
//		if (cbaseBucketModel == null
//				|| StringUtils.isEmpty(cbaseBucketModel.getBucketName())) {
//			throw new ValidateException("参数不合法");
//		} else {
//
//		}
		System.out.println("in POST");
		cbaseBucketModel.setCreateUser(this.sessionService.getSession()
				.getUserId());
		
		System.out.println(cbaseBucketModel.getBucketName());
		System.out.println(cbaseBucketModel.getHclusterId());
		System.out.println(cbaseBucketModel.getHcluster());
		System.out.println(cbaseBucketModel.getCbaseClusterId());
		System.out.println(cbaseBucketModel.getCbaseCluster());
		System.out.println(cbaseBucketModel.getStatus());
		System.out.println(cbaseBucketModel.getDescn());
		System.out.println(cbaseBucketModel.getBucketType());
		
		System.out.println(cbaseBucketModel.getRamQuotaMB());
		System.out.println(cbaseBucketModel.getAuthType());
		System.out.println(cbaseBucketModel.getContainers());
		
		System.out.println("insert");
		this.cbaseBucketService.insert(cbaseBucketModel);
		
		String name = cbaseBucketModel.getBucketName();
		String ramQuotaMB = cbaseBucketModel.getRamQuotaMB();
		String authType = "sasl";
		Integer bucketType = cbaseBucketModel.getBucketType();
		
		
		
		//this.cbaseProxy.saveAndBuild(cbaseBucketModel);
		
		CloseableHttpClient client = HttpClients.createDefault();

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

		HttpPost postrequest = new HttpPost(
				"http://10.154.156.57:8091/pools/default/buckets");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("name", name));
		nvps.add(new BasicNameValuePair("ramQuotaMB", ramQuotaMB));
		nvps.add(new BasicNameValuePair("authType", authType));
		
		// 非持久化 <=300MB
		if(1==bucketType)
			nvps.add(new BasicNameValuePair("bucketType", "memcached"));
		// 持久化 MB >=100MB
		else if(0==bucketType)
			nvps.add(new BasicNameValuePair("replicaNumber", "2"));

		try {
			postrequest.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			CloseableHttpResponse response = client.execute(targetHost,
					postrequest, context);

			HttpEntity entity = response.getEntity();
			String responseBody = EntityUtils.toString(entity);

			// System.out.println(responseBody);

			result.setData(response.getStatusLine().toString());
			result.addMsg(responseBody);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}

}
