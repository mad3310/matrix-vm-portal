package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceGroup;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import com.letv.portal.service.openstack.util.Ref;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.nova.v2_0.domain.Server;

public class AddVolumeTask extends VmsCreateSubTask {

    @Override
    boolean isEnable(MultiVmCreateContext context) {
        return context.getVmCreateConf().getVolumeSize() > 0;
    }

    @Override
    public void run(MultiVmCreateContext context) throws OpenStackException {
        if (isEnable(context)) {
            if (context.getVmCreateContexts() != null) {
                for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
                    if (vmCreateContext.getServerCreated() != null && vmCreateContext.getVolume() != null) {
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
                            if (volume.getStatus() == Volume.Status.AVAILABLE) {
                                OpenStackServiceGroup openStackServiceGroup = OpenStackServiceImpl.getOpenStackServiceGroup();
                                CloudvmVolume cloudvmVolume = openStackServiceGroup.getCloudvmVolumeService()
                                        .selectByVolumeId(context.getUserId(), context.getVmCreateConf().getRegion(), volume.getId());
//                                cloudvmVolume.setStatus(CloudvmVolumeStatus.WAITING_ATTACHING);
//                                openStackServiceGroup.getCloudvmVolumeService().update(cloudvmVolume);
                                context.getApiCache()
                                        .getVolumeAttachmentApi()
                                        .attachVolumeToServerAsDevice(
                                                vmCreateContext.getVolume().getId(),
                                                vmCreateContext.getServerCreated().getId(), "");
                                openStackServiceGroup.getVolumeSyncService().syncStatus(cloudvmVolume, new Checker<Volume>() {
                                    @Override
                                    public boolean check(Volume volume) throws Exception {
                                        return volume.getStatus() != Volume.Status.ATTACHING;
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void rollback(MultiVmCreateContext context)
            throws OpenStackException {
    }

    @Override
    boolean needContinueAfterException() {
        return true;
    }
}
