package com.letv.portal.service.openstack.resource.impl;

import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.v2_0.domain.Resource;

import com.letv.portal.service.openstack.resource.VMResource;

public class VMResourceImpl extends AbstractResource implements VMResource {

	private String region;
	private Server resource;

	public VMResourceImpl(String region, Server resource) {
		this.region = region;
		this.resource = resource;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return this.resource.getId();
	}

	@Override
	public String getName() {
		return this.resource.getName();
	}

}
