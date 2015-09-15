package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.exception.OpenStackException;

public class BindFloatingIpTask implements VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			context.getApiCache()
					.getNovaFloatingIPApi()
					.addToServer(
							vmCreateContext.getFloatingIp()
									.getFloatingIpAddress(),
							vmCreateContext.getServerCreated().getId());
		}
		// TODO send email
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
	}

}
