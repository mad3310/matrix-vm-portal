package com.letv.portal.service.openstack.resource.impl;

import java.util.List;

import com.letv.portal.service.openstack.resource.SubnetResource;

import org.jclouds.openstack.neutron.v2.domain.Subnet;

/**
 * Created by zhouxianguang on 2015/6/12.
 */
public class SubnetResourceImpl extends AbstractResource implements
		SubnetResource {

	private String region;
	private Subnet subnet;
	private List<String> dnsNameservers;

	public SubnetResourceImpl(String region, Subnet subnet) {
		this.region = region;
		this.subnet = subnet;
		this.dnsNameservers = subnet.getDnsNameservers().asList();
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
}
