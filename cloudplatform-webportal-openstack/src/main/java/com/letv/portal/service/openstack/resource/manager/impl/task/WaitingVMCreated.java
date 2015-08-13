package com.letv.portal.service.openstack.resource.manager.impl.task;

import java.util.List;

import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.PollingInterruptedException;
import com.letv.portal.service.openstack.resource.impl.VMResourceImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;

public class WaitingVMCreated implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(WaitingVMCreated.class);

	private VMManagerImpl vmManager;
	private String region;
	private String vmId;
	private List<Runnable> afterTasks;

	public WaitingVMCreated(VMManagerImpl vmManager, String region,
			String vmId, List<Runnable> afterTasks) {
		this.vmManager = vmManager;
		this.region = region;
		this.vmId = vmId;
		this.afterTasks = afterTasks;
	}

	@Override
	public void run() {
		try {
			Server server =((VMResourceImpl) vmManager.get(region,vmId)).server;
			while (server
					.getAddresses()
					.get(vmManager.getOpenStackConf()
							.getUserPrivateNetworkName()).isEmpty()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new PollingInterruptedException(e);
				}
				server =((VMResourceImpl) vmManager.get(region,vmId)).server;
			}
			for (Runnable task : afterTasks) {
				task.run();
			}
		} catch (OpenStackException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
