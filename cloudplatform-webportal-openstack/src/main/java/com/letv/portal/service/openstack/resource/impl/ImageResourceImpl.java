package com.letv.portal.service.openstack.resource.impl;

import com.letv.portal.service.openstack.resource.ImageResource;

import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;

public class ImageResourceImpl extends AbstractResource implements
		ImageResource {

	private String region;
	private ImageDetails image;

	public ImageResourceImpl(String region, ImageDetails image) {
		this.region = region;
		this.image = image;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return this.image.getId();
	}

	@Override
	public String getName() {
		return this.image.getName();
	}

	@Override
	public String getChecksum() {
		return image.getChecksum().get();
	}

	@Override
	public String getContainerFormat() {
		return image.getContainerFormat().get().toString();
	}

	@Override
	public long getCreatedAt() {
		return image.getCreatedAt().getTime();
	}

	@Override
	public long getUpdatedAt() {
		return image.getUpdatedAt().getTime();
	}

	@Override
	public long getDeletedAt() {
		return image.getDeletedAt().get().getTime();
	}

	@Override
	public String getDiskFormat() {
		return image.getDiskFormat().get().toString();
	}

	@Override
	public String getLocation() {
		return image.getLocation().get();
	}

	@Override
	public long getMinDisk() {
		return image.getMinDisk();
	}

	@Override
	public long getMinRam() {
		return image.getMinRam();
	}

	@Override
	public String getOwner() {
		return image.getOwner().get();
	}

	@Override
	public long getSize() {
		return image.getSize().get();
	}

	@Override
	public String getStatus() {
		return image.getStatus().toString();
	}

}
