package com.letv.portal.service.openstack.resource.impl;

import org.jclouds.openstack.v2_0.domain.Resource;

import com.letv.portal.service.openstack.resource.VMResource;

public class VMResourceImpl extends AbstractResource implements VMResource {

	private String region;
	private Resource resource;

	public VMResourceImpl(String region, Resource resource) {
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
