package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

public class VmCreateContext {
	private MultiVmCreateContext multiVmCreateContext;
	
	public void setMultiVmCreateContext(
			MultiVmCreateContext multiVmCreateContext) {
		this.multiVmCreateContext = multiVmCreateContext;
	}
	
	public MultiVmCreateContext getMultiVmCreateContext() {
		return multiVmCreateContext;
	}
}
