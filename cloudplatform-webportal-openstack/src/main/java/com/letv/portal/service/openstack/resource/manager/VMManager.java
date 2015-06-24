package com.letv.portal.service.openstack.resource.manager;

import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.exception.VMDeleteException;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.VMResource;

import java.util.List;

public interface VMManager extends ResourceManager {
	List<VMResource> list(String region) throws RegionNotFoundException, ResourceNotFoundException;

	VMResource get(String region, String id) throws RegionNotFoundException,
			ResourceNotFoundException;

	VMResource create(String region, VMCreateConf conf)
			throws RegionNotFoundException, ResourceNotFoundException;

	void delete(String region, VMResource vm) throws RegionNotFoundException,
			VMDeleteException;

	void start(String region, VMResource vm) throws RegionNotFoundException;

	void stop(String region, VMResource vm) throws RegionNotFoundException;

	List<FlavorResource> listFlavorResources(String region)
			throws RegionNotFoundException;

	FlavorResource getFlavorResource(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException;
}
