package com.letv.lcp.openstack.service.manage.check;

import org.jclouds.openstack.nova.v2_0.domain.Server;

public interface ServerChecker {
	boolean check(Server server) throws Exception;
}
