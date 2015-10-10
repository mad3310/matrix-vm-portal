package com.letv.portal.service.openstack.resource.manager;

import java.util.List;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.resource.VolumeResource;
import com.letv.portal.service.openstack.resource.VolumeTypeResource;

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

	Page list(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

	void create(String region, int sizeGB, String volumeTypeId, String name, String description,
			Integer count) throws RegionNotFoundException, OpenStackException;

	void delete(String region, VolumeResource volumeResource)
			throws OpenStackException;

	List<VolumeTypeResource> listVolumeType(String region) throws OpenStackException;

}
