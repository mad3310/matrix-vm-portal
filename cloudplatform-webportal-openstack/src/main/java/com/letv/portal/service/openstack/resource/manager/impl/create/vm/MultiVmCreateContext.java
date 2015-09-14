package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.List;

import org.jclouds.openstack.cinder.v1.domain.VolumeType;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;

import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;

public class MultiVmCreateContext {

//	private OpenStackConf openStackConf;
//	private OpenStackUser openStackUser;
	
	private VMCreateConf2 vmCreateConf;
	private VMManagerImpl vmManager;

	private ApiCache apiCache;

	private String regionDisplayName;
	private Flavor flavor;
	private Image image;
	private Network privateNetwork;
	private Subnet privateSubnet;
	private Network sharedNetwork;
	private Image snapshot;
	private VolumeType volumeType;
	private KeyPair keyPair;
	
	private List<VmCreateContext> vmCreateContexts;
	
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
	
	public void setVmCreateContexts(List<VmCreateContext> vmCreateContexts) {
		this.vmCreateContexts = vmCreateContexts;
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
		this.apiCache = apiCache;
	}
	
	public ApiCache getApiCache() {
		return apiCache;
	}
}
