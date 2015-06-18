package com.letv.portal.service.openstack.resource.impl;

import java.util.ArrayList;
import java.util.List;

import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.NetworkSegment;

import com.google.common.collect.ImmutableList;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.NetworkSegmentResource;
import com.letv.portal.service.openstack.resource.SubnetResource;

public class NetworkResourceImpl extends AbstractResource implements
		NetworkResource {

	private String region;
	private Network network;
	private List<SubnetResource> subnetResources;
	private List<NetworkSegmentResource> networkSegmentResources;

	public NetworkResourceImpl(String region, Network network,
			List<SubnetResource> subnetResources) {
		this.region = region;
		this.network = network;
		this.subnetResources = subnetResources;
		ImmutableList<NetworkSegment> networkSegments = network.getSegments()
				.asList();
		this.networkSegmentResources = new ArrayList<NetworkSegmentResource>(
				networkSegments.size());
		for (NetworkSegment networkSegment : networkSegments) {
			this.networkSegmentResources.add(new NetworkSegmentResourceImpl(
					region, networkSegment));
		}
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return this.network.getId();
	}

	@Override
	public String getName() {
		return this.network.getName();
	}

	@Override
	public List<SubnetResource> getSubnets() {
		return subnetResources;
	}

	@Override
	public boolean getAdminStateUp() {
		return network.getAdminStateUp();
	}

	@Override
	public boolean getExternal() {
		return network.getExternal();
	}

	@Override
	public String getMemberSegments() {
		return network.getMemberSegments();
	}

	@Override
	public String getMulticastIp() {
		return network.getMulticastIp();
	}

	@Override
	public String getNetworkFlavor() {
		return network.getNetworkFlavor();
	}

	@Override
	public String getNetworkType() {
		return network.getNetworkType().toString();
	}

	@Override
	public String getPhysicalNetworkName() {
		return network.getPhysicalNetworkName();
	}

	@Override
	public boolean getPortSecurity() {
		return network.getPortSecurity();
	}

	@Override
	public String getProfileId() {
		return network.getProfileId();
	}

	@Override
	public String getSegmentAdd() {
		return network.getSegmentAdd();
	}

	@Override
	public int getSegmentationId() {
		return network.getSegmentationId();
	}

	@Override
	public String getSegmentDel() {
		return network.getSegmentDel();
	}

	@Override
	public boolean getShared() {
		return network.getShared();
	}

	@Override
	public String getStatus() {
		return network.getStatus().toString();
	}

	@Override
	public List<NetworkSegmentResource> getNetworkSegments() {
		return networkSegmentResources;
	}
}
