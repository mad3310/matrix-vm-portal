package com.letv.lcp.openstack.listener.adapter;

import com.letv.lcp.openstack.listener.VmCreateListener;
import com.letv.lcp.openstack.model.event.VmCreateEvent;
import com.letv.lcp.openstack.model.event.VmCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class VmCreateAdapter implements VmCreateListener {
    @Override
    public void vmCreated(VmCreateEvent event) throws Exception {}

    @Override
    public void vmCreateFailed(VmCreateFailEvent event) throws Exception {}
}
