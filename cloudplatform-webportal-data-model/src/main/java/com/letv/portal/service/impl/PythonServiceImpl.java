package com.letv.portal.service.impl;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.letv.portal.service.IPythonService;

@Service("pythonService")
public class PythonServiceImpl implements IPythonService{
	private final static Logger logger = LoggerFactory.getLogger(PythonServiceImpl.class);
	
	@Resource
	private RestTemplate restTemplate;

	@Override
	public String createContainer() {
		
		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//
//		restTemplate.postForObject(url, request, responseType);
		// TODO Auto-generated method stub
		
		/*HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
		DefaultHttpClient httpClient = (DefaultHttpClient)requestFactory.getHttpClient();
		httpClient.getCredentialsProvider().setCredentials(new AuthScope(null, (Integer) null, AuthScope.ANY_REALM),new UsernamePasswordCredentials("name", "pass"));
			
		restTemplate.exchange("http://localhost:8080/spring-security-rest-template/api/foos/1", HttpMethod.POST, null, String.class);*/
		
		
		 /*String userName = "XXXX";
         String password = "XXXX";
         
         CredentialsProvider credsProvider = new BasicCredentialsProvider();
         UsernamePasswordCredentials usernamePassword = new UsernamePasswordCredentials(
                 userName, password);
         credsProvider.setCredentials(AuthScope.ANY, usernamePassword);
         httpclient.setCredentialsProvider(credsProvider);
         
         HttpPost httpPost = new HttpPost(url);
         HttpResponse response = httpclient.execute(httpPost);*/


			    		
			    		
		return null;
	}

	@Override
	public String checkContainerCreateStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String initZookeeper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String initUserAndPwd4Manager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postMclusterInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String initMcluster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postContainerInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String syncContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String startMcluster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkContainerStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createDb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createDbManagerUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createDbRoDbUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createDbWrUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String initContainer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
