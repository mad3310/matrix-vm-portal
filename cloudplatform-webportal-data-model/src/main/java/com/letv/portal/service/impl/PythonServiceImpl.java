package com.letv.portal.service.impl;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.letv.portal.service.IPythonService;



@Service("pythonService")
public class PythonServiceImpl implements IPythonService{
	private final static Logger logger = LoggerFactory.getLogger(PythonServiceImpl.class);

	@Override
	public String createContainer() {
		
		getRestTemplate().exchange("http://localhost:8080/spring-security-rest-template/api/foos/1", HttpMethod.POST, null, String.class);
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
	
	@SuppressWarnings("deprecation")
	private RestTemplate getRestTemplate() {
		String username = "";
		String password = "";
		RestTemplate restTemplate = new RestTemplate();
		HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
		DefaultHttpClient httpClient = (DefaultHttpClient)requestFactory.getHttpClient();
		httpClient.getCredentialsProvider().setCredentials(new AuthScope(null, (Integer) null, AuthScope.ANY_REALM),new UsernamePasswordCredentials(username, password));
		return restTemplate;
	}
	
	
	@SuppressWarnings("deprecation")
	private DefaultHttpClient getHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		 String userName = "";
         String password = "";
         
         CredentialsProvider credsProvider = new BasicCredentialsProvider();
         UsernamePasswordCredentials usernamePassword = new UsernamePasswordCredentials(
                 userName, password);
         credsProvider.setCredentials(AuthScope.ANY, usernamePassword);
         httpClient.setCredentialsProvider(credsProvider);
         
        /* HttpPost httpPost = new HttpPost();
         HttpResponse response = httpClient.execute(httpPost);*/
         
         return httpClient;
	}
	
	
}
