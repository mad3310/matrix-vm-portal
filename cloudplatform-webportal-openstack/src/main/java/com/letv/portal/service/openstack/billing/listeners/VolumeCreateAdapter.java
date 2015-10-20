package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.VolumeCreateEvent;
import com.letv.portal.service.openstack.billing.listeners.event.VolumeCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class VolumeCreateAdapter implements VolumeCreateListener{
    @Override
    public void volumeCreated(String region, String volumeId, int volumeIndex, Object userData) throws Exception {}

    @Override
    public void volumeCreated(VolumeCreateEvent event) throws Exception {}

    @Override
    public void volumeCreateFailed(VolumeCreateFailEvent event) throws Exception {}
}
