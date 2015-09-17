package com.letv.portal.service.openstack.resource;

import java.util.List;

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
