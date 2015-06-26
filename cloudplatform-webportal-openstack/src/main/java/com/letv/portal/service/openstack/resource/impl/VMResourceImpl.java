package com.letv.portal.service.openstack.resource.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jclouds.openstack.nova.v2_0.domain.Address;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerExtendedStatus;

import com.google.common.base.Optional;
import com.google.common.collect.Multimap;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.IPAddresses;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;

public class VMResourceImpl extends AbstractResource implements VMResource {

	private String region;
	@JsonIgnore
	public Server server;
	private ImageResource imageResource;
	private FlavorResource flavorResource;
	// private NetworkResource networkResource;
	private IPAddresses ipAddresses;

	public VMResourceImpl(String region, Server server,
			ImageResource imageResource, FlavorResource flavorResource,
			List<FloatingIP> floatingIPs, OpenStackUser user)
			throws RegionNotFoundException, ResourceNotFoundException {
		this.region = region;
		this.server = server;
		this.flavorResource = flavorResource;
		this.imageResource = imageResource;
		// this.networkResource = networkManager.get(region, resource.get);

		ipAddresses = new IPAddresses();
		Set<String> publicIPs = new HashSet<String>();
		Set<String> privateIPs = new HashSet<String>();
		for (FloatingIP floatingIP : floatingIPs) {
			if (server.getId().equals(floatingIP.getInstanceId())
					&& user.getPublicNetworkName().equals(floatingIP.getPool())) {
				publicIPs.add(floatingIP.getIp());
				privateIPs.add(floatingIP.getFixedIp());
			}
		}
		Multimap<String, Address> addresses = server.getAddresses();
		for (Entry<String, Address> entry : addresses.entries()) {
			final String networkName = entry.getKey();
			final String ip = entry.getValue().getAddr();
			if (user.getPrivateNetworkName().equals(networkName)) {
				boolean isIPInSet = false;
				if (publicIPs.contains(ip)) {
					isIPInSet = true;
					ipAddresses.getPublicIP().add(ip);
				}
				if (privateIPs.contains(ip)) {
					isIPInSet = true;
					ipAddresses.getPrivateIP().add(ip);
				}
				if (isIPInSet == false) {
					ipAddresses.getPrivateIP().add(ip);
				}
			} else {
				if (user.getInternalUser()) {
					if (user.getSharedNetworkName().equals(networkName)) {
						ipAddresses.getSharedIP().add(ip);
					}
				}
			}
		}
	}

	public VMResourceImpl(String region, Server server,
			VMManagerImpl vmManager, ImageManager imageManager,
			OpenStackUser openStackUser) throws RegionNotFoundException,
			ResourceNotFoundException, APINotAvailableException {
		this(region, server, imageManager
				.get(region, server.getImage().getId()), vmManager
				.getFlavorResource(region, server.getFlavor().getId()),
				vmManager.listFloatingIPs(region), openStackUser);
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

	@Override
	public String getTaskState() {
		Optional<ServerExtendedStatus> extendedStatus = server
				.getExtendedStatus();
		if (!extendedStatus.isPresent()) {
			return null;
		} else {
			return extendedStatus.get().getTaskState();
		}
	}

	@Override
	public int getPowerState() {
		Optional<ServerExtendedStatus> extendedStatus = server
				.getExtendedStatus();
		if (!extendedStatus.isPresent()) {
			return -1;
		} else {
			return extendedStatus.get().getPowerState();
		}
	}

	@Override
	public String getVmState() {
		Optional<ServerExtendedStatus> extendedStatus = server
				.getExtendedStatus();
		if (!extendedStatus.isPresent()) {
			return null;
		} else {
			return extendedStatus.get().getVmState();
		}
	}
}
