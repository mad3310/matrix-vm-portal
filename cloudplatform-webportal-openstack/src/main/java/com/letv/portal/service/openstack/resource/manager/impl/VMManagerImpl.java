package com.letv.portal.service.openstack.resource.manager.impl;

import java.util.List;
import java.util.Set;

import org.jclouds.openstack.keystone.v2_0.domain.User;

import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.manager.VMCreateConf;
import com.letv.portal.service.openstack.resource.manager.VMManager;

public class VMManagerImpl extends AbstractResourceManager implements VMManager{

	public VMManagerImpl(String endpoint, User user, String password) {
		super(endpoint, user, password);
	}

	@Override
	public Set<String> getRegions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VMResource> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VMResource create(VMCreateConf conf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(VMResource vm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(VMResource vm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop(VMResource vm) {
		// TODO Auto-generated method stub
		
	}

}
