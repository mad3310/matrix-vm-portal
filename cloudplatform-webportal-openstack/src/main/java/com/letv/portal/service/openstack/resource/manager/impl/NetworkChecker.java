package com.letv.portal.service.openstack.resource.manager.impl;

import org.jclouds.openstack.neutron.v2.domain.Network;

public interface NetworkChecker {
	boolean check(Network network) throws Exception;
}
