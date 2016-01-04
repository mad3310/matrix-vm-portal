package com.letv.lcp.openstack.listener.adapter;

import com.letv.lcp.openstack.listener.VmSnapshotCreateListener;
import com.letv.lcp.openstack.model.event.VmSnapshotCreateEvent;
import com.letv.lcp.openstack.model.event.VmSnapshotCreateFailEvent;


/**
 * Created by zhouxianguang on 2015/10/19.
 */
public abstract class VmSnapshotCreateAdapter implements VmSnapshotCreateListener {
    @Override
    public void vmSnapshotCreated(VmSnapshotCreateEvent event) throws Exception {}

    @Override
    public void vmSnapshotCreateFailed(VmSnapshotCreateFailEvent event) throws Exception {}
}
