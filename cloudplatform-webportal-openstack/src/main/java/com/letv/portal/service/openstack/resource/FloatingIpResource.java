package com.letv.portal.service.openstack.resource;

import com.letv.portal.service.openstack.billing.BillingResource;

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
