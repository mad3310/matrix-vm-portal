package com.letv.portal.service.openstack.resource;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/6/12.
 */
public interface SubnetResource extends Resource {
	public String getName();

	String getCidr();

	Boolean getEnableDhcp();

	String getGatewayIp();

	Integer getIpVersion();

	List<String> getDnsNameservers();
}