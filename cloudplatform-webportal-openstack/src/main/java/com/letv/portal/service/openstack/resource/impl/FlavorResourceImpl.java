package com.letv.portal.service.openstack.resource.impl;

import org.jclouds.openstack.v2_0.domain.Resource;

import com.letv.portal.service.openstack.resource.FlavorResource;

public class FlavorResourceImpl extends AbstractResource implements
		FlavorResource {

	private String region;
	private Resource flavor;

	public FlavorResourceImpl(String region, Resource flavor) {
		this.region = region;
		this.flavor = flavor;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return flavor.getId();
	}

	@Override
	public String getName() {
		return flavor.getName();
	}

}
