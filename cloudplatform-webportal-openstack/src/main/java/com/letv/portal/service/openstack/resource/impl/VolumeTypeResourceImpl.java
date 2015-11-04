package com.letv.portal.service.openstack.resource.impl;

import org.jclouds.openstack.cinder.v1.domain.VolumeType;

import com.letv.portal.service.openstack.resource.VolumeTypeResource;

public class VolumeTypeResourceImpl extends AbstractResource implements
		VolumeTypeResource {

	private String region;

	private VolumeType volumeType;

	public VolumeTypeResourceImpl(String region, VolumeType volumeType) {
		this.region = region;
		this.volumeType = volumeType;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return volumeType.getId();
	}

	@Override
	public String getName() {
		return volumeType.getName();
	}

	@Override
	public Long getThroughput() {
		return null;
	}

	@Override
	public Long getIops() {
		return null;
	}

}
