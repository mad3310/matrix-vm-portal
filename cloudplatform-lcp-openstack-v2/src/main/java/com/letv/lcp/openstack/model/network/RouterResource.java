package com.letv.lcp.openstack.model.network;

import java.util.List;

import com.letv.lcp.openstack.model.base.Resource;
import com.letv.lcp.openstack.model.billing.BillingResource;

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
