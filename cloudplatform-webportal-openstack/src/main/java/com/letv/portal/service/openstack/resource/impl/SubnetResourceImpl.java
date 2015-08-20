package com.letv.portal.service.openstack.resource.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.letv.portal.service.openstack.resource.SubnetResource;

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

	public SubnetResourceImpl(String region,String regionDisplayName, Subnet subnet) {
		this.region = region;
		this.regionDisplayName = regionDisplayName;
		this.subnet = subnet;
		ImmutableSet<String> dnsNameservers = subnet.getDnsNameservers();
		if (dnsNameservers != null) {
			this.dnsNameservers = dnsNameservers.asList();
		} else {
			this.dnsNameservers = new ArrayList<String>();
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
}
