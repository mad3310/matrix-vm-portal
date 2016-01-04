package com.letv.lcp.openstack.model.network.impl;

import org.jclouds.openstack.neutron.v2.domain.NetworkSegment;
import org.jclouds.openstack.neutron.v2.domain.NetworkType;

import com.letv.lcp.openstack.model.base.AbstractResource;
import com.letv.lcp.openstack.model.network.NetworkSegmentResource;


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
		NetworkType type = networkSegment.getNetworkType();
		if (type != null) {
			return type.toString();
		} else {
			return null;
		}
	}

	@Override
	public String getPhysicalNetwork() {
		return networkSegment.getPhysicalNetwork();
	}

	@Override
	public Integer getSegmentationId() {
		return networkSegment.getSegmentationId();
	}

}
