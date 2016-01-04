package com.letv.lcp.openstack.model.storage;

import com.letv.lcp.openstack.model.base.Resource;

/**
 * Created by zhouxianguang on 2015/10/12.
 */
public interface VolumeSnapshotResource extends Resource {
    String getName();
    String getVolumeName();
    String getVolumeId();
    String getStatus();
    Integer getSize();
    Long getCreated();
    String getDescription();
}
