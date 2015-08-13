package com.letv.portal.service.openstack.resource.manager;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.resource.NetworkResource;

import java.util.List;

public interface NetworkManager extends ResourceManager {
	List<NetworkResource> list(String region) throws RegionNotFoundException, OpenStackException;

	NetworkResource get(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException,OpenStackException;
}
