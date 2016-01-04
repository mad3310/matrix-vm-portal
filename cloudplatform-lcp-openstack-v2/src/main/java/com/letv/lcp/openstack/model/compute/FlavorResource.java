package com.letv.lcp.openstack.model.compute;

import com.letv.lcp.openstack.model.base.Resource;

public interface FlavorResource extends Resource{
	public String getName();
	Integer getVcpus();
	Integer getDisk();
	Integer getRam();
	Integer getEphemeral();
	Double getRxtxFactor();
	String getSwap();
}