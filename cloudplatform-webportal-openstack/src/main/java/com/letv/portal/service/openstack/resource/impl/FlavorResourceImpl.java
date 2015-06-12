package com.letv.portal.service.openstack.resource.impl;

import com.letv.portal.service.openstack.resource.FlavorResource;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;

public class FlavorResourceImpl extends AbstractResource implements
		FlavorResource {

	private String region;
	private Flavor flavor;

	public FlavorResourceImpl(String region, Flavor flavor) {
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

	@Override
	public int getVcpus() {
		return this.flavor.getVcpus();
	}

	@Override
	public int getDisk() {
		return flavor.getDisk();
	}

	@Override
	public int getRam() {
		return flavor.getRam();
	}

}
