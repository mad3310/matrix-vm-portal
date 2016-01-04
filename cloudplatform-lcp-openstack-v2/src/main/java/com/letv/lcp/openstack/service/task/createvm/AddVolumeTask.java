package com.letv.lcp.openstack.service.task.createvm;

import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.service.base.OpenStackServiceGroup;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.util.ThreadUtil;
import com.letv.lcp.openstack.util.function.Function0;
import com.letv.portal.model.cloudvm.CloudvmVolume;

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
                        ThreadUtil.waiting(new Function0<Boolean>() {
                            @Override
                            public Boolean apply() throws Exception {
                                Volume volume = volumeApi.get(volumeId);
                                return !(volume == null || volume.getStatus() != Volume.Status.CREATING);
                            }
                        },500L);
                        Server server = serverApi.get(vmCreateContext.getServerCreated().getId());
                        Volume volume = volumeApi.get(vmCreateContext.getVolume().getId());
                        if (volume != null && server != null && volume.getStatus() == Volume.Status.AVAILABLE && server.getStatus() != Server.Status.ERROR) {
                            context.getApiCache()
                                    .getVolumeAttachmentApi()
                                    .attachVolumeToServerAsDevice(
                                            vmCreateContext.getVolume().getId(),
                                            vmCreateContext.getServerCreated().getId(), "");
                            ThreadUtil.waiting(new Function0<Boolean>() {
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
                                    ThreadUtil.waiting(new Function0<Boolean>() {
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
