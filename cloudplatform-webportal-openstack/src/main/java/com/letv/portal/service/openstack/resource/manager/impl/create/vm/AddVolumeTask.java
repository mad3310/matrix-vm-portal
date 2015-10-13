package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import org.jclouds.openstack.cinder.v1.domain.Volume;

public class AddVolumeTask implements VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			context.getVolumeManager().waitingVolume(context.getApiCache().getVolumeApi(), vmCreateContext.getVolume().getId(), 1000, new Checker<Volume>() {
				@Override
				public boolean check(Volume volume) throws Exception {
					return volume == null || volume.getStatus() == Volume.Status.AVAILABLE;
				}
			});
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
