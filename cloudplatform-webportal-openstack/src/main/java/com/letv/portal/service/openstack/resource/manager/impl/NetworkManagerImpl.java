package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.v2_0.domain.User;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.impl.NetworkResourceImpl;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;

public class NetworkManagerImpl extends AbstractResourceManager implements
		NetworkManager {

	private NeutronApi neutronApi;

	public NetworkManagerImpl(String endpoint, String userId, String password) {
		super(endpoint, userId, password);

		Iterable<Module> modules = ImmutableSet
				.<Module> of(new SLF4JLoggingModule());

		neutronApi = ContextBuilder.newBuilder("openstack-neutron")
				.endpoint(endpoint)
				.credentials(userId + ":" + userId, password)
				.modules(modules).buildApi(NeutronApi.class);
	}

	@Override
	public void close() throws IOException {
		neutronApi.close();
	}

	@Override
	public Set<String> getRegions() {
		return neutronApi.getConfiguredRegions();
	}

	@Override
	public List<NetworkResource> list(String region)
			throws RegionNotFoundException {
		checkRegion(region);

		NetworkApi networkApi = neutronApi.getNetworkApi(region);
		FluentIterable<Network> networks = networkApi.list().concat();
		List<NetworkResource> networkResources = new ArrayList<NetworkResource>(
				networks.size());
		for (Network network : networks) {
			networkResources.add(new NetworkResourceImpl(region, network));
		}
		return networkResources;
	}

	@Override
	public NetworkResource get(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException {
		checkRegion(region);

		NetworkApi networkApi = neutronApi.getNetworkApi(region);
		Network network = networkApi.get(id);
		if (network != null) {
			return new NetworkResourceImpl(region, network);
		} else {
			throw new ResourceNotFoundException(MessageFormat.format(
					"Network '{0}' is not found.", id));
		}
	}

}
