package com.letv.portal.service.openstack.resource.impl;

import java.util.Date;

import org.jclouds.openstack.glance.v1_0.domain.ContainerFormat;
import org.jclouds.openstack.glance.v1_0.domain.DiskFormat;
import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;

import com.google.common.base.Optional;
import com.letv.portal.service.openstack.resource.ImageResource;

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
		return image.getChecksum().orNull();
	}

	@Override
	public String getContainerFormat() {
		Optional<ContainerFormat> optional = image.getContainerFormat();
		if (optional.isPresent()) {
			return optional.get().toString();
		} else {
			return null;
		}
	}

	@Override
	public Long getCreatedAt() {
		return image.getCreatedAt().getTime();
	}

	@Override
	public Long getUpdatedAt() {
		return image.getUpdatedAt().getTime();
	}

	@Override
	public Long getDeletedAt() {
		Optional<Date> optional = image.getDeletedAt();
		if (optional.isPresent()) {
			return optional.get().getTime();
		} else {
			return null;
		}
	}

	@Override
	public String getDiskFormat() {
		Optional<DiskFormat> optional = image.getDiskFormat();
		if (optional.isPresent()) {
			return optional.get().toString();
		} else {
			return null;
		}
	}

	@Override
	public String getLocation() {
		return image.getLocation().orNull();
	}

	@Override
	public Long getMinDisk() {
		return image.getMinDisk();
	}

	@Override
	public Long getMinRam() {
		return image.getMinRam();
	}

	@Override
	public String getOwner() {
		return image.getOwner().orNull();
	}

	@Override
	public Long getSize() {
		return image.getSize().orNull();
	}

	@Override
	public String getStatus() {
		return image.getStatus().toString();
	}

}
