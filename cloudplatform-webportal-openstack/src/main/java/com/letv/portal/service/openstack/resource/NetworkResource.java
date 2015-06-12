package com.letv.portal.service.openstack.resource;

import java.util.List;

public interface NetworkResource extends Resource{
	public String getName();

	List<SubnetResource> getSubnetResources();
}
