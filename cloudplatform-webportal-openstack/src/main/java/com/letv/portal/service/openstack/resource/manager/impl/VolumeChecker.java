package com.letv.portal.service.openstack.resource.manager.impl;

import org.jclouds.openstack.cinder.v1.domain.Volume;

public interface VolumeChecker {
	boolean check(Volume volume);
}
