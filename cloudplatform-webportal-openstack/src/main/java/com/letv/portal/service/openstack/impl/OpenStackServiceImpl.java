package com.letv.portal.service.openstack.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.v2_0.KeystoneApi;
import org.jclouds.openstack.keystone.v2_0.domain.User;
import org.jclouds.openstack.keystone.v2_0.features.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.internal.UserRegister;
import com.letv.portal.service.openstack.password.PasswordService;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
@Service("openStackService")
public class OpenStackServiceImpl implements OpenStackService {

	@Value("openstack.endpoint")
	private String endpoint;

	@Value("openstack.admin.tenantName")
	private String adminTenantName;

	@Value("openstack.admin.userName")
	private String adminUserName;

	@Value("openstack.admin.credential")
	private String adminCredential;

	@Autowired
	private PasswordService passwordService;

	private KeystoneApi keystoneApi;
	private UserApi userApi;

	@PostConstruct
	public void open() throws OpenStackException {
		Iterable<Module> modules = ImmutableSet
				.<Module> of(new SLF4JLoggingModule());
		keystoneApi = ContextBuilder
				.newBuilder("openstack-keystone")
				.endpoint(endpoint)
				.credentials(adminTenantName + ":" + adminUserName,
						adminCredential).modules(modules)
				.buildApi(KeystoneApi.class);

		Optional<? extends UserApi> userApiOptional = keystoneApi.getUserApi();
		if (userApiOptional.isPresent()) {
			this.userApi = userApiOptional.get();
		} else {
			throw new OpenStackException(
					"The user api of OpenStack does not exist.");
		}
	}

	@PreDestroy
	public void close() throws IOException {
		keystoneApi.close();
	}

	public OpenStackSession createSession(String userId)
			throws OpenStackException {
		User user = userApi.get(userId);
		try {
			if (user == null) {
				UserRegister userRegister = new UserRegister(endpoint, userId,
						passwordService.userIdToPassword(userId));
				userRegister.run();
				user = userApi.get(userId);
				if (user == null) {
					throw new OpenStackException(
							"can not create openstack user:" + userId);
				}
			}
			return new OpenStackSessionImpl(endpoint, user,
					passwordService.userIdToPassword(userId));
		} catch (NoSuchAlgorithmException e) {
			throw new OpenStackException(e);
		}
	}

}
