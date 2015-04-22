package com.letv.portal.controller.cloudcbase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpClient;
import com.letv.common.util.HttpUtil;
import com.letv.portal.controller.clouddb.HclusterController;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.enumeration.MclusterType;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.MclusterModel;
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

	@RequestMapping(value = "/cbase", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("createUser", sessionService.getSession().getUserId());	
		obj.setData(this.cbaseBucketService.findPagebyParams(params, page));
		
		//lyh test
		System.out.println("obj="+obj.getResult());
		System.out.println("obj="+obj.getCallback());
		System.out.println("obj="+obj.getMsgs());
		
		for (CbaseBucketModel cbasebucket : ((List<CbaseBucketModel>)((Page)obj.getData()).getData()) ){
			System.out.println(cbasebucket.getBucketName());
		}
		
		
		return obj;
	}
	
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
		cbaseBucketModel.setBucketName(name);
		cbaseBucketModel.setCbaseClusterId(null);
		cbaseBucketModel.setHclusterId(1L);
		cbaseBucketModel.setBucketType(1);
		cbaseBucketModel.setRamQuotaMB("100");
		cbaseBucketModel.setAuthType("sasl");
		
		
		System.out.println("insert");
		this.cbaseBucketService.insert(cbaseBucketModel);
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		// id 是mysql生成的 
		params.put("cbaseBucketId", cbaseBucketModel.getId());
		System.out.println(cbaseBucketModel.getId());
		
		StringBuffer cbaseclusterName = new StringBuffer();
		cbaseclusterName.append(userId).append("_").append(cbaseBucketModel.getBucketName());
		
		System.out.println("cbaseclusterName="+cbaseclusterName);
		
		Boolean isExist= this.cbaseClusterService.isExistByName(cbaseclusterName.toString());
		
		System.out.println(isExist);
		
		int i = 0;
		while(!isExist) {
			isExist= this.cbaseClusterService.isExistByName(cbaseclusterName.toString() + i);
			i++;
		}
		if(i>0)
			cbaseclusterName.append(i);
		
		System.out.println(cbaseclusterName);
		
		params.put("cbaseclusterName", cbaseclusterName.toString());
		params.put("status", DbStatus.BUILDDING.getValue());
		params.put("hclusterId", cbaseBucketModel.getHclusterId());
		
		Integer status = (Integer) params.get("status");
		Long cbaseclusterId = (Long) params.get("cbaseclusterId");
		Long cbaseBucketId = (Long) params.get("cbaseBucketId");
		String cbaseclusterName2 = (String) params.get("cbaseclusterName");		
		String auditInfo = (String) params.get("auditInfo");
		Long hclusterId = (Long) params.get("hclusterId");
		
		CbaseBucketModel cbModel = new CbaseBucketModel();
		cbModel.setId(cbaseBucketId);
		cbModel.setStatus(status);
		
		CbaseClusterModel cbasecluster = new CbaseClusterModel();
		
		//创建新的cbasecluster集群
		if(cbaseclusterId == null) { 
			
			System.out.println("cbaseclusterId=null");
			
			if(cbaseclusterName2 == null) {
				throw new ValidateException("参数不合法");
			}
			cbasecluster.setCbaseClusterName(cbaseclusterName2);
			
			CbaseBucketModel db = this.cbaseBucketService.selectById(cbaseBucketId);
			cbasecluster.setHclusterId(hclusterId == null?db.getHclusterId():hclusterId);
			cbasecluster.setCreateUser(db.getCreateUser());
			
			String mclusterName = cbasecluster.getCbaseClusterName();
			cbasecluster.setAdminUser("root");
			cbasecluster.setAdminPassword(mclusterName);
			cbasecluster.setDeleted(true);
			cbasecluster.setType(MclusterType.AUTO.getValue());
			cbasecluster.setStatus(MclusterStatus.BUILDDING.getValue());
			this.cbaseClusterService.insert(cbasecluster);
			
			db.setCbaseClusterId(cbasecluster.getId());
			this.cbaseBucketService.updateBySelective(db);
			
			
		}
		
		
//		CloseableHttpClient client = HttpClients.createDefault();
//
//		HttpHost targetHost = new HttpHost("10.154.156.57", 8091, "http");
//
//		CredentialsProvider credsProvider = new BasicCredentialsProvider();
//		credsProvider.setCredentials(AuthScope.ANY,
//				new UsernamePasswordCredentials("Administrator", "password"));
//
//		AuthCache authCache = new BasicAuthCache();
//		authCache.put(targetHost, new BasicScheme());
//
//		// Add AuthCache to the execution context
//		final HttpClientContext context = HttpClientContext.create();
//		context.setCredentialsProvider(credsProvider);
//		context.setAuthCache(authCache);
//
//		HttpPost postrequest = new HttpPost(
//				"http://10.154.156.57:8091/pools/default/buckets");
//
//		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		nvps.add(new BasicNameValuePair("name", name));
//		nvps.add(new BasicNameValuePair("ramQuotaMB", ramQuotaMB));
//		nvps.add(new BasicNameValuePair("authType", "sasl"));
//		nvps.add(new BasicNameValuePair("bucketType", bucketType));
//		// nvps.add(new BasicNameValuePair("proxyPort", "11217"));
//
//		try {
//			postrequest.setEntity(new UrlEncodedFormEntity(nvps));
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		try {
//			CloseableHttpResponse response = client.execute(targetHost,
//					postrequest, context);
//
//			// System.out.println("Status code: "+response.getStatusLine().getStatusCode());
//			// System.out.println("Response line: "+response.getStatusLine().toString());
//
//			// Header[] headers = response.getAllHeaders();
//			// for(Header header : headers){
//			// System.out.println(header.getName()+" : "+header.getValue());
//			//
//			// }
//
//			// System.out.println("Response body:");
//			// HttpEntity entity = response.getEntity();
//
//			// String responseBody = EntityUtils.toString(entity);
//			// System.out.println(responseBody);
//
//			HttpEntity entity = response.getEntity();
//			String responseBody = EntityUtils.toString(entity);
//
//			// System.out.println(responseBody);
//
//			result.setData(response.getStatusLine().toString());
//			result.addMsg(responseBody);
//
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}

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
