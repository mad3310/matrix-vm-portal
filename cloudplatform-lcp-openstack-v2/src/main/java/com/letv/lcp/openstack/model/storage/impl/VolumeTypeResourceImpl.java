package com.letv.lcp.openstack.model.storage.impl;

import org.jclouds.openstack.cinder.v1.domain.VolumeType;

import com.letv.lcp.openstack.model.base.AbstractResource;
import com.letv.lcp.openstack.model.storage.VolumeTypeResource;


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

	@Override
	public Boolean getEnable() {
		return null;
	}

	@Override
	public Long getCapacity() {
		return null;
	}

}
