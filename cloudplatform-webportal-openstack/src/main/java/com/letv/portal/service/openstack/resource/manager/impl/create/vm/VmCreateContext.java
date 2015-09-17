package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;

public class VmCreateContext {

	private Port subnetPort;
	private Volume volume;
	private FloatingIP floatingIp;
	private ServerCreated serverCreated;
	
	public void setSubnetPort(Port subnetPort) {
		this.subnetPort = subnetPort;
	}
	
	public Port getSubnetPort() {
		return subnetPort;
	}

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
	
	public ServerCreated getServerCreated() {
		return serverCreated;
	}
	
	public void setServerCreated(ServerCreated serverCreated) {
		this.serverCreated = serverCreated;
	}

}
