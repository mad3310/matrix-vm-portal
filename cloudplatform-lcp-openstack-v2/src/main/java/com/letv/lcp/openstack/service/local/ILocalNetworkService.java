package com.letv.lcp.openstack.service.local;

import org.jclouds.openstack.neutron.v2.NeutronApi;

import com.letv.lcp.openstack.exception.OpenStackException;

public interface ILocalNetworkService {
	String getPublicNetworkId(NeutronApi neutronApi, String region)
			throws OpenStackException;
}
