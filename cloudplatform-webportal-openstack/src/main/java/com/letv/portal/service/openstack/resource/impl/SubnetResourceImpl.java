package com.letv.portal.service.openstack.resource.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.letv.portal.service.openstack.resource.IpAllocationPool;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.RouterResource;
import com.letv.portal.service.openstack.resource.SubnetResource;

import org.jclouds.openstack.neutron.v2.domain.AllocationPool;
import org.jclouds.openstack.neutron.v2.domain.Subnet;

/**
 * Created by zhouxianguang on 2015/6/12.
 */
public class SubnetResourceImpl extends AbstractResource implements
		SubnetResource {

	private String regionDisplayName;
	private String region;
	private Subnet subnet;
	private List<String> dnsNameservers;
	private List<IpAllocationPool> ipAllocationPools;
	private RouterResource router;
	private NetworkResource network;

	public SubnetResourceImpl(String region,String regionDisplayName,Subnet subnet){
		this(region,regionDisplayName,subnet,null,null);
	}

	public SubnetResourceImpl(String region,String regionDisplayName,Subnet subnet,NetworkResource network){
		this(region,regionDisplayName,subnet,network,null);
	}

	public SubnetResourceImpl(String region,String regionDisplayName,Subnet subnet,RouterResource router){
		this(region,regionDisplayName,subnet,null,router);
	}

	public SubnetResourceImpl(String region,String regionDisplayName,Subnet subnet,NetworkResource network, RouterResource router){
		this.region = region;
		this.regionDisplayName = regionDisplayName;
		this.subnet = subnet;
		this.network = network;
		this.router = router;

		ImmutableSet<String> dnsNameservers = subnet.getDnsNameservers();
		if (dnsNameservers != null) {
			this.dnsNameservers = dnsNameservers.asList();
		} else {
			this.dnsNameservers = new ArrayList<String>();
		}

		this.ipAllocationPools = new LinkedList<IpAllocationPool>();
		ImmutableSet<AllocationPool> allocationPools = subnet
				.getAllocationPools();
		if (allocationPools != null) {
			for (AllocationPool allocationPool : allocationPools) {
				this.ipAllocationPools.add(new IpAllocationPool(allocationPool
						.getStart(), allocationPool.getEnd()));
			}
		}
	}

	@Override
	public String getName() {
		return subnet.getName();
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return subnet.getId();
	}

	@Override
	public String getCidr() {
		return subnet.getCidr();
	}

	@Override
	public Boolean getEnableDhcp() {
		return subnet.getEnableDhcp();
	}

	@Override
	public String getGatewayIp() {
		return subnet.getGatewayIp();
	}

	@Override
	public Integer getIpVersion() {
		return subnet.getIpVersion();
	}

	@Override
	public List<String> getDnsNameservers() {
		return dnsNameservers;
	}

	@Override
	public String getRegionDisplayName() {
		return regionDisplayName;
	}

	@Override
	public String getNetworkId() {
		return subnet.getNetworkId();
	}

	@Override
	public List<IpAllocationPool> getIpAllocationPools() {
		return ipAllocationPools;
	}

	@Override
	public RouterResource getRouter(){
		return router;
	}

	@Override
	public NetworkResource getNetwork() {
		return network;
	}
}
