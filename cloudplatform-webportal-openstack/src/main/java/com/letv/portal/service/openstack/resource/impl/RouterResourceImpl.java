package com.letv.portal.service.openstack.resource.impl;

import java.util.ArrayList;
import java.util.List;

import org.jclouds.openstack.neutron.v2.domain.ExternalGatewayInfo;
import org.jclouds.openstack.neutron.v2.domain.Router;

import com.letv.portal.service.openstack.resource.PortResource;
import com.letv.portal.service.openstack.resource.RouterResource;

public class RouterResourceImpl extends AbstractResource implements
		RouterResource {

	private String region;
	private String regionDisplayName;
	private Router router;
	private List<PortResource> ports;

	public RouterResourceImpl(String region, String regionDisplayName,
			Router router) {
		this(region, regionDisplayName, router, new ArrayList<PortResource>());
	}

	public RouterResourceImpl(String region, String regionDisplayName,
			Router router, List<PortResource> ports) {
		this.region = region;
		this.regionDisplayName = regionDisplayName;
		this.router = router;
		this.ports = ports;
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

	@Override
	public List<PortResource> getPorts() {
		return ports;
	}

}
