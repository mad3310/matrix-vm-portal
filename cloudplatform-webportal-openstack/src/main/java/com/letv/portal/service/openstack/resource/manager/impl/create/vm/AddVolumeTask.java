package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceGroup;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
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
                OpenStackServiceGroup openStackServiceGroup = OpenStackServiceImpl.getOpenStackServiceGroup();
                for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
                    boolean volumeUpdated = false;
                    if (vmCreateContext.getServerCreated() != null && vmCreateContext.getVolume() != null) {
                        context.getVolumeManager().waitingVolume(context.getApiCache().getVolumeApi(), vmCreateContext.getVolume().getId(), 500, new Checker<Volume>() {
                            @Override
                            public boolean check(Volume volume) throws Exception {
                                return !(volume == null || volume.getStatus() != Volume.Status.CREATING);
                            }
                        });
                        Server server = context.getApiCache().getServerApi().get(vmCreateContext.getServerCreated().getId());
                        Volume volume = context.getApiCache().getVolumeApi().get(vmCreateContext.getVolume().getId());
                        if (volume != null && server != null && volume.getStatus() == Volume.Status.AVAILABLE) {
                            context.getApiCache()
                                    .getVolumeAttachmentApi()
                                    .attachVolumeToServerAsDevice(
                                            vmCreateContext.getVolume().getId(),
                                            vmCreateContext.getServerCreated().getId(), "");
                            CloudvmVolume cloudvmVolume = openStackServiceGroup.getCloudvmVolumeService()
                                    .selectByVolumeId(context.getUserId(), context.getVmCreateConf().getRegion(), volume.getId());
                            openStackServiceGroup.getVolumeSyncService().syncStatus(cloudvmVolume, new Checker<Volume>() {
                                @Override
                                public boolean check(Volume volume) throws Exception {
                                    Volume.Status status = volume.getStatus();
                                    return (status == Volume.Status.IN_USE
                                            && volume.getAttachments().size() > 0)
                                            || status == Volume.Status.AVAILABLE
                                            || status == Volume.Status.ERROR;
                                }
                            });
                            volumeUpdated = true;
                        }
                    }
                    if (!volumeUpdated) {
                        if (vmCreateContext.getVolume() != null) {
                            Volume volume = context.getApiCache().getVolumeApi().get(vmCreateContext.getVolume().getId());
                            if (volume != null) {
                                CloudvmVolume cloudvmVolume = openStackServiceGroup.getCloudvmVolumeService()
                                        .selectByVolumeId(context.getUserId(), context.getVmCreateConf().getRegion(), volume.getId());
                                if (cloudvmVolume != null) {
                                    openStackServiceGroup.getVolumeSyncService().syncStatus(cloudvmVolume, new Checker<Volume>() {
                                        @Override
                                        public boolean check(Volume volume) throws Exception {
                                            Volume.Status status = volume.getStatus();
                                            return status != Volume.Status.ATTACHING && status != Volume.Status.CREATING;
                                        }
                                    });
                                }
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
