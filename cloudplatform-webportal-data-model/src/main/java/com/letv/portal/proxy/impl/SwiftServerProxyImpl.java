package com.letv.portal.proxy.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.exception.CommonException;
import com.letv.common.exception.ValidateException;
import com.letv.common.util.HttpsClient;
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
		
		HttpResponse response = HttpsClient.httpDeleteByHeader(getSwiftGetTokenUrl(host.getHostIp(),user.getUserName(),server.getName()),headParams,1000,1000);
		if(response == null || response.getStatusLine() == null || response.getStatusLine().getStatusCode()>300) {
			throw new CommonException(response == null?"api connect failed":response.getStatusLine().toString());
		}
	}
	private String getSuperToken(SwiftServer server) {
		HostModel host = this.getHost(server.getHclusterId());
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("x-auth-key", "swauthkey");
		headParams.put("x-auth-user", ".super_admin:.super_admin");
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
	private String getSwiftGetTokenUrl(String ip,String username,String containerName) {
		StringBuffer sb = new StringBuffer();
		sb.append("https://").append(ip).append(":443").append("/v1/AUTH_").append(username).append("/").append(containerName);
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

}
