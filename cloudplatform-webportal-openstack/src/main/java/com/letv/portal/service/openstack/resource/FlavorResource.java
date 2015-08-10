package com.letv.portal.service.openstack.resource;

public interface FlavorResource extends Resource{
	public String getName();
	Integer getVcpus();
	Integer getDisk();
	Integer getRam();
	Integer getEphemeral();
	Double getRxtxFactor();
	String getSwap();
}