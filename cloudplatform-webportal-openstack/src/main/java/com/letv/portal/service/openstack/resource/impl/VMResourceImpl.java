package com.letv.portal.service.openstack.resource.impl;

import java.util.Date;
import java.util.Map.Entry;

import org.jclouds.openstack.nova.v2_0.domain.Address;
import org.jclouds.openstack.nova.v2_0.domain.Server;

import com.google.common.collect.Multimap;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.IPAddresses;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.VMManager;

public class VMResourceImpl extends AbstractResource implements VMResource {

	private String region;
	public Server server;
	private ImageResource imageResource;
	private FlavorResource flavorResource;
	// private NetworkResource networkResource;
	private IPAddresses ipAddresses;

	public VMResourceImpl(String region, Server server,
			ImageResource imageResource, FlavorResource flavorResource,
			OpenStackUser user) throws RegionNotFoundException,
			ResourceNotFoundException {
		this.region = region;
		this.server = server;
		this.flavorResource = flavorResource;
		this.imageResource = imageResource;
		// this.networkResource = networkManager.get(region, resource.get);

		ipAddresses = new IPAddresses();
		Multimap<String, Address> addresses = server.getAddresses();
		for (Entry<String, Address> entry : addresses.entries()) {
			final String networkName = entry.getKey();
			final String ip = entry.getValue().getAddr();
			if (user.getPublicNetworkName().equals(networkName)) {
				ipAddresses.getPublicIP().add(ip);
			} else if (user.getPrivateNetworkName().equals(networkName)) {
				ipAddresses.getPrivateIP().add(ip);
			} else {
				if (user.getInternalUser()) {
					if (user.getSharedNetworkName().equals(networkName)) {
						ipAddresses.getSharedIP().add(ip);
					}
				}
			}
		}
	}

	public VMResourceImpl(String region, Server server, VMManager vmManager,
			ImageManager imageManager, OpenStackUser openStackUser)
			throws RegionNotFoundException, ResourceNotFoundException {
		this(region, server, imageManager
				.get(region, server.getImage().getId()), vmManager
				.getFlavorResource(region, server.getFlavor().getId()),
				openStackUser);
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return this.server.getId();
	}

	@Override
	public String getName() {
		return this.server.getName();
	}

	@Override
	public String getAccessIPv4() {
		return this.server.getAccessIPv4();
	}

	@Override
	public String getAccessIPv6() {
		return this.server.getAccessIPv6();
	}

	@Override
	public String getStatus() {
		return this.server.getStatus().toString();
	}

	// public void setImage(ImageResource imageResource) {
	// this.imageResource = imageResource;
	// }
	//
	// public void setFlavor(FlavorResource flavorResource) {
	// this.flavorResource = flavorResource;
	// }
	//
	// public void setNetwork(NetworkResource networkResource) {
	// this.networkResource = networkResource;
	// }

	@Override
	public ImageResource getImage() {
		return imageResource;
	}

	@Override
	public FlavorResource getFlavor() {
		return flavorResource;
	}

	// @Override
	// public NetworkResource getNetwork() {
	// return networkResource;
	// }

	@Override
	public IPAddresses getIpAddresses() {
		return ipAddresses;
	}

	@Override
	public String getAvailabilityZone() {
		return server.getAvailabilityZone().orNull();
	}

	@Override
	public String getConfigDrive() {
		return server.getConfigDrive();
	}

	@Override
	public Long getCreated() {
		return server.getCreated().getTime();
	}

	@Override
	public Long getUpdated() {
		Date date = server.getUpdated();
		if (date != null) {
			return date.getTime();
		} else {
			return null;
		}
	}

	@Override
	public String getDiskConfig() {
		return server.getDiskConfig().orNull();
	}

	@Override
	public String getHostId() {
		return server.getHostId();
	}

	@Override
	public String getKeyName() {
		return server.getKeyName();
	}
}
