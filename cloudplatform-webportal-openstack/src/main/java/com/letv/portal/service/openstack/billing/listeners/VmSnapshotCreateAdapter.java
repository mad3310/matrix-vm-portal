package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.VmSnapshotCreateEvent;
import com.letv.portal.service.openstack.billing.listeners.event.VmSnapshotCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public abstract class VmSnapshotCreateAdapter implements VmSnapshotCreateListener {
    @Override
    public void vmSnapshotCreated(VmSnapshotCreateEvent event) throws Exception {}

    @Override
    public void vmSnapshotCreateFailed(VmSnapshotCreateFailEvent event) throws Exception {}
}
