package com.letv.portal.service.openstack.resource;

public interface RouterResource extends Resource{
	String getName();
	String getStatus();
	String getRegionDisplayName();
	boolean getPublicNetworkGatewayEnable();
}
