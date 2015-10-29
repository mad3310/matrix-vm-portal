package com.letv.portal.service.openstack.resource.manager;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.resource.ImageResource;

public interface ImageManager extends ResourceManager {
	List<ImageResource> list(String region) throws RegionNotFoundException, OpenStackException;

	ImageResource get(String region, String id) throws RegionNotFoundException,
			ResourceNotFoundException, OpenStackException;

	Map<String, Map<String, ImageResource>> group(String region)
			throws RegionNotFoundException, OpenStackException;

	void delete(String region,String imageId) throws OpenStackException;
}
