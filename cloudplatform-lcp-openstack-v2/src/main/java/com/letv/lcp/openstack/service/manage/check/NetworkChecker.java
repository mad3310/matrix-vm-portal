package com.letv.lcp.openstack.service.manage.check;

import org.jclouds.openstack.neutron.v2.domain.Network;

public interface NetworkChecker {
	boolean check(Network network) throws Exception;
}
