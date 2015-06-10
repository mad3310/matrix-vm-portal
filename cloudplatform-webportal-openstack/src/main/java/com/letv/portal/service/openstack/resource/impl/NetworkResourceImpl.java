package com.letv.portal.service.openstack.resource.impl;

import org.jclouds.openstack.neutron.v2.domain.Network;

import com.letv.portal.service.openstack.resource.NetworkResource;

public class NetworkResourceImpl extends AbstractResource implements
		NetworkResource {

	private String region;
	private Network network;

	public NetworkResourceImpl(String region, Network network) {
		this.region = region;
		this.network = network;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return this.network.getId();
	}

	@Override
	public String getName() {
		return this.network.getName();
	}

}
