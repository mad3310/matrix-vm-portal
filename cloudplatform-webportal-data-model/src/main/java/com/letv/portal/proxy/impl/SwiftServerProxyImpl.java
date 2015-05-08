package com.letv.portal.proxy.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.exception.CommonException;
import com.letv.common.exception.ValidateException;
import com.letv.common.util.HttpsClient;
import com.letv.portal.enumeration.OssServerVisibility;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.swift.SwiftServer;
import com.letv.portal.model.task.service.ITaskEngine;
import com.letv.portal.proxy.ISwiftServerProxy;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.swift.ISwiftServerService;

@Component
public class SwiftServerProxyImpl extends BaseProxyImpl<SwiftServer> implements ISwiftServerProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(SwiftServerProxyImpl.class);
	
	@Autowired
	private ISwiftServerService swiftServerService;
	
	@Autowired
	private ITaskEngine taskEngine;
	@Autowired
	private IHostService hostService;
	@Autowired
	private IUserService userService;
	
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Value("${matrix.swift.super.user}")
	private String SWIFT_SUPER_USER;
	@Value("${matrix.swift.super.password}")
	private String SWIFT_SUPER_USER_PWD;
	@Override
	public IBaseService<SwiftServer> getService() {
		return swiftServerService;
	}
	
	@Override
	public void saveAndBuild(SwiftServer swift) {
		this.swiftServerService.insert(swift);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("swiftId", swift.getId());
		this.build(params);
	}

	private void build(Map<String,Object> params) {
    	this.taskEngine.run("OSS_BUY",params);
	}
	
	@Override
	public void deleteAndBuild(Long swiftId) {
		SwiftServer server = this.selectById(swiftId);
		if(server == null)
			throw new ValidateException("oss 服务不存在");
		this.deleteBuild(server, this.getSuperToken(server));
		super.delete(server);
	}

	private void deleteBuild(SwiftServer server,String token) {
		HostModel host = this.getHost(server.getHclusterId());
		UserModel user = this.userService.selectById(server.getCreateUser());
		
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("X-Auth-Token", token);
		
		HttpResponse response = HttpsClient.httpDeleteByHeader(getSwiftMainUrl(host.getHostIp(),user.getUserName(),server.getName()),headParams,1000,1000);
		if(response == null || response.getStatusLine() == null || response.getStatusLine().getStatusCode()>300) {
			throw new CommonException(response == null?"api connect failed":response.getStatusLine().toString());
		}
	}
	private String getSuperToken(SwiftServer server) {
		HostModel host = this.getHost(server.getHclusterId());
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("x-auth-user", SWIFT_SUPER_USER);
		headParams.put("x-auth-key", SWIFT_SUPER_USER_PWD);
		HttpResponse response = HttpsClient.httpGetByHeader(getSwiftGetTokenUrl(host.getHostIp()),headParams,1000,1000);
		if(response == null || response.getFirstHeader("X-Auth-Token") == null) {
			throw new CommonException("oss delete exception:get super token failed");
		}
		String authToken = response.getFirstHeader("X-Auth-Token").getValue();
		return authToken;
	}
	private String getSwiftGetTokenUrl(String ip) {
		StringBuffer sb = new StringBuffer();
		sb.append("https://").append(ip).append(":443").append("/auth/v1.0");
		return sb.toString();
	}
	private String getSwiftMainUrl(String ip,String username,String containerName) {
		StringBuffer sb = new StringBuffer();
		sb.append("https://").append(ip).append(":443").append("/v1/AUTH_").append(username).append("/").append(containerName);
		return sb.toString();
		
	}
	private String getSwiftMainFileUrl(String ip,String username,String containerName,String directory) {
		StringBuffer sb = new StringBuffer();
		if("root".equals(directory)) {
			directory = "";
		} else {
			directory +="/";
		}
		sb.append("https://").append(ip).append(":443").append("/v1/AUTH_").append(username).append("/").append(containerName)
		.append("?format=json&prefix=").append(directory).append("&delimiter=/");
		return sb.toString();
		
	}
	private  HostModel getHost(Long hclusterId) {
		if(hclusterId == null)
			throw new ValidateException("hclusterId is null :" + hclusterId);
		HostModel host = this.hostService.getHostByHclusterId(hclusterId);
		if(host == null)
			throw new ValidateException("host is null by hclusterIdId:" + hclusterId);
		
		return host;
	}

	@Override
	public Object getFiles(Long id, String directory) {
		SwiftServer server = this.selectById(id);
		if(server == null)
			throw new ValidateException("oss 服务不存在");
		return this.getFiles(server,  this.getSuperToken(server),directory);
	}
	
	private Object getFiles(SwiftServer server,String token,String directory) {
		HostModel host = this.getHost(server.getHclusterId());
		UserModel user = this.userService.selectById(server.getCreateUser());
		
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("X-Auth-Token", token);
		HttpResponse response = HttpsClient.httpGetByHeader(getSwiftMainFileUrl(host.getHostIp(),user.getUserName(),server.getName(),directory),headParams,1000,1000);
		if(response == null || response.getStatusLine() == null || response.getStatusLine().getStatusCode()>300) {
			throw new CommonException(response == null?"api connect failed":response.getStatusLine().toString());
		}
		String result = "";
		try {
			result = EntityUtils.toString(response.getEntity());
		}  catch (Exception e) {
			throw new CommonException("get oss files failed :" + e.getMessage());
		} 
		return JSONArray.parse(result);
	}

	@Override
	public void changeService(Long id, String level,Long storeSize) {
			
		SwiftServer server = this.selectById(id);
		HostModel host = this.getHost(server.getHclusterId());
		UserModel user = this.userService.selectById(server.getCreateUser());
		
		server.setStoreSize(storeSize);
		server.setVisibilityLevel(OssServerVisibility.PRIVATE);
		
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("X-Auth-Token", getSuperToken(server));
		headParams.put("X-Container-Meta-Quota-Bytes", String.valueOf(server.getStoreSize()*1024*1024*1024));
		headParams.put("X-Container-Write", user.getUserName() + ":" + user.getUserName());
		headParams.put("X-Container-Read", user.getUserName() + ":" + user.getUserName());
		if("PUBLIC".equals(level)) {
			server.setVisibilityLevel(OssServerVisibility.PUBLIC);
			headParams.put("X-Container-Read", ".r:*,.rlistings");
		}
		if(storeSize != null)
			headParams.put("X-Container-Meta-Quota-Bytes", String.valueOf(storeSize*1024*1024*1024));
		HttpResponse response = HttpsClient.httpPutByHeader(getSwiftMainUrl(host.getHostIp(),user.getUserName(),server.getName()),headParams,1000,1000);
		if(response == null || response.getStatusLine() == null || response.getStatusLine().getStatusCode()>300) {
			throw new CommonException(response == null?"api connect failed":response.getStatusLine().toString());
		}
		
		this.updateBySelective(server);
	}
}
