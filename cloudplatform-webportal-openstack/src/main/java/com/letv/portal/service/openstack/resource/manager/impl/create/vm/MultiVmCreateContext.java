package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;

public class MultiVmCreateContext {
	private String regionDisplayName;

	private VMCreateConf2 vmCreateConf;
	private VMManagerImpl vmManager;

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

}
