package com.letv.portal.service.openstack.billing;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface VolumeCreateListener {
    void volumeCreated(String region, String volumeId, int volumeIndex, Object userData) throws Exception;
}
