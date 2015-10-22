package com.letv.portal.service.openstack.local.resource;

import com.letv.portal.model.cloudvm.CloudvmServer;
import com.letv.portal.service.openstack.resource.*;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
public class LocalVmResource implements VMResource {

    private Region region;
    private CloudvmServer cloudvmServer;

    public LocalVmResource(Region region, CloudvmServer cloudvmServer) {
        this.region = region;
        this.cloudvmServer = cloudvmServer;
    }

    @Override
    public String getName() {
        return cloudvmServer.getName();
    }

    @Override
    public String getAccessIPv4() {
        return null;
    }

    @Override
    public String getAccessIPv6() {
        return null;
    }

    @Override
    public String getStatus() {
        return cloudvmServer.getStatus().name();
    }

    @Override
    public String getTaskState() {
        return cloudvmServer.getExtendedStatusTaskState();
    }

    @Override
    public Integer getPowerState() {
        return cloudvmServer.getExtendedPowerState();
    }

    @Override
    public String getVmState() {
        return cloudvmServer.getExtendedStatusVmState();
    }

    @Override
    public ImageResource getImage() {
        return null;
    }

    @Override
    public FlavorResource getFlavor() {
        return null;
    }

    @Override
    public IPAddresses getIpAddresses() {
        return null;
    }

    @Override
    public String getAvailabilityZone() {
        return null;
    }

    @Override
    public String getConfigDrive() {
        return null;
    }

    @Override
    public Long getCreated() {
        Date date = cloudvmServer.getCreateTime();
        if (date != null) {
            return date.getTime();
        } else {
            return null;
        }
    }

    @Override
    public Long getUpdated() {
        Date date = cloudvmServer.getUpdateTime();
        if (date != null) {
            return date.getTime();
        } else {
            return null;
        }
    }

    @Override
    public String getDiskConfig() {
        return null;
    }

    @Override
    public String getHostId() {
        return null;
    }

    @Override
    public String getKeyName() {
        return null;
    }

    @Override
    public String getRegionDisplayName() {
        return region.getName();
    }

    @Override
    public List<VolumeResource> getVolumes() {
        return null;
    }

    @Override
    public String getRegion() {
        return cloudvmServer.getRegion();
    }

    @Override
    public String getId() {
        return cloudvmServer.getServerId();
    }
}
