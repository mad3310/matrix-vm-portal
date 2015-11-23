package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.VmSnapshotCreateEvent;
import com.letv.portal.service.openstack.billing.listeners.event.VmSnapshotCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public interface VmSnapshotCreateListener {
    void vmSnapshotCreated(VmSnapshotCreateEvent event) throws Exception;
    void vmSnapshotCreateFailed(VmSnapshotCreateFailEvent event) throws Exception;
}
