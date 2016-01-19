package com.letv.lcp.cloudvm.listener.adapter;

import com.letv.lcp.cloudvm.listener.VolumeCreateListener;
import com.letv.lcp.cloudvm.model.event.VolumeCreateEvent;
import com.letv.lcp.cloudvm.model.event.VolumeCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class VolumeCreateAdapter implements VolumeCreateListener{

    @Override
    public void volumeCreated(VolumeCreateEvent event) throws Exception {}

    @Override
    public void volumeCreateFailed(VolumeCreateFailEvent event) throws Exception {}
}
