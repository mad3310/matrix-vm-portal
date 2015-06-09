package com.letv.portal.service.openstack.resource.manager.impl;

import java.util.Set;

import org.jclouds.openstack.keystone.v2_0.domain.User;

import com.letv.portal.service.openstack.resource.manager.NetworkManager;

public class NetworkManagerImpl extends AbstractResourceManager implements NetworkManager{

	public NetworkManagerImpl(String endpoint, User user, String password) {
		super(endpoint, user, password);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<String> getRegions() {
		// TODO Auto-generated method stub
		return null;
	}

}
