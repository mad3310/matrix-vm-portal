package com.letv.portal.service.openstack.resource;

public interface NetworkSegmentResource {
	String getNetworkType();

	String getPhysicalNetwork();

	int getSegmentationId();
}
