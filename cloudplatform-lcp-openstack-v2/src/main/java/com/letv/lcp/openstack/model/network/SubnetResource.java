package com.letv.lcp.openstack.model.network;

import java.util.List;

import com.letv.lcp.openstack.model.base.Resource;

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
	
	String getRegionDisplayName();
	
	String getNetworkId();
	
	List<IpAllocationPool> getIpAllocationPools();

	RouterResource getRouter();

	NetworkResource getNetwork();
	
	Long getCreated();
}