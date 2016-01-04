package com.letv.lcp.openstack.model.network.impl;

import java.util.Date;

import org.jclouds.openstack.neutron.v2.domain.FloatingIP;

import com.letv.lcp.openstack.model.base.AbstractResource;
import com.letv.lcp.openstack.model.base.Resource;
import com.letv.lcp.openstack.model.compute.VMResource;
import com.letv.lcp.openstack.model.network.FloatingIpResource;
import com.letv.lcp.openstack.model.network.NetworkResource;
import com.letv.lcp.openstack.model.network.RouterResource;
import com.letv.lcp.openstack.service.manage.impl.NetworkManagerImpl;

public class FloatingIpResourceImpl extends AbstractResource implements
		FloatingIpResource {

	private String region;
	private String regionDisplayName;
	private FloatingIP floatingIp;
	private Resource bindResource;
	private NetworkResource networkResource;

	public FloatingIpResourceImpl(String region, String regionDisplayName,
			FloatingIP floatingIp, NetworkResource networkResource) {
		this(region, regionDisplayName, floatingIp, networkResource, null);
	}

	public FloatingIpResourceImpl(String region, String regionDisplayName,
			FloatingIP floatingIp, NetworkResource networkResource,
			Resource bindResource) {
		this.region = region;
		this.regionDisplayName = regionDisplayName;
		this.floatingIp = floatingIp;
		this.bindResource = bindResource;
		this.networkResource = networkResource;
	}

    public FloatingIpResourceImpl(FloatingIP floatingIP) {
        this.floatingIp = floatingIP;
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

	@Override
	public NetworkResource getCarrier() {
		return networkResource;
	}

	@Override
	public String getName() {
		return floatingIp.getName();
	}

	@Override
	public Long getCreated() {
		Date date = floatingIp.getCreated();
		if (date != null) {
			return date.getTime();
		}
		return null;
	}

	@Override
	public Long getUpdated() {
		Date date = floatingIp.getUpdated();
		if (date != null) {
			return date.getTime();
		}
		return null;
	}
}
