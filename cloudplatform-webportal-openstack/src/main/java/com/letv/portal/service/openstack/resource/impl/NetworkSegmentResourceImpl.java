package com.letv.portal.service.openstack.resource.impl;

import org.jclouds.openstack.neutron.v2.domain.NetworkSegment;

import com.letv.portal.service.openstack.resource.NetworkSegmentResource;

public class NetworkSegmentResourceImpl extends AbstractResource implements
		NetworkSegmentResource {

	@SuppressWarnings("unused")
	private String region;
	private NetworkSegment networkSegment;

	public NetworkSegmentResourceImpl(String region,
			NetworkSegment networkSegment) {
		this.region = region;
		this.networkSegment = networkSegment;
	}

	@Override
	public String getNetworkType() {
		return networkSegment.getNetworkType().toString();
	}

	@Override
	public String getPhysicalNetwork() {
		return networkSegment.getPhysicalNetwork();
	}

	@Override
	public int getSegmentationId() {
		return networkSegment.getSegmentationId();
	}

}
