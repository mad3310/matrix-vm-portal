package com.letv.lcp.cloudvm.listener;

import java.util.EventListener;

import com.letv.lcp.cloudvm.model.event.VolumeCreateEvent;
import com.letv.lcp.cloudvm.model.event.VolumeCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface VolumeCreateListener extends EventListener {
    void volumeCreated(VolumeCreateEvent event) throws Exception;
    void volumeCreateFailed(VolumeCreateFailEvent event) throws Exception;
}
