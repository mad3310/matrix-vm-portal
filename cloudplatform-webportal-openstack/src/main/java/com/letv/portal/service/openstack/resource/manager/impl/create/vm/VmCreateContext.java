package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;

public class VmCreateContext {
	private MultiVmCreateContext multiVmCreateContext;

	private Volume volume;
	private FloatingIP floatingIp;

	public void setFloatingIp(FloatingIP floatingIp) {
		this.floatingIp = floatingIp;
	}

	public FloatingIP getFloatingIp() {
		return floatingIp;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	public Volume getVolume() {
		return volume;
	}

	public void setMultiVmCreateContext(
			MultiVmCreateContext multiVmCreateContext) {
		this.multiVmCreateContext = multiVmCreateContext;
	}

	public MultiVmCreateContext getMultiVmCreateContext() {
		return multiVmCreateContext;
	}
}
