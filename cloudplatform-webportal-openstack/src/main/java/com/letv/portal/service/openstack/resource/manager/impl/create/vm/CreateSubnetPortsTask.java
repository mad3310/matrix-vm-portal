package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Port;

import com.google.common.collect.ImmutableSet;
import com.letv.portal.service.openstack.exception.OpenStackException;

public class CreateSubnetPortsTask implements VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		if (context.getPrivateSubnet() == null) {
			return;
		}

		for (int i = 0; i < context.getVmCreateConf().getCount(); i++) {
			createOnePort(context, context.getVmCreateContexts().get(i));
		}
	}

	private void createOnePort(MultiVmCreateContext context,
			VmCreateContext vmCreateContext) {
		Port subnetPort = context
				.getApiCache()
				.getPortApi()
				.create(Port
						.createBuilder(context.getPrivateNetwork().getId())
						.fixedIps(
								ImmutableSet.<IP> of(IP
										.builder()
										.subnetId(
												context.getPrivateSubnet()
														.getId()).build()))
						.build());
		vmCreateContext.setSubnetPort(subnetPort);
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
		if (context.getPrivateSubnet() == null) {
			return;
		}

		for (int i = 0; i < context.getVmCreateConf().getCount(); i++) {
			VmCreateContext vmCreateContext = context.getVmCreateContexts()
					.get(i);
			if (vmCreateContext.getServerCreated() == null) {
				context.getApiCache().getPortApi()
						.delete(vmCreateContext.getSubnetPort().getId());
			}
		}
	}

}
