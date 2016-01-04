package com.letv.lcp.openstack.model.network;

import com.letv.lcp.openstack.model.base.Resource;
import com.letv.lcp.openstack.model.billing.BillingResource;


public interface FloatingIpResource extends Resource,BillingResource{
	String getRegionDisplayName();
	Integer getBandWidth();
	String getIpAddress();
	String getBindResourceType();
	Resource getBindResource(); 
	String getStatus();
	NetworkResource getCarrier();
	String getName();
	Long getCreated();
	Long getUpdated();
}
