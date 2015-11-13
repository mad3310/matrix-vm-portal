package com.letv.portal.service.openstack.local.service;

import org.jclouds.openstack.neutron.v2.NeutronApi;

import com.letv.portal.service.openstack.exception.OpenStackException;

public interface LocalNetworkService {
	String getPublicNetworkId(NeutronApi neutronApi, String region)
			throws OpenStackException;
}
