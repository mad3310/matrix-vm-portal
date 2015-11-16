package com.letv.portal.service.openstack.resource.impl;

import com.letv.portal.service.openstack.resource.FlavorResource;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;

public class FlavorResourceImpl extends AbstractResource implements
		FlavorResource {

	private String region;
	@JsonIgnore
	public Flavor flavor;

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
	public Integer getVcpus() {
		return this.flavor.getVcpus();
	}

	@Override
	public Integer getDisk() {
		return flavor.getDisk();
	}

	@Override
	public Integer getRam() {
		return flavor.getRam();
	}

	@Override
	public Integer getEphemeral() {
		return flavor.getEphemeral().orNull();
	}

	@Override
	public Double getRxtxFactor() {
		return flavor.getRxtxFactor().orNull();
	}

	@Override
	public String getSwap() {
		return flavor.getSwap().orNull();
	}

}
