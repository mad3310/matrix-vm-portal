package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackServiceGroup;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.VolumeResource;
import com.letv.portal.service.openstack.resource.impl.VolumeResourceImpl;
import com.letv.portal.service.openstack.resource.manager.VolumeManager;

public class VolumeManagerImpl extends AbstractResourceManager implements
		VolumeManager {

	private CinderApi cinderApi;

	public VolumeManagerImpl(OpenStackServiceGroup openStackServiceGroup,
			OpenStackConf openStackConf, OpenStackUser openStackUser) {
		super(openStackServiceGroup, openStackConf, openStackUser);

		Iterable<Module> modules = ImmutableSet
				.<Module> of(new SLF4JLoggingModule());

		cinderApi = ContextBuilder
				.newBuilder("openstack-cinder")
				.endpoint(openStackConf.getPublicEndpoint())
				.credentials(
						openStackUser.getUserId() + ":"
								+ openStackUser.getUserId(),
						openStackUser.getPassword()).modules(modules)
				.buildApi(CinderApi.class);
	}

	@Override
	public Set<String> getRegions() {
		return cinderApi.getConfiguredRegions();
	}

	@Override
	public void close() throws IOException {
		cinderApi.close();
	}

	@Override
	public VolumeResource get(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException {
		checkRegion(region);

		VolumeApi volumeApi = cinderApi.getVolumeApi(region);
		Volume volume = volumeApi.get(id);
		if (volume != null) {
			return new VolumeResourceImpl(region, volume);
		} else {
			throw new ResourceNotFoundException("Volume", "卷", id);
		}
	}

	public CinderApi getCinderApi() {
		return cinderApi;
	}
	
	
}
