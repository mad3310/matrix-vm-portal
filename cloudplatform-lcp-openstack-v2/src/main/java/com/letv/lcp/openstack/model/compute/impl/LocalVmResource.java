package com.letv.lcp.openstack.model.compute.impl;

import java.util.Date;
import java.util.List;

import com.letv.lcp.openstack.model.compute.FlavorResource;
import com.letv.lcp.openstack.model.compute.VMResource;
import com.letv.lcp.openstack.model.image.ImageResource;
import com.letv.lcp.openstack.model.network.IPAddresses;
import com.letv.lcp.openstack.model.region.Region;
import com.letv.lcp.openstack.model.storage.VolumeResource;
import com.letv.portal.model.cloudvm.CloudvmServer;


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
