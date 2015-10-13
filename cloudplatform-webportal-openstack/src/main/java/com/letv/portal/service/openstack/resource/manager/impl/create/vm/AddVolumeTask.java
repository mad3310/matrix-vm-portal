package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import com.letv.portal.service.openstack.util.Ref;
import org.jclouds.openstack.cinder.v1.domain.Volume;

public class AddVolumeTask implements VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			final Ref<Boolean> volumeExists = new Ref<Boolean>(true);
			context.getVolumeManager().waitingVolume(context.getApiCache().getVolumeApi(), vmCreateContext.getVolume().getId(), 1000, new Checker<Volume>() {
				@Override
				public boolean check(Volume volume) throws Exception {
					if (volume == null) {
						volumeExists.set(false);
						return true;
					}
					return volume.getStatus() == Volume.Status.AVAILABLE;
				}
			});
			if (volumeExists.get()) {
				context.getApiCache()
						.getVolumeAttachmentApi()
						.attachVolumeToServerAsDevice(
								vmCreateContext.getVolume().getId(),
								vmCreateContext.getServerCreated().getId(), "");
			}
		}
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
	}

}
