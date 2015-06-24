package com.letv.portal.service.openstack.impl;

import com.google.common.io.Closeables;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;
import com.letv.portal.service.openstack.resource.manager.VMManager;
import com.letv.portal.service.openstack.resource.manager.impl.ImageManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;

import java.io.IOException;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public class OpenStackSessionImpl implements OpenStackSession {

	private String endpoint;
	private String userId;
	private String password;

	private ImageManagerImpl imageManager;
	private NetworkManagerImpl networkManager;
	private VMManagerImpl vmManager;

//	private Object imageManagerLock;
//	private Object networkManagerLock;
//	private Object vmManagerLock;

	private boolean isClosed;

	public OpenStackSessionImpl(String endpoint, String userId, String password) {
		this.endpoint = endpoint;
		this.userId = userId;
		this.password = password;

//		this.imageManagerLock = new Object();
//		this.networkManagerLock = new Object();
//		this.vmManagerLock = new Object();
		
		imageManager = new ImageManagerImpl(endpoint, userId,
				password);
		networkManager = new NetworkManagerImpl(endpoint, userId,
				password);
		vmManager = new VMManagerImpl(endpoint, userId, password);
		vmManager.setImageManager(imageManager);
		vmManager.setNetworkManager(networkManager);

		isClosed = false;
	}

	@Override
	public ImageManager getImageManager() {
//		if (imageManager == null) {
//			synchronized (this.imageManagerLock) {
//				if (imageManager == null) {
//					imageManager = new ImageManagerImpl(endpoint, userId,
//							password);
//				}
//			}
//		}
		return imageManager;
	}

	@Override
	public NetworkManager getNetworkManager() {
//		if (networkManager == null) {
//			synchronized (this.networkManagerLock) {
//				if (networkManager == null) {
//					networkManager = new NetworkManagerImpl(endpoint, userId,
//							password);
//				}
//			}
//		}
		return networkManager;
	}

	@Override
	public VMManager getVMManager() {
//		if (vmManager == null) {
//			synchronized (this.vmManagerLock) {
//				if (vmManager == null) {
//					vmManager = new VMManagerImpl(endpoint, userId, password);
//				}
//			}
//		}
		return vmManager;
	}

	@Override
	public boolean isClosed() {
		return isClosed;
	}

	@Override
	public void close() throws IOException {
		isClosed = true;
		if (imageManager != null) {
			Closeables.close(imageManager, true);
		}
		if (networkManager != null) {
			Closeables.close(networkManager, true);
		}
		if (vmManager != null) {
			Closeables.close(vmManager, true);
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
