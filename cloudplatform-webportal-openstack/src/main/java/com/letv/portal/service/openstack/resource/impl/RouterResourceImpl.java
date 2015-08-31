package com.letv.portal.service.openstack.resource.impl;

import org.jclouds.openstack.neutron.v2.domain.ExternalGatewayInfo;
import org.jclouds.openstack.neutron.v2.domain.Router;

import com.letv.portal.service.openstack.resource.RouterResource;

public class RouterResourceImpl extends AbstractResource implements
		RouterResource {

	private String region;
	private String regionDisplayName;
	private Router router;

	public RouterResourceImpl(String region, String regionDisplayName,
			Router router) {
		this.region = region;
		this.regionDisplayName = regionDisplayName;
		this.router = router;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return router.getId();
	}

	@Override
	public String getName() {
		return router.getName();
	}

	@Override
	public String getStatus() {
		return router.getStatus().toString();
	}

	@Override
	public String getRegionDisplayName() {
		return regionDisplayName;
	}

	@Override
	public boolean getPublicNetworkGatewayEnable() {
		ExternalGatewayInfo info = router.getExternalGatewayInfo();
		if (info != null) {
			return info.getNetworkId() != null
					&& !info.getNetworkId().isEmpty();
		}
		return false;
	}

}
