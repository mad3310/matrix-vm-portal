package com.letv.portal.service.openstack.resource.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jclouds.openstack.neutron.v2.domain.ExternalGatewayInfo;
import org.jclouds.openstack.neutron.v2.domain.Router;

import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.RouterResource;
import com.letv.portal.service.openstack.resource.SubnetResource;

public class RouterResourceImpl extends AbstractResource implements
		RouterResource {

	private String region;
	private String regionDisplayName;
	private Router router;
//	private List<PortResource> ports;
	private List<SubnetResource> subnets;

	public RouterResourceImpl(String region, String regionDisplayName,
			Router router) {
		this(region, regionDisplayName, router, new ArrayList<SubnetResource>());
	}

	public RouterResourceImpl(String region, String regionDisplayName,
			Router router, List<SubnetResource> subnets) {
		this.region = region;
		this.regionDisplayName = regionDisplayName;
		this.router = router;
		this.subnets = subnets;
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
	public List<SubnetResource> getSubnets() {
		return subnets;
	}

	@Override
	public Long getCreated() {
		Date date = router.getCreated();
		if (date != null) {
			return date.getTime();
		}
		return null;
	}
}
