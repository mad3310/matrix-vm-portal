package com.letv.portal.service.openstack.resource.manager.impl.task;

import com.letv.portal.service.openstack.resource.manager.impl.ImageManagerImpl;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.impl.VMResourceImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;

public class BindFloatingIP implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(BindFloatingIP.class);

	private VMManagerImpl vmManager;
	private ImageManagerImpl imageManager;
	private String region;
	private Server server;
	private FloatingIP floatingIP;

	public BindFloatingIP(VMManagerImpl vmManager, ImageManagerImpl imageManager,
			String region, Server server, FloatingIP floatingIP) {
		this.vmManager = vmManager;
		this.imageManager = imageManager;
		this.region = region;
		this.server = server;
		this.floatingIP = floatingIP;
	}

	@Override
	public void run() {
		try {
			vmManager.bindFloatingIP(region, floatingIP, server.getId());
			vmManager.emailBindIP(
					new VMResourceImpl(region, vmManager
							.getRegionDisplayName(region), server, vmManager,
							this.imageManager, vmManager.getOpenStackUser()),
					floatingIP.getIp());
		} catch (OpenStackException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
