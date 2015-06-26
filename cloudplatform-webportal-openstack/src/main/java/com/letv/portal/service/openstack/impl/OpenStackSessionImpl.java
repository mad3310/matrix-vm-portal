package com.letv.portal.service.openstack.impl;

import java.io.IOException;

import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.ExternalGatewayInfo;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;

import com.google.common.io.Closeables;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.exception.OpenStackException;
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

	@SuppressWarnings("unused")
	private OpenStackConf openStackConf;
	@SuppressWarnings("unused")
	private OpenStackUser openStackUser;

	private ImageManagerImpl imageManager;
	private NetworkManagerImpl networkManager;
	private VMManagerImpl vmManager;

	// private Object imageManagerLock;
	// private Object networkManagerLock;
	// private Object vmManagerLock;

	private boolean isClosed;

	public OpenStackSessionImpl(OpenStackConf openStackConf, OpenStackUser openStackUser) throws OpenStackException{
		this.openStackConf = openStackConf;
		this.openStackUser = openStackUser;

		// this.imageManagerLock = new Object();
		// this.networkManagerLock = new Object();
		// this.vmManagerLock = new Object();
		
		openStackUser.setPrivateNetworkName(openStackConf.getUserPrivateNetworkName());

		imageManager = new ImageManagerImpl(openStackConf, openStackUser);
		networkManager = new NetworkManagerImpl(openStackConf, openStackUser);
		vmManager = new VMManagerImpl(openStackConf, openStackUser);
		vmManager.setImageManager(imageManager);
		vmManager.setNetworkManager(networkManager);

		isClosed = false;

//		if (openStackUser.getFirstLogin()) {
			NeutronApi neutronApi = networkManager.getNeutronApi();
			for (String region : neutronApi.getConfiguredRegions()) {
				NetworkApi networkApi = neutronApi.getNetworkApi(region);

				Network publicNetwork = networkApi.get(openStackConf.getGlobalPublicNetworkId());
//				for (Network network : networkApi.list().concat().toList()) {
//					if ("__public_network".equals(network.getName())) {
//						publicNetwork = network;
//						break;
//					}
//				}
				if (publicNetwork == null) {
					throw new OpenStackException("can not find public network under region: "+region,"后台服务异常");
				}
				openStackUser.setPublicNetworkName(publicNetwork.getName());
				
				if(openStackUser.getInternalUser()){
					openStackUser.setSharedNetworkName(networkApi.get(openStackConf.getGlobalSharedNetworkId()).getName());
				}

				Network privateNetwork = null;
				for(Network network:networkApi.list().concat().toList()){
					if(openStackConf.getUserPrivateNetworkName().equals(network.getName())){
						privateNetwork=network;
						break;
					}
				}
				if(privateNetwork==null){
					privateNetwork = networkApi.create(Network.CreateNetwork
							.createBuilder("").name(openStackConf.getUserPrivateNetworkName())
							.build());
				}
				
				SubnetApi subnetApi = neutronApi.getSubnetApi(region);
				
				Subnet privateSubnet = null;
				for(Subnet subnet:subnetApi.list().concat().toList()){
					if(openStackConf.getUserPrivateNetworkSubnetName().equals(subnet.getName())){
						privateSubnet=subnet;
						break;
					}
				}
				if(privateSubnet==null){
					privateSubnet = subnetApi.create(Subnet.CreateSubnet
							.createBuilder(privateNetwork.getId(), openStackConf.getUserPrivateNetworkSubnetCidr())
							.enableDhcp(true).name(openStackConf.getUserPrivateNetworkSubnetName()).ipVersion(4).build());
				}
				
				RouterApi routerApi = neutronApi.getRouterApi(region).get();

				Router privateRouter =null;
				for(Router router:routerApi.list().concat().toList()){
					if(openStackConf.getUserPrivateRouterName().equals(router.getName())){
						privateRouter=router;
						break;
					}
				}
				if(privateRouter==null){
					privateRouter = routerApi.create(Router.CreateRouter
							.createBuilder().name(openStackConf.getUserPrivateRouterName())
							.externalGatewayInfo(
									ExternalGatewayInfo.builder()//.enableSnat(true)
											.networkId(publicNetwork.getId())
											.build()).build());
					try{
						routerApi.addInterfaceForSubnet(privateRouter.getId(), privateSubnet.getId());
					}catch(Exception ex){
						routerApi.delete(privateRouter.getId());
						throw new OpenStackException("后台服务异常",ex);
					}
				}
				
//				Router router = routerApi.create(Router.CreateRouter
//						.createBuilder().name(openStackConf.getUserPrivateRouterName())
//						.build());
//				.externalGatewayInfo(
//						ExternalGatewayInfo.builder().enableSnat(true)
//								.networkId(publicNetwork.getId())
//								.build())
				
			}
//		}
	}

	@Override
	public ImageManager getImageManager() {
		// if (imageManager == null) {
		// synchronized (this.imageManagerLock) {
		// if (imageManager == null) {
		// imageManager = new ImageManagerImpl(endpoint, userId,
		// password);
		// }
		// }
		// }
		return imageManager;
	}

	@Override
	public NetworkManager getNetworkManager() {
		// if (networkManager == null) {
		// synchronized (this.networkManagerLock) {
		// if (networkManager == null) {
		// networkManager = new NetworkManagerImpl(endpoint, userId,
		// password);
		// }
		// }
		// }
		return networkManager;
	}

	@Override
	public VMManager getVMManager() {
		// if (vmManager == null) {
		// synchronized (this.vmManagerLock) {
		// if (vmManager == null) {
		// vmManager = new VMManagerImpl(endpoint, userId, password);
		// }
		// }
		// }
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
