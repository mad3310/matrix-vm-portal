package com.letv.portal.service.openstack.resource;

import com.letv.portal.service.openstack.billing.BillingResource;

import java.util.List;

public interface RouterResource extends Resource,BillingResource{
	String getName();
	String getStatus();
	String getRegionDisplayName();
	boolean getPublicNetworkGatewayEnable();
//	List<PortResource> getPorts();
	List<SubnetResource> getSubnets();
	Long getCreated();
	NetworkResource getCarrier();
	String getGatewayIp();
}
