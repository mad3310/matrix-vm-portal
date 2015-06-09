package com.letv.portal.service.openstack.resource.manager.impl;

import org.jclouds.openstack.keystone.v2_0.domain.User;

import com.letv.portal.service.openstack.resource.manager.ResourceManager;

public abstract class AbstractResourceManager implements ResourceManager{
	protected String endpoint;
	protected User user;
	protected String password;

	public AbstractResourceManager(String endpoint, User user, String password) {
		this.endpoint = endpoint;
		this.user = user;
		this.password = password;
	}
}
