package com.letv.portal.service.openstack.resource;

import java.util.List;

public interface RouterResource extends Resource{
	String getName();
	String getStatus();
	String getRegionDisplayName();
	boolean getPublicNetworkGatewayEnable();
//	List<PortResource> getPorts();
	List<SubnetResource> getSubnets();
	Long getCreated();
}
