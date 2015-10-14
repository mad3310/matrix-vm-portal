package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import com.letv.portal.service.openstack.util.Ref;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.nova.v2_0.domain.Server;

public class AddVolumeTask implements VmsCreateSubTask {

    @Override
    public void run(MultiVmCreateContext context) throws OpenStackException {
        for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
            final Ref<Volume> volumeRef = new Ref<Volume>();
            context.getVolumeManager().waitingVolume(context.getApiCache().getVolumeApi(), vmCreateContext.getVolume().getId(), 1000, new Checker<Volume>() {
                @Override
                public boolean check(Volume volume) throws Exception {
                    if (volume == null) {
                        volumeRef.set(volume);
                        return true;
                    }
                    return volume.getStatus() == Volume.Status.AVAILABLE;
                }
            });
            Server server = context.getApiCache().getServerApi().get(vmCreateContext.getServerCreated().getId());
            if (volumeRef.get() != null && server != null) {
                Volume volume = volumeRef.get();
                if (volume.getStatus() == Volume.Status.AVAILABLE && server.getStatus() == Server.Status.ACTIVE && (server.getExtendedStatus().get() == null || server.getExtendedStatus().get().getTaskState() == null)) {
                    context.getApiCache()
                            .getVolumeAttachmentApi()
                            .attachVolumeToServerAsDevice(
                                    vmCreateContext.getVolume().getId(),
                                    vmCreateContext.getServerCreated().getId(), "");
                }
            }
        }
    }

    @Override
    public void rollback(MultiVmCreateContext context)
            throws OpenStackException {
    }

}
