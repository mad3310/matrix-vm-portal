package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.Closeable;

import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.manager.ResourceManager;

public abstract class AbstractResourceManager implements ResourceManager,
		Closeable {
	protected OpenStackConf openStackConf;
	protected OpenStackUser openStackUser;

	public AbstractResourceManager(OpenStackConf openStackConf,
			OpenStackUser openStackUser) {
		this.openStackConf = openStackConf;
		this.openStackUser = openStackUser;
	}

	public void checkRegion(String region) throws RegionNotFoundException {
		if (!getRegions().contains(region)) {
			throw new RegionNotFoundException(region);
		}
	}
}
