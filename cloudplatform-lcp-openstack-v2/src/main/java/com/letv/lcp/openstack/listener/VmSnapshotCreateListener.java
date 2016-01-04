package com.letv.lcp.openstack.listener;

import com.letv.lcp.openstack.model.event.VmSnapshotCreateEvent;
import com.letv.lcp.openstack.model.event.VmSnapshotCreateFailEvent;


/**
 * Created by zhouxianguang on 2015/10/19.
 */
public interface VmSnapshotCreateListener {
    void vmSnapshotCreated(VmSnapshotCreateEvent event) throws Exception;
    void vmSnapshotCreateFailed(VmSnapshotCreateFailEvent event) throws Exception;
}
