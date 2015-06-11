package com.letv.portal.proxy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.letv.common.util.HttpClient;
import com.letv.portal.proxy.IGfsProxy;
 
@Component
public class GfsProxyImpl implements IGfsProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(GfsProxyImpl.class);
	
	private final static String URL_HEAD = "http://";
	private final static String URL_PORT = ":8000";	
	
	@Override
	public String getGfsPeers(String ip) {
		StringBuffer url = new StringBuffer();
		url.append(URL_HEAD).append(ip).append(URL_PORT).append("/api/v1/peers");
		String result = HttpClient.get(url.toString());
		return result;
	}
	
	@Override
	public String getGfsVolumes(String ip) {
		StringBuffer url = new StringBuffer();
		url.append(URL_HEAD).append(ip).append(URL_PORT).append("/api/v1/volume/info");
		String result = HttpClient.get(url.toString());
		return result;
	}
	
	@Override
	public String getVolProcessByName(String ip,String name) {
		StringBuffer url = new StringBuffer();
		url.append(URL_HEAD).append(ip).append(URL_PORT).append("/api/v1/volume/status/").append(name);
		String result = HttpClient.get(url.toString());
		return result;
	}
	
	@Override
	public String getVolCapacityByName(String ip,String name) {
		StringBuffer url = new StringBuffer();
		url.append(URL_HEAD).append(ip).append(URL_PORT).append("/api/v1/volume/df/").append(name);
		String result = HttpClient.get(url.toString());
		return result;
	}
}
