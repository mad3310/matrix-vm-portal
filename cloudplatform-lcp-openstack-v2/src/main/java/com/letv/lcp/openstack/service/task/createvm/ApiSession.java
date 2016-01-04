package com.letv.lcp.openstack.service.task.createvm;

import java.io.Closeable;
import java.io.IOException;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Closeables;
import com.google.inject.Module;
import com.letv.common.exception.MatrixException;
import com.letv.lcp.openstack.model.conf.OpenStackConf;
import com.letv.lcp.openstack.model.user.OpenStackUser;

public class ApiSession implements Closeable {
	private NovaApi novaApi;
	private NeutronApi neutronApi;
	private CinderApi cinderApi;
	private GlanceApi glanceApi;

	public ApiSession(OpenStackConf openStackConf, OpenStackUser openStackUser) {
		Iterable<Module> modules = ImmutableSet
				.<Module> of(new SLF4JLoggingModule());
		this.novaApi = openApi(openStackConf, openStackUser, modules,
				"openstack-nova", NovaApi.class);
		this.neutronApi = openApi(openStackConf, openStackUser, modules,
				"openstack-neutron", NeutronApi.class);
		this.cinderApi = openApi(openStackConf, openStackUser, modules,
				"openstack-cinder", CinderApi.class);
		this.glanceApi = openApi(openStackConf, openStackUser, modules,
				"openstack-glance", GlanceApi.class);
	}

	private <ApiType extends Closeable> ApiType openApi(
			OpenStackConf openStackConf, OpenStackUser openStackUser,
			Iterable<Module> modules, String providerOrApi,
			Class<ApiType> apiClass) {
		ApiType api = ContextBuilder
				.newBuilder(providerOrApi)
				.endpoint(openStackConf.getPublicEndpoint())
				.credentials(
						openStackUser.tenant.jcloudsCredentialsIdentity,
						openStackUser.tenant.password).modules(modules)
				.buildApi(apiClass);
		return api;
	}

	public NovaApi getNovaApi() {
		return novaApi;
	}

	public NeutronApi getNeutronApi() {
		return neutronApi;
	}

	public CinderApi getCinderApi() {
		return cinderApi;
	}
	
	public GlanceApi getGlanceApi() {
		return glanceApi;
	}

	public void close() {
		try {
			Closeables.close(novaApi, true);
			Closeables.close(neutronApi, true);
			Closeables.close(cinderApi, true);
			Closeables.close(glanceApi, true);
		} catch (IOException e) {
			throw new MatrixException("后台错误", e);
		}
	}
}
