package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;

import java.util.Date;

public class VmCreateContext {

	private Port subnetPort;
	private Volume volume;
	private FloatingIP floatingIp;
	private ServerCreated serverCreated;
	private Server server;
	private Date floatingIpBindDate;

	public void setServer(Server server) {
		this.server = server;
	}

	public Server getServer() {
		return server;
	}

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

	public Date getFloatingIpBindDate() {
		return floatingIpBindDate;
	}

	public void setFloatingIpBindDate(Date floatingIpBindDate) {
		this.floatingIpBindDate = floatingIpBindDate;
	}
}
