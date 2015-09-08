package com.letv.portal.service.openstack.resource.impl;

import org.jclouds.openstack.neutron.v2.domain.FloatingIP;

import com.letv.portal.service.openstack.resource.FloatingIpResource;
import com.letv.portal.service.openstack.resource.Resource;
import com.letv.portal.service.openstack.resource.RouterResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;

public class FloatingIpResourceImpl extends AbstractResource implements
		FloatingIpResource {

	private String region;
	private String regionDisplayName;
	private FloatingIP floatingIp;
	private Resource bindResource;

	public FloatingIpResourceImpl(String region, String regionDisplayName,
			FloatingIP floatingIp) {
		this(region, regionDisplayName, floatingIp, null);
	}

	public FloatingIpResourceImpl(String region, String regionDisplayName,
			FloatingIP floatingIp, Resource bindResource) {
		this.region = region;
		this.regionDisplayName = regionDisplayName;
		this.floatingIp = floatingIp;
		this.bindResource = bindResource;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return floatingIp.getId();
	}

	@Override
	public String getRegionDisplayName() {
		return regionDisplayName;
	}

	@Override
	public Integer getBandWidth() {
		if (floatingIp.getFipQos() != null) {
			return NetworkManagerImpl.getBandWidth(floatingIp.getFipQos());
		}
		return null;
	}

	@Override
	public String getIpAddress() {
		return floatingIp.getFloatingIpAddress();
	}

	@Override
	public String getBindResourceType() {
		if (bindResource instanceof RouterResource) {
			return "ROUTER";
		} else if (bindResource instanceof VMResource) {
			return "VM";
		}
		return null;
	}

	@Override
	public Resource getBindResource() {
		return bindResource;
	}

	@Override
	public String getStatus() {
		if (bindResource == null) {
			return "AVAILABLE";
		} else {
			return "BINDED";
		}
	}

}
