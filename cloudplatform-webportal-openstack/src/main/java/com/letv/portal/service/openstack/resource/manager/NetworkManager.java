package com.letv.portal.service.openstack.resource.manager;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.resource.NetworkResource;

import java.util.List;

public interface NetworkManager extends ResourceManager {
	List<NetworkResource> list(String region) throws RegionNotFoundException,
			OpenStackException;

	NetworkResource get(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException,
			OpenStackException;

	NetworkResource getPrivate(String region, String id)
			throws OpenStackException;

	Page listPrivate(String regionGroup, String name, Integer currentPage,
			Integer recordsPerPage) throws OpenStackException;

	void createPrivate(String region, String name) throws OpenStackException;

	void editPrivate(String region, String networkId, String name)
			throws OpenStackException;
}
