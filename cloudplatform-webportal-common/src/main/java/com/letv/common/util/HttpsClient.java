package com.letv.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/*
 * author:haungxuebin
 * 云南新接口
 * 
 */
public class HttpsClient {

	/**
	 * 访问https的网站
	 * 
	 * @param httpclient
	 */
	private static void enableSSL(DefaultHttpClient httpclient) {
		// 调用ssl
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme https = new Scheme("https", sf, 443);
			httpclient.getConnectionManager().getSchemeRegistry()
					.register(https);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重写验证方法，取消检测ssl
	 */
	private static TrustManager truseAllManager = new X509TrustManager() {

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

	};

	/**
	 * HTTP Client Object,used HttpClient Class before(version 3.x),but now the
	 * HttpClient is an interface
	 */
	public static String sendXMLDataByGet(String url,int connectionTimeout,int soTimeout) {
		// 创建HttpClient实例
		DefaultHttpClient client = getHttpclient(connectionTimeout, soTimeout);
		enableSSL(client);
		// 创建Get方法实例
		HttpGet httpsgets = new HttpGet(url);

		String strRep = "";
		try {
			HttpResponse response = client.execute(httpsgets);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				strRep = EntityUtils.toString(response.getEntity());
				// Do not need the rest
				httpsgets.abort();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return strRep;
	}
	public static HttpResponse httpGetByHeader(String url,Map<String,String> headParams,int connectionTimeout,int soTimeout) {
		// 创建HttpClient实例
		DefaultHttpClient client = getHttpclient(connectionTimeout, soTimeout);
		enableSSL(client);
		// 创建Get方法实例
		HttpGet httpsgets = new HttpGet(url);
	    for (Iterator<Map.Entry<String, String>> it = headParams.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            httpsgets.setHeader(entry.getKey(),entry.getValue());
         }
		HttpResponse response = null;
		try {
			response = client.execute(httpsgets);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			client.getConnectionManager().shutdown();
		}
		return response;
	}
	public static HttpResponse httpPutByHeader(String url,Map<String,String> headParams,AbstractHttpEntity entity,int connectionTimeout,int soTimeout) {
		// 创建HttpClient实例
		DefaultHttpClient client = getHttpclient(connectionTimeout, soTimeout);
		enableSSL(client);
		// 创建Get方法实例
		HttpPut httpPut = new HttpPut(url);
		for (Iterator<Map.Entry<String, String>> it = headParams.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			httpPut.setHeader(entry.getKey(),entry.getValue());
		}
		if(entity !=null)
			httpPut.setEntity(entity);
		HttpResponse response = null;
		try {
			response = client.execute(httpPut);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			client.getConnectionManager().shutdown();
		}
		return response;
	}
	public static HttpResponse httpDeleteByHeader(String url,Map<String,String> headParams,int connectionTimeout,int soTimeout) {
		// 创建HttpClient实例
		DefaultHttpClient client = getHttpclient(connectionTimeout, soTimeout);
		enableSSL(client);
		// 创建Get方法实例
		HttpDelete httpsgets = new HttpDelete(url);
		for (Iterator<Map.Entry<String, String>> it = headParams.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			httpsgets.setHeader(entry.getKey(),entry.getValue());
		}
		HttpResponse response = null;
		try {
			response = client.execute(httpsgets);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			client.getConnectionManager().shutdown();
		}
		return response;
	}
	public static HttpResponse httpPostByHeader(String url,Map<String,String> headParams,int connectionTimeout,int soTimeout) {
		DefaultHttpClient client = getHttpclient(connectionTimeout, soTimeout);
		enableSSL(client);
		client.getParams().setParameter("http.protocol.content-charset",
				HTTP.UTF_8);
		client.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
		client.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
		client.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET,
				HTTP.UTF_8);

		HttpPost post = new HttpPost(url);
		// Set content type of request header
		post.setHeader("Content-Type", "text/xml;charset=UTF-8");
		 for (Iterator<Map.Entry<String, String>> it = headParams.entrySet().iterator(); it.hasNext();) {
	            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
	            post.setHeader(entry.getKey(),entry.getValue());
	         }
		// Execute request and get the response
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			client.getConnectionManager().shutdown();
		}
		return response;
	}

	/**
	 * Send a XML-Formed string to HTTP Server by post method
	 * 
	 * @param url
	 *            the request URL string
	 * @param xmlData
	 *            XML-Formed string ,will not check whether this string is
	 *            XML-Formed or not
	 * @return the HTTP response status code ,like 200 represents OK,404 not
	 *         found
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String sendXMLDataByPost(String url, String xmlData,int connectionTimeout,int soTimeout)
			throws ClientProtocolException, IOException {
		DefaultHttpClient client = getHttpclient(connectionTimeout, soTimeout);
		enableSSL(client);
		client.getParams().setParameter("http.protocol.content-charset",
				HTTP.UTF_8);
		client.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
		client.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
		client.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET,
				HTTP.UTF_8);

		// System.out.println(HTTP.UTF_8);
		// Send data by post method in HTTP protocol,use HttpPost instead of
		// PostMethod which was occurred in former version
		// System.out.println(url);
		HttpPost post = new HttpPost(url);
		post.getParams().setParameter("http.protocol.content-charset",
				HTTP.UTF_8);
		post.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
		post.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
		post.getParams()
				.setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET, HTTP.UTF_8);

		// Construct a string entity
		StringEntity entity = new StringEntity(getUTF8XMLString(xmlData),
				"UTF-8");
		entity.setContentType("text/xml;charset=UTF-8");
		entity.setContentEncoding("UTF-8");
		// Set XML entity
		post.setEntity(entity);
		// Set content type of request header
		post.setHeader("Content-Type", "text/xml;charset=UTF-8");
		// Execute request and get the response
		HttpResponse response = client.execute(post);
		HttpEntity entityRep = response.getEntity();
		String strrep = "";
		if (entityRep != null) {
			strrep = EntityUtils.toString(response.getEntity());
			// Do not need the rest
			post.abort();
		}
		client.getConnectionManager().shutdown();
		// Response Header - StatusLine - status code
		// statusCode = response.getStatusLine().getStatusCode();
		return strrep;
	}
	
	private static DefaultHttpClient getHttpclient(int connectionTimeout,int soTimeout){
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		/*
		 * 设置超时时间
		 */
		HttpParams params = httpclient.getParams();  
		HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);  
		HttpConnectionParams.setSoTimeout(params, soTimeout);
        
	    return httpclient;
	}


	/**
	 * Get XML String of utf-8
	 * 
	 * @return XML-Formed string
	 */
	public static String getUTF8XMLString(String xml) {
		// A StringBuffer Object
		StringBuffer sb = new StringBuffer();
		sb.append(xml);
		String xmString = "";
		try {
			xmString = new String(sb.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return to String Formed
		return xmString.toString();
	}
	
	public static void main(String[] args) {
		/*String result = HttpsClient.sendXMLDataByGet("https://login.lecloud.com/getfield?client_id=client_id-liuhao1-1420627685913&client_secret=45860d612fc62e85423389aafaf100e7",1000,1000);
		System.out.println(result);*/
		System.out.println("in");
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("x-auth-key", "swauthkey");
		headParams.put("x-auth-user", ".super_admin:.super_admin");
//		HttpResponse response = HttpsClient.httpGetByHeader("http://10.150.110.216:8081/auth/v1.0",headParams,1000,1000);
		HttpResponse response = HttpsClient.httpGetByHeader("https://10.150.110.216:443/auth/v1.0",headParams,1000,1000);
		System.out.println("end");
		System.out.println(response);
		Header[] headers = response.getAllHeaders();
		for (Header header : headers) {
			System.out.println(header.getName());
			System.out.println(header.getValue());
		}
		try {
			System.out.println("---------------------------"+EntityUtils.toString(response.getEntity()));
			System.out.println("---------------------------"+response.getStatusLine());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}