package com.letv.portal.controller.cloudcbase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpClient;
import com.letv.portal.controller.clouddb.HclusterController;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.model.cbase.CbaseContainerModel;
import com.letv.portal.service.ICbaseService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.cbase.ICbaseBucketService;
import com.letv.portal.service.cbase.ICbaseClusterService;
import com.letv.portal.service.cbase.ICbaseContainerService;
import com.letv.portal.service.IHclusterService;


/**
 * 
 * @author liyunhui
 *
 */

@Controller
public class CbaseController {
	@Autowired(required = false)
	private SessionServiceImpl sessionService;
	@Resource
	private ICbaseBucketService cbaseBucketService;
	@Resource
	private ICbaseService cbaseService;
	
	@Resource
	private ICbaseClusterService cbaseClusterService;
	@Resource
	private ICbaseContainerService cbaseContainerService;

	private final static Logger logger = LoggerFactory
			.getLogger(CbaseController.class);

	//
	// @Value("${oauth.auth.http}")
	// private String OAUTH_AUTH_HTTP;
	// @Value("${webportal.local.http}")
	// private String WEBPORTAL_LOCAL_HTTP;

	@RequestMapping(value = "/cbase/hello", method = RequestMethod.GET)
	public @ResponseBody ResultObject returnhello(
			@RequestParam(value = "name", required = false, defaultValue = "lyh") String name,
			ResultObject result) {

		System.out.println("name=" + name);
		result.setData(cbaseService.getHello(name));
		return result;
	}

	@RequestMapping(value = "/cbase/clusterDetail", method = RequestMethod.GET)
	public @ResponseBody ResultObject cbaseClusterDetail(ResultObject result) {
		result.setData(cbaseService.getClusterDetail());
		return result;

	}

	@RequestMapping(value = "/cbase/nodeDetail", method = RequestMethod.GET)
	public @ResponseBody ResultObject cbaseNodeDetail(ResultObject result) {
		result.setData(cbaseService.getNodeDetail());
		return result;

	}

	@RequestMapping(value = "/cbase/bucketDetail", method = RequestMethod.GET)
	public @ResponseBody ResultObject cbaseBucketDetail(ResultObject result) {
		result.setData(cbaseService.getBucketDetail());
		return result;
	}

	@RequestMapping(value = "/cbase/createBucket", method = RequestMethod.GET)
	public @ResponseBody ResultObject createBucket(
			@RequestParam(value = "name", required = false, defaultValue = "default") String name,
			@RequestParam(value = "ramQuotaMB", required = false, defaultValue = "100") String ramQuotaMB,
			@RequestParam(value = "bucketType", required = false, defaultValue = "memcached") String bucketType,

			ResultObject result) {
		
		// 添加数据库处理
		
		Long userId = sessionService.getSession().getUserId();
		System.out.println("userId = "+userId);
		CbaseBucketModel cbaseBucketModel = new CbaseBucketModel();
		CbaseBucketModel searchresult;
		
		cbaseBucketModel.setCreateUser(userId);
		cbaseBucketModel.setStatus(0);
		cbaseBucketModel.setDeleted(false);
		cbaseBucketModel.setBucketName("default");
		cbaseBucketModel.setCbaseClusterId(null);
		cbaseBucketModel.setHclusterId(null);
		cbaseBucketModel.setBucketType(1);
		cbaseBucketModel.setRamQuotaMB("100");
		cbaseBucketModel.setAuthType("sasl");
		
		
//		System.out.println("insert");
//		this.cbaseBucketService.insert(cbaseBucketModel);
		
		// 测试service
		CbaseContainerModel cbaseContainerModel = new CbaseContainerModel();
		cbaseContainerModel.setContainerName("cccc");
		cbaseContainerModel.setDiskSize(1000);
		cbaseContainerModel.setStatus(1);
		this.cbaseContainerService.insert(cbaseContainerModel);
		
		cbaseContainerModel.setContainerName("modify");
		cbaseContainerModel.setDiskSize(200);
		cbaseContainerModel.setStatus(2);
		this.cbaseContainerService.update(cbaseContainerModel);
		
		CbaseContainerModel resultCluster = this.cbaseContainerService.selectById(1L);
		System.out.println(resultCluster.getContainerName());
		
		cbaseContainerModel.setId(1L);
		this.cbaseContainerService.delete(cbaseContainerModel);
		
		
		
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
		nvps.add(new BasicNameValuePair("authType", "sasl"));
		nvps.add(new BasicNameValuePair("bucketType", bucketType));
		// nvps.add(new BasicNameValuePair("proxyPort", "11217"));

		try {
			postrequest.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			CloseableHttpResponse response = client.execute(targetHost,
					postrequest, context);

			// System.out.println("Status code: "+response.getStatusLine().getStatusCode());
			// System.out.println("Response line: "+response.getStatusLine().toString());

			// Header[] headers = response.getAllHeaders();
			// for(Header header : headers){
			// System.out.println(header.getName()+" : "+header.getValue());
			//
			// }

			// System.out.println("Response body:");
			// HttpEntity entity = response.getEntity();

			// String responseBody = EntityUtils.toString(entity);
			// System.out.println(responseBody);

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
	
	@RequestMapping(value = "/cbase/test", method = RequestMethod.GET)
	public @ResponseBody ResultObject test(ResultObject result) {

		String resultstr = HttpClient.get("http://10.154.156.57:8091/pools",
				"Administrator", "password");

		System.out.println(resultstr);
		result.setData(resultstr);
		return result;
	}

	@RequestMapping(value = "/cbase/deleteBucket", method = RequestMethod.GET)
	public @ResponseBody ResultObject deleteBucket(
			@RequestParam(value = "name", required = false, defaultValue = "default") String name,
			ResultObject result) {

		if (name == "default") {
			System.out.println("error, no bucket name");
			result.setData("error");
			return result;
		}

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

		HttpDelete deleterequest = new HttpDelete(
				"http://10.154.156.57:8091:8091/pools/default/buckets/" + name);

		try {
			CloseableHttpResponse response = client.execute(targetHost,
					deleterequest, context);

			// System.out.println("Status code: "
			// + response.getStatusLine().getStatusCode());
			// System.out.println("Response line: "
			// + response.getStatusLine().toString());
			//
			// Header[] headers = response.getAllHeaders();
			// for (Header header : headers) {
			// System.out
			// .println(header.getName() + " : " + header.getValue());
			//
			// }

			// System.out.println("Response body:");
			// HttpEntity entity = response.getEntity();
			//
			// String responseBody = EntityUtils.toString(entity);
			// System.out.println(responseBody);

			result.setData(response.getStatusLine().toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	
}
