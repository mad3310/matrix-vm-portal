package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.v2_0.KeystoneApi;
import org.jclouds.openstack.keystone.v2_0.domain.User;
import org.jclouds.openstack.keystone.v2_0.features.UserApi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackServiceGroup;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.manager.IdentityManager;

public class IdentityManagerImpl extends AbstractResourceManager implements
		IdentityManager {

	private KeystoneApi keystoneApi;

	public IdentityManagerImpl(OpenStackServiceGroup openStackServiceGroup,
			OpenStackConf openStackConf, OpenStackUser openStackUser) {
		super(openStackServiceGroup, openStackConf, openStackUser);

		Iterable<Module> modules = ImmutableSet
				.<Module> of(new SLF4JLoggingModule());

		keystoneApi = ContextBuilder
				.newBuilder("openstack-keystone")
				.endpoint(openStackConf.getPublicEndpoint())
				.credentials(
						openStackUser.getUserId() + ":"
								+ openStackUser.getUserId(),
						openStackUser.getPassword()).modules(modules)
				.buildApi(KeystoneApi.class);
	}

	@Override
	public void close() throws IOException {
		keystoneApi.close();
	}

	@Override
	public Set<String> getRegions() {
		return null;
	}

	public User getUserByName(String userName) throws APINotAvailableException {
		Optional<? extends UserApi> userApiOptional = keystoneApi.getUserApi();
		if (!userApiOptional.isPresent()) {
			throw new APINotAvailableException(UserApi.class);
		}
		UserApi userApi = userApiOptional.get();

		User user = userApi.getByName(userName);
		return user;
	}

}
