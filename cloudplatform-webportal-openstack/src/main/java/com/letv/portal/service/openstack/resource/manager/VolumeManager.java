package com.letv.portal.service.openstack.resource.manager;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.resource.VolumeResource;

public interface VolumeManager extends ResourceManager {

	VolumeResource get(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException;

	Page listAll(String name, Integer currentPage, Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException;

	Page listByRegionGroup(String regionGroup, String name,
			Integer currentPage, Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException;

	VolumeResource create(String region, int sizeGB, String name,
			String description) throws RegionNotFoundException,
			OpenStackException;

	void delete(String region, VolumeResource volumeResource)
			throws OpenStackException;

}
