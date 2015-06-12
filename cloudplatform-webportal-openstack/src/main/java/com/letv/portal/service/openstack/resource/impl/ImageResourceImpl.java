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

}
