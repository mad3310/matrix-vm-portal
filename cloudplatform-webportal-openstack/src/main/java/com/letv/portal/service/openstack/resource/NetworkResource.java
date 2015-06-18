package com.letv.portal.service.openstack.resource;

import java.util.List;

public interface NetworkResource extends Resource{
	public String getName();
	boolean getAdminStateUp();
	boolean getExternal();
	String getMemberSegments();
	String getMulticastIp();
	String getNetworkFlavor();
	String getNetworkType();
	String getPhysicalNetworkName();
	boolean getPortSecurity();
	String getProfileId();
	String getSegmentAdd();
	int getSegmentationId();
	String getSegmentDel();
	boolean getShared();
	String getStatus();
	List<NetworkSegmentResource> getNetworkSegments();
	List<SubnetResource> getSubnets();
}
