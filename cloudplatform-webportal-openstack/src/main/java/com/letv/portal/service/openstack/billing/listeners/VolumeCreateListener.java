package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.VolumeCreateEvent;
import com.letv.portal.service.openstack.billing.listeners.event.VolumeCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface VolumeCreateListener {
    void volumeCreated(String region, String volumeId, int volumeIndex, Object userData) throws Exception;
    void volumeCreated(VolumeCreateEvent event) throws Exception;
    void volumeCreateFailed(VolumeCreateFailEvent event) throws Exception;
}
