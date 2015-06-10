package com.letv.portal.service.openstack.impl;

import java.io.IOException;

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

	private ImageManagerImpl imageManager;
	private NetworkManagerImpl networkManager;
	private VMManagerImpl vmManager;

	private Object imageManagerLock;
	private Object networkManagerLock;
	private Object vmManagerLock;

	public OpenStackSessionImpl(String endpoint, User user, String password) {
		this.endpoint = endpoint;
		this.user = user;
		this.password = password;

		this.imageManagerLock = new Object();
		this.networkManagerLock = new Object();
		this.vmManagerLock = new Object();
	}

	@Override
	public ImageManager getImageManager() {
		if (imageManager == null) {
			synchronized (this.imageManagerLock) {
				if (imageManager == null) {
					imageManager = new ImageManagerImpl(endpoint, user,
							password);
				}
			}
		}
		return imageManager;
	}

	@Override
	public NetworkManager getNetworkManager() {
		if (networkManager == null) {
			synchronized (this.networkManagerLock) {
				if (networkManager == null) {
					networkManager = new NetworkManagerImpl(endpoint, user,
							password);
				}
			}
		}
		return networkManager;
	}

	@Override
	public VMManager getVMManager() {
		if (vmManager == null) {
			synchronized (this.vmManagerLock) {
				if (vmManager == null) {
					vmManager = new VMManagerImpl(endpoint, user, password);
				}
			}
		}
		return vmManager;
	}

	@Override
	public void close() throws IOException {
		if (imageManager != null) {
			imageManager.close();
		}
		if (networkManager != null) {
			networkManager.close();
		}
		if (vmManager != null) {
			vmManager.close();
		}
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
