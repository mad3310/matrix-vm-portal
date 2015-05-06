package com.letv.portal.task.swift.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.util.HttpsClient;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.swift.SwiftServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IUserService;

@Service("taskSwiftCreateContainerService")
public class TaskSwiftCreateContainerServiceImpl extends BaseTask4SwiftServiceImpl implements IBaseTaskService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskSwiftCreateContainerServiceImpl.class);
	@Autowired
	private IUserService userService;
	
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;
		SwiftServer server = super.getServer(params);
		HostModel host = super.getHost(server.getHclusterId());
		UserModel user = this.userService.selectById(server.getCreateUser());
		
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("X-Auth-Token", (String)params.get("X-Auth-Token"));
		headParams.put("X-Container-Meta-Quota-Bytes", String.valueOf(server.getStoreSize()));
		headParams.put("X-Container-Read", user.getUserName() + ":" + user.getUserName());
		headParams.put("X-Container-Write", user.getUserName() + ":" + user.getUserName());
		
		HttpResponse response = HttpsClient.httpPutByHeader(getSwiftGetTokenUrl(host.getHostIp(),user.getUserName(),server.getName()),headParams,1000,1000);
		if(response == null || response.getStatusLine() == null || response.getStatusLine().getStatusCode()>300) {
			tr.setSuccess(false);
			return tr;
		}
		tr.setSuccess(true);
		tr.setParams(params);
		return tr;
	}
	
	private String getSwiftGetTokenUrl(String ip,String username,String containerName) {
		StringBuffer sb = new StringBuffer();
		sb.append("https://").append(ip).append(":443").append("/v1/AUTH_").append(username).append("/").append(containerName);
		return sb.toString();
		
	}
	
	@Override
	public void callBack(TaskResult tr) {
		super.rollBack(tr);
	}
	
}
