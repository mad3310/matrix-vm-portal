package com.letv.lcp.openstack.model.network;

public interface NetworkSegmentResource {
	String getNetworkType();

	String getPhysicalNetwork();

	Integer getSegmentationId();
}
