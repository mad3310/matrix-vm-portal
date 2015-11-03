package com.letv.portal.service.openstack.local.resource;

import com.letv.portal.service.openstack.resource.VolumeAttachmentResource;

/**
 * Created by zhouxianguang on 2015/10/22.
 */
public class LocalVolumeAttachmentResource implements VolumeAttachmentResource {

    private String vmId;
    private String vmName;

    public LocalVolumeAttachmentResource(String vmId, String vmName) {
        this.vmId = vmId;
        this.vmName = vmName;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getVolumeId() {
        return null;
    }

    @Override
    public String getVmId() {
        return vmId;
    }

    @Override
    public String getVmName() {
        return vmName;
    }

    @Override
    public String getDevice() {
        return null;
    }

}
