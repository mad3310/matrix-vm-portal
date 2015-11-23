package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.List;

import com.letv.portal.service.openstack.billing.listeners.VmCreateListener;
import org.jclouds.openstack.cinder.v1.domain.VolumeType;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;

import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VolumeManagerImpl;

public class MultiVmCreateContext {

//	private OpenStackConf openStackConf;
//	private OpenStackUser openStackUser;
	
	private VMCreateConf2 vmCreateConf;
	private VMManagerImpl vmManager;
	private NetworkManagerImpl networkManager;
	private VolumeManagerImpl volumeManager;

//	private ApiSession apiSession;
	private ThreadLocal<ApiCache> apiCacheThreadLocal=new ThreadLocal<ApiCache>();

	private String regionDisplayName;
	private Flavor flavor;
	private Image image;
	private Network privateNetwork;
	private Subnet privateSubnet;
	private Network sharedNetwork;
	private Image snapshot;
	private VolumeType volumeType;
	private KeyPair keyPair;
	private Network floatingNetwork;
	
	private List<VmCreateContext> vmCreateContexts;

	private VmCreateListener vmCreateListener;
	private Object listenerUserData;
	private Long userId;

//	public void setOpenStackConf(OpenStackConf openStackConf) {
//		this.openStackConf = openStackConf;
//	}
//	
//	public OpenStackConf getOpenStackConf() {
//		return openStackConf;
//	}
//	
//	public void setOpenStackUser(OpenStackUser openStackUser) {
//		this.openStackUser = openStackUser;
//	}
//	
//	public OpenStackUser getOpenStackUser() {
//		return openStackUser;
//	}
	
//	public void setApiSession(ApiSession apiSession) {
//		this.apiSession = apiSession;
//	}
//	
//	public ApiSession getApiSession() {
//		return apiSession;
//	}
	
	public void setVolumeManager(VolumeManagerImpl volumeManager) {
		this.volumeManager = volumeManager;
	}
	
	public VolumeManagerImpl getVolumeManager() {
		return volumeManager;
	}
	
	public void setFloatingNetwork(Network floatingNetwork) {
		this.floatingNetwork = floatingNetwork;
	}
	
	public Network getFloatingNetwork() {
		return floatingNetwork;
	}
	
	public void setVmCreateContexts(List<VmCreateContext> vmCreateContexts) {
		this.vmCreateContexts = vmCreateContexts;
	}
	
	public NetworkManagerImpl getNetworkManager() {
		return networkManager;
	}

	public void setNetworkManager(NetworkManagerImpl networkManager) {
		this.networkManager = networkManager;
	}

	public List<VmCreateContext> getVmCreateContexts() {
		return vmCreateContexts;
	}

	public void setPrivateNetwork(Network privateNetwork) {
		this.privateNetwork = privateNetwork;
	}

	public Network getPrivateNetwork() {
		return privateNetwork;
	}

	public void setPrivateSubnet(Subnet privateSubnet) {
		this.privateSubnet = privateSubnet;
	}

	public Subnet getPrivateSubnet() {
		return privateSubnet;
	}

	public void setSharedNetwork(Network sharedNetwork) {
		this.sharedNetwork = sharedNetwork;
	}

	public Network getSharedNetwork() {
		return sharedNetwork;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public String getRegionDisplayName() {
		return regionDisplayName;
	}

	public void setRegionDisplayName(String regionDisplayName) {
		this.regionDisplayName = regionDisplayName;
	}

	public VMCreateConf2 getVmCreateConf() {
		return vmCreateConf;
	}

	public void setVmCreateConf(VMCreateConf2 vmCreateConf) {
		this.vmCreateConf = vmCreateConf;
	}

	public VMManagerImpl getVmManager() {
		return vmManager;
	}

	public void setVmManager(VMManagerImpl vmManager) {
		this.vmManager = vmManager;
	}

	public void setFlavor(Flavor flavor) {
		this.flavor = flavor;
	}

	public Flavor getFlavor() {
		return flavor;
	}

	public void setSnapshot(Image snapshot) {
		this.snapshot = snapshot;
	}

	public Image getSnapshot() {
		return snapshot;
	}

	public void setVolumeType(VolumeType volumeType) {
		this.volumeType = volumeType;
	}

	public VolumeType getVolumeType() {
		return volumeType;
	}

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public void setApiCache(ApiCache apiCache) {
		this.apiCacheThreadLocal.set(apiCache);
	}
	
	public ApiCache getApiCache() {
		return apiCacheThreadLocal.get();
	}

	public void setVmCreateListener(VmCreateListener vmCreateListener) {
		this.vmCreateListener = vmCreateListener;
	}

	public VmCreateListener getVmCreateListener() {
		return vmCreateListener;
	}

	public void setListenerUserData(Object listenerUserData) {
		this.listenerUserData = listenerUserData;
	}

	public Object getListenerUserData() {
		return listenerUserData;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}
}
