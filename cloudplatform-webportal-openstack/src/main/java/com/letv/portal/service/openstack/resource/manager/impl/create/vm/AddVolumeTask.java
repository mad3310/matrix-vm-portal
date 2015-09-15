package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.exception.OpenStackException;

public class AddVolumeTask implements VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			context.getApiCache()
					.getVolumeAttachmentApi()
					.attachVolumeToServerAsDevice(
							vmCreateContext.getVolume().getId(),
							vmCreateContext.getServerCreated().getId(), "");
		}
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
	}

}
