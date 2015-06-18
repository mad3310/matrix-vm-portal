package com.letv.portal.service.openstack.resource.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jclouds.openstack.nova.v2_0.domain.Address;
import org.jclouds.openstack.nova.v2_0.domain.Server;

import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.VMManager;

public class VMResourceImpl extends AbstractResource implements VMResource {

	private String region;
	private Server server;
	private ImageResource imageResource;
	private FlavorResource flavorResource;
	// private NetworkResource networkResource;
	private List<String> ipAddresses;

	public VMResourceImpl(String region, Server server,
			ImageResource imageResource, FlavorResource flavorResource)
			throws RegionNotFoundException, ResourceNotFoundException {
		this.region = region;
		this.server = server;
		this.flavorResource = flavorResource;
		this.imageResource = imageResource;
		// this.networkResource = networkManager.get(region, resource.get);
		Collection<Address> addresses = server.getAddresses().values();
		ipAddresses = new ArrayList<String>(addresses.size());
		for (Address address : addresses) {
			ipAddresses.add(address.getAddr());
		}
	}

	public VMResourceImpl(String region, Server server, VMManager vmManager,
			ImageManager imageManager) throws RegionNotFoundException,
			ResourceNotFoundException {
		this(region, server, imageManager
				.get(region, server.getImage().getId()), vmManager
				.getFlavorResource(region, server.getFlavor().getId()));
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
	public List<String> getIpAddresses() {
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
		Date date=server.getUpdated();
		if(date!=null){
			return date.getTime();
		}else{
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
