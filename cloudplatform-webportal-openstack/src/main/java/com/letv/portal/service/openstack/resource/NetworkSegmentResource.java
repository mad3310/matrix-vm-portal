package com.letv.portal.service.openstack.resource;

public interface NetworkSegmentResource {
	String getNetworkType();

	String getPhysicalNetwork();

	Integer getSegmentationId();
}
