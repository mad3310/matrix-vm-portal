package com.letv.portal.service.openstack.resource.manager.impl.task;

import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.impl.VMResourceImpl;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;

public class BindFloatingIP implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(BindFloatingIP.class);

	private VMManagerImpl vmManager;
	private ImageManager imageManager;
	private String region;
	private Server server;

	public BindFloatingIP(VMManagerImpl vmManager, ImageManager imageManager,
			String region, Server server) {
		this.vmManager = vmManager;
		this.imageManager = imageManager;
		this.region = region;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			String floatingIP = vmManager
					.bindFloatingIP(region, server.getId());
			vmManager.emailBindIP(
					new VMResourceImpl(region, vmManager
							.getRegionDisplayName(region), server, vmManager,
							this.imageManager, vmManager.getOpenStackUser()),
					floatingIP);
		} catch (OpenStackException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
