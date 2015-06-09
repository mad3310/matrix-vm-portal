package com.letv.portal.service.openstack.impl;

import org.jclouds.openstack.keystone.v2_0.domain.User;

import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;
import com.letv.portal.service.openstack.resource.manager.VMManager;
import com.letv.portal.service.openstack.resource.manager.impl.ImageManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public class OpenStackSessionImpl implements OpenStackSession {

	private String endpoint;
	private User user;
	private String password;

	private ImageManager imageManager;
	private NetworkManager networkManager;
	private VMManager vmManager;

	public OpenStackSessionImpl(String endpoint, User user, String password) {
		this.endpoint = endpoint;
		this.user = user;
		this.password = password;
	}

	@Override
	public ImageManager getImageManager() {
		if (imageManager == null) {
			imageManager = new ImageManagerImpl(endpoint, user, password);
		}
		return imageManager;
	}

	@Override
	public NetworkManager getNetworkManager() {
		if (networkManager == null) {
			networkManager = new NetworkManagerImpl(endpoint, user, password);
		}
		return networkManager;
	}

	@Override
	public VMManager getVMManager() {
		if (vmManager == null) {
			vmManager = new VMManagerImpl(endpoint, user, password);
		}
		return vmManager;
	}

	// private NovaApi novaApi;
	// private String tenantName;
	// private String userName;
	// private String password;
	//
	// public OpenStackSessionImpl(String userName) {
	// novaApi = ContextBuilder.newBuilder(provider)
	// .endpoint("http://xxx.xxx.xxx.xxx:5000/v2.0/")
	// .credentials(identity, credential)
	// .modules(modules)
	// .buildApi(NovaApi.class);
	// }
	//
	// void close() {
	//
	// }
	//
	// Set<String> listRegions() {
	//
	// }
	//
}
