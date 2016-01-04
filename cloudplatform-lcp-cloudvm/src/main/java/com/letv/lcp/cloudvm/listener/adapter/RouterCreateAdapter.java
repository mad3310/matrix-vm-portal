package com.letv.lcp.cloudvm.listener.adapter;

import com.letv.lcp.cloudvm.listener.RouterCreateListener;
import com.letv.lcp.cloudvm.model.event.RouterCreateEvent;
import com.letv.lcp.cloudvm.model.event.RouterCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class RouterCreateAdapter implements RouterCreateListener {
    @Override
    public void routerCreated(String region, String routerId, int routerIndex, Object userData) throws Exception {}

    @Override
    public void routerCreated(RouterCreateEvent event) throws Exception{}

    @Override
    public void routerCreateFailed(RouterCreateFailEvent event) throws Exception {}
}
