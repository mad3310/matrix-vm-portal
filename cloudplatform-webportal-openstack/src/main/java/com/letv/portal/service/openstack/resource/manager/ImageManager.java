package com.letv.portal.service.openstack.resource.manager;

import java.util.List;

import com.letv.portal.service.openstack.resource.ImageResource;

public interface ImageManager extends ResourceManager{
	List<ImageResource> list();
}
