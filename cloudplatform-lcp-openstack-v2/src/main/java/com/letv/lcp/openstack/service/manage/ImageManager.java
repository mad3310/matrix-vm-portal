package com.letv.lcp.openstack.service.manage;

import java.util.List;
import java.util.Map;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.RegionNotFoundException;
import com.letv.lcp.openstack.exception.ResourceNotFoundException;
import com.letv.lcp.openstack.model.image.ImageResource;

public interface ImageManager extends ResourceManager {
	List<ImageResource> list(String region) throws RegionNotFoundException, OpenStackException;

	ImageResource get(String region, String id) throws RegionNotFoundException,
			ResourceNotFoundException, OpenStackException;

	Map<String, Map<String, ImageResource>> group(String region)
			throws RegionNotFoundException, OpenStackException;

	void delete(String region,String imageId) throws OpenStackException;
}
