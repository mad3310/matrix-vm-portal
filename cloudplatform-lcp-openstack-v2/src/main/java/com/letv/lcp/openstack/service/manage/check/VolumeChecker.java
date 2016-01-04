package com.letv.lcp.openstack.service.manage.check;

import org.jclouds.openstack.cinder.v1.domain.Volume;

public interface VolumeChecker {
	boolean check(Volume volume);
}
