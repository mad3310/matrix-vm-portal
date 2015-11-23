package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceGroup;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import com.letv.portal.service.openstack.util.ThreadUtil;
import com.letv.portal.service.openstack.util.Timeout;
import com.letv.portal.service.openstack.util.function.Function;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;

import java.util.concurrent.TimeUnit;

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
                final VolumeApi volumeApi = context.getApiCache().getVolumeApi();
                final ServerApi serverApi = context.getApiCache().getServerApi();
                for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
                    boolean volumeUpdated = false;
                    if (vmCreateContext.getServerCreated() != null && vmCreateContext.getVolume() != null) {
                        final String volumeId = vmCreateContext.getVolume().getId();
                        ThreadUtil.waiting(new Function<Boolean>() {
                            @Override
                            public Boolean apply() throws Exception {
                                Volume volume = volumeApi.get(volumeId);
                                return !(volume == null || volume.getStatus() != Volume.Status.CREATING);
                            }
                        },500L);
                        Server server = serverApi.get(vmCreateContext.getServerCreated().getId());
                        Volume volume = volumeApi.get(vmCreateContext.getVolume().getId());
                        if (volume != null && server != null && volume.getStatus() == Volume.Status.AVAILABLE) {
                            context.getApiCache()
                                    .getVolumeAttachmentApi()
                                    .attachVolumeToServerAsDevice(
                                            vmCreateContext.getVolume().getId(),
                                            vmCreateContext.getServerCreated().getId(), "");
                            ThreadUtil.waiting(new Function<Boolean>() {
                                @Override
                                public Boolean apply() throws Exception {
                                    Volume volume = volumeApi.get(volumeId);
                                    if(volume == null){
                                        return false;
                                    }
                                    Volume.Status status = volume.getStatus();
                                    return !((status == Volume.Status.IN_USE
                                            && !volume.getAttachments().isEmpty())
                                            || status == Volume.Status.AVAILABLE
                                            || status == Volume.Status.ERROR);
                                }
                            },500L);
                            openStackServiceGroup.getLocalVolumeService().update(context.getUserId(),context.getUserId(),context.getVmCreateConf().getRegion(),volumeApi.get(volumeId));
                            volumeUpdated = true;
                        }
                    }
                    if (!volumeUpdated) {
                        if (vmCreateContext.getVolume() != null) {
                            Volume volume = volumeApi.get(vmCreateContext.getVolume().getId());
                            if (volume != null) {
                                CloudvmVolume cloudvmVolume = openStackServiceGroup.getCloudvmVolumeService()
                                        .selectByVolumeId(context.getUserId(), context.getVmCreateConf().getRegion(), volume.getId());
                                if (cloudvmVolume != null) {
                                    final String volumeId = vmCreateContext.getVolume().getId();
                                    ThreadUtil.waiting(new Function<Boolean>() {
                                        @Override
                                        public Boolean apply() throws Exception {
                                            Volume volume = volumeApi.get(volumeId);
                                            if(volume == null){
                                                return false;
                                            }
                                            Volume.Status status = volume.getStatus();
                                            return status == Volume.Status.ATTACHING || status == Volume.Status.CREATING;
                                        }
                                    },500L);
                                    openStackServiceGroup.getLocalVolumeService().update(context.getUserId(),context.getUserId(),context.getVmCreateConf().getRegion(),volumeApi.get(volumeId));
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
