package com.letv.lcp.openstack.service.jclouds;

import java.io.Closeable;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.lcp.openstack.model.user.OpenStackTenant;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.portal.model.common.UserVo;
import com.letv.portal.service.common.IUserService;

@Service("apiSession")
public class ApiSession {
	@Autowired
    private IUserService userService;


	private <ApiType extends Closeable> ApiType openApi(String identity, String password,
			Iterable<Module> modules, String providerOrApi,
			Class<ApiType> apiClass) {
		ApiType api = ContextBuilder
				.newBuilder(providerOrApi)
				.endpoint(OpenStackServiceImpl.getOpenStackConf().getPublicEndpoint())
				.credentials(identity, password).modules(modules)
				.buildApi(apiClass);
		return api;
	}

	public NovaApi getNovaApi(Long userId) {
		UserVo user = userService.getUcUserById(userId);
		OpenStackTenant openStackTenant = new OpenStackTenant(userId, user.getEmail());
		
		Iterable<Module> modules = ImmutableSet.<Module> of(new SLF4JLoggingModule());
		return openApi(openStackTenant.jcloudsCredentialsIdentity,
				openStackTenant.password, modules,
				"openstack-nova", NovaApi.class);
	}

	public NeutronApi getNeutronApi(Long userId) {
		UserVo user = userService.getUcUserById(userId);
		OpenStackTenant openStackTenant = new OpenStackTenant(userId, user.getEmail());
		
		Iterable<Module> modules = ImmutableSet.<Module> of(new SLF4JLoggingModule());
		return openApi(openStackTenant.jcloudsCredentialsIdentity,
				openStackTenant.password, modules,
				"openstack-neutron", NeutronApi.class);
	}

	public CinderApi getCinderApi(Long userId) {
		UserVo user = userService.getUcUserById(userId);
		OpenStackTenant openStackTenant = new OpenStackTenant(userId, user.getEmail());
		
		Iterable<Module> modules = ImmutableSet.<Module> of(new SLF4JLoggingModule());
		return openApi(openStackTenant.jcloudsCredentialsIdentity,
				openStackTenant.password, modules,
				"openstack-cinder", CinderApi.class);
	}
	
	public GlanceApi getGlanceApi(Long userId) {
		UserVo user = userService.getUcUserById(userId);
		OpenStackTenant openStackTenant = new OpenStackTenant(userId, user.getEmail());
		
		Iterable<Module> modules = ImmutableSet.<Module> of(new SLF4JLoggingModule());
		return openApi(openStackTenant.jcloudsCredentialsIdentity,
				openStackTenant.password, modules,
				"openstack-glance", GlanceApi.class);
	}

}
