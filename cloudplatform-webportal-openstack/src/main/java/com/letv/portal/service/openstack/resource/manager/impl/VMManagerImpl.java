package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.v2_0.domain.User;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.features.FlavorApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.jclouds.openstack.nova.v2_0.options.CreateServerOptions;
import org.jclouds.openstack.v2_0.domain.Resource;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.exception.VMDeleteException;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.impl.FlavorResourceImpl;
import com.letv.portal.service.openstack.resource.impl.VMResourceImpl;
import com.letv.portal.service.openstack.resource.manager.VMCreateConf;
import com.letv.portal.service.openstack.resource.manager.VMManager;

public class VMManagerImpl extends AbstractResourceManager implements VMManager {

	private NovaApi novaApi;

	public VMManagerImpl(String endpoint, User user, String password) {
		super(endpoint, user, password);

		Iterable<Module> modules = ImmutableSet
				.<Module> of(new SLF4JLoggingModule());

		novaApi = ContextBuilder.newBuilder("openstack-nova")
				.endpoint(endpoint)
				.credentials(user.getTenantId() + ":" + user.getId(), password)
				.modules(modules).buildApi(NovaApi.class);
	}

	@Override
	public void close() throws IOException {
		novaApi.close();
	}

	@Override
	public Set<String> getRegions() {
		return novaApi.getConfiguredRegions();
	}

	@Override
	public List<VMResource> list(String region) throws RegionNotFoundException {
		checkRegion(region);

		ServerApi serverApi = novaApi.getServerApi(region);
		FluentIterable<Resource> resources = serverApi.list().concat();
		List<VMResource> vmResources = new ArrayList<VMResource>(
				resources.size());
		for (Resource resource : resources) {
			vmResources.add(new VMResourceImpl(region, resource));
		}
		return vmResources;
	}

	@Override
	public VMResource get(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException {
		checkRegion(region);

		ServerApi serverApi = novaApi.getServerApi(region);
		Server server = serverApi.get(id);
		if (server != null) {
			return new VMResourceImpl(region, server);
		} else {
			throw new ResourceNotFoundException(MessageFormat.format(
					"VM '{0}' is not found.", id));
		}
	}

	@Override
	public VMResource create(String region, VMCreateConf conf)
			throws RegionNotFoundException {
		checkRegion(region);

		List<NetworkResource> networkResources = conf.getNetworkResources();
		String[] networks = new String[networkResources.size()];
		for (int i = 0; i < networks.length; i++) {
			networks[i] = networkResources.get(i).getId();
		}

		ServerApi serverApi = novaApi.getServerApi(region);
		ServerCreated serverCreated = serverApi.create(conf.getName(), conf
				.getImageResource().getId(), conf.getFlavorResource().getId(),
				CreateServerOptions.Builder.networks(networks));
		return new VMResourceImpl(region, serverCreated);
	}

	@Override
	public void delete(String region, VMResource vm)
			throws RegionNotFoundException, VMDeleteException {
		checkRegion(region);

		ServerApi serverApi = novaApi.getServerApi(region);
		boolean isSuccess = serverApi.delete(vm.getId());
		if (!isSuccess) {
			throw new VMDeleteException(MessageFormat.format(
					"VM '{0}' delete failed.", vm.getId()));
		}
	}

	@Override
	public void start(String region, VMResource vm)
			throws RegionNotFoundException {
		checkRegion(region);

		ServerApi serverApi = novaApi.getServerApi(region);
		serverApi.start(vm.getId());
	}

	@Override
	public void stop(String region, VMResource vm)
			throws RegionNotFoundException {
		checkRegion(region);

		ServerApi serverApi = novaApi.getServerApi(region);
		serverApi.stop(vm.getId());
	}

	@Override
	public List<FlavorResource> listFlavorResources(String region)
			throws RegionNotFoundException {
		checkRegion(region);

		FlavorApi flavorApi = novaApi.getFlavorApi(region);
		FluentIterable<Resource> resources = flavorApi.list().concat();
		List<FlavorResource> flavorResources = new ArrayList<FlavorResource>(
				resources.size());
		for (Resource resource : resources) {
			flavorResources.add(new FlavorResourceImpl(region, resource));
		}
		return flavorResources;
	}

	@Override
	public FlavorResource getFlavorResource(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException {
		checkRegion(region);

		FlavorApi flavorApi = novaApi.getFlavorApi(region);
		Flavor flavor = flavorApi.get(id);
		if (flavor != null) {
			return new FlavorResourceImpl(region, flavor);
		} else {
			throw new ResourceNotFoundException(MessageFormat.format(
					"Flavor '{0}' is not found.", id));
		}
	}

}
