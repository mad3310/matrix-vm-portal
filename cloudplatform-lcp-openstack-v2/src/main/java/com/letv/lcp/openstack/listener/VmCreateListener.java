package com.letv.lcp.openstack.listener;

import com.letv.lcp.openstack.model.event.VmCreateEvent;
import com.letv.lcp.openstack.model.event.VmCreateFailEvent;



/**
 * Created by zhouxianguang on 2015/9/22.
 */
public interface VmCreateListener {
    void vmCreated(VmCreateEvent event) throws Exception;
    void vmCreateFailed(VmCreateFailEvent event) throws Exception;
}
