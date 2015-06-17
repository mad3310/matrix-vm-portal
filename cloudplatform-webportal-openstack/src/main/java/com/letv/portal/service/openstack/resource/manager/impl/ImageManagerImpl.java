package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;
import org.jclouds.openstack.glance.v1_0.features.ImageApi;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.impl.ImageResourceImpl;
import com.letv.portal.service.openstack.resource.manager.ImageManager;

public class ImageManagerImpl extends AbstractResourceManager implements
		ImageManager {

	private GlanceApi glanceApi;

	public ImageManagerImpl(String endpoint, String userId, String password) {
		super(endpoint, userId, password);

		Iterable<Module> modules = ImmutableSet
				.<Module> of(new SLF4JLoggingModule());

		glanceApi = ContextBuilder.newBuilder("openstack-glance")
				.endpoint(endpoint)
				.credentials(userId + ":" + userId, password)
				.modules(modules).buildApi(GlanceApi.class);
	}

	@Override
	public void close() throws IOException {
		glanceApi.close();
	}

	@Override
	public Set<String> getRegions() {
		return glanceApi.getConfiguredRegions();
	}

	@Override
	public List<ImageResource> list(String region)
			throws RegionNotFoundException {
		checkRegion(region);

		ImageApi imageApi = glanceApi.getImageApi(region);
		List<ImageDetails> images = imageApi.listInDetail().concat().toList();
		List<ImageResource> imageResources = new ArrayList<ImageResource>(
				images.size());
		for (ImageDetails image : images) {
			imageResources.add(new ImageResourceImpl(region, image));
		}
		return imageResources;
	}

	@Override
	public ImageResource get(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException {
		checkRegion(region);

		ImageApi imageApi = glanceApi.getImageApi(region);
		ImageDetails imageDetails = imageApi.get(id);
		if (imageDetails != null) {
			return new ImageResourceImpl(region, imageDetails);
		} else {
			throw new ResourceNotFoundException(MessageFormat.format(
					"Image \"{0}\" is not found.", id));
		}
	}

}
