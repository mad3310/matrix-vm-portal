package com.letv.portal.service.openstack.resource.manager.impl;

import java.util.List;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.v2_0.KeystoneApi;
import org.jclouds.openstack.keystone.v2_0.domain.User;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.features.ImageApi;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.manager.ImageManager;

public class ImageManagerImpl extends AbstractResourceManager implements
		ImageManager {

//	private GlanceApi imageApi;

	public ImageManagerImpl(String endpoint, User user, String password) {
		super(endpoint, user, password);
		
		Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());
//		GlanceApi
		
//		imageApi=ContextBuilder.newBuilder("openstack-glance")
//				.endpoint(endpoint)
//				.credentials(user.getTenantId() + ":" + user.getId(),
//				password).modules(modules).buildApi(GlanceApi.class);
	}

	@Override
	public List<ImageResource> list() {

		return null;
	}

	@Override
	public Set<String> getRegions() {
//		return imageApi;
		return null;
	}

}
