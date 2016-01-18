package com.letv.lcp.cloudvm.listener.adapter;

import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.model.event.FloatingIpCreateEvent;
import com.letv.lcp.cloudvm.model.event.FloatingIpCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class FloatingIpCreateAdapter implements FloatingIpCreateListener {

    @Override
    public void floatingIpCreated(FloatingIpCreateEvent event) throws Exception {}

    @Override
    public void floatingIpCreateFailed(FloatingIpCreateFailEvent event) throws Exception {}
}
