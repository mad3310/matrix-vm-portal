package com.letv.portal.service.openstack.resource;

public interface FlavorResource extends Resource{
	public String getName();
	int getVcpus();
	int getDisk();
	int getRam();
}
