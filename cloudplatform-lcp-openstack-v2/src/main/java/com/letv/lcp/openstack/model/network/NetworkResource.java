package com.letv.lcp.openstack.model.network;

import java.util.List;

import com.letv.lcp.openstack.model.base.Resource;

public interface NetworkResource extends Resource{
	public String getName();
	Boolean getAdminStateUp();
	Boolean getExternal();
	String getMemberSegments();
	String getMulticastIp();
	String getNetworkFlavor();
	String getNetworkType();
	String getPhysicalNetworkName();
	Boolean getPortSecurity();
	String getProfileId();
	String getSegmentAdd();
	Integer getSegmentationId();
	String getSegmentDel();
	Boolean getShared();
	String getStatus();
	List<NetworkSegmentResource> getNetworkSegments();
	List<SubnetResource> getSubnets();
	String getRegionDisplayName();
	Long getCreated();
}
