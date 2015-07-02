package com.letv.portal.service.openstack.impl;

import com.letv.portal.service.cloudvm.ICloudvmRegionService;

public class OpenStackServiceGroup {
	private ICloudvmRegionService cloudvmRegionService;

	public ICloudvmRegionService getCloudvmRegionService() {
		return cloudvmRegionService;
	}

	public void setCloudvmRegionService(
			ICloudvmRegionService cloudvmRegionService) {
		this.cloudvmRegionService = cloudvmRegionService;
	}

}
