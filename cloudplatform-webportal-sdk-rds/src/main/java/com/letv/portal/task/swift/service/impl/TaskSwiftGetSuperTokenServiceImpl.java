package com.letv.portal.task.swift.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.util.ConfigUtil;
import com.letv.common.util.HttpsClient;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.swift.SwiftServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;

@Service("taskSwiftGetSuperTokenService")
public class TaskSwiftGetSuperTokenServiceImpl extends BaseTask4SwiftServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskSwiftGetSuperTokenServiceImpl.class);
	@Value("${matrix.swift.super.user}")
	private String SWIFT_SUPER_USER;
	@Value("${matrix.swift.super.password}")
	private String SWIFT_SUPER_USER_PWD;
	@Value("${matrix.swift.auth.source}")
	private String SWIFT_SUPER_AUTH_SOURCE;
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;
		SwiftServer server = super.getServer(params);
		HostModel host = super.getHost(server.getHclusterId());
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("x-auth-user", SWIFT_SUPER_USER);
		headParams.put("x-auth-key", SWIFT_SUPER_USER_PWD);
		headParams.put("x-auth-source", SWIFT_SUPER_AUTH_SOURCE);
		HttpResponse response = HttpsClient.httpGetByHeader(getSwiftGetTokenUrl(host.getHostIp()),headParams,2000,5000);
		if(response == null || response.getFirstHeader("X-Auth-Token") == null) {
			tr.setResult(response == null?"api connect failed":response.getStatusLine().toString());
			tr.setSuccess(false);
			return tr;
		}
		String authToken = response.getFirstHeader("X-Auth-Token").getValue();
		params.put("X-Auth-Token", authToken);
		tr.setParams(params);
		tr.setResult(response.getStatusLine().toString());
		return tr;
	}
	
	private String getSwiftGetTokenUrl(String ip) {
		StringBuffer sb = new StringBuffer();
		sb.append("https://").append(ip).append(":443").append("/auth/v1.0");
		return sb.toString();
		
	}
	
}
