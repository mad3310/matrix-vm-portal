package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.RouterCreateEvent;
import com.letv.portal.service.openstack.billing.listeners.event.RouterCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface RouterCreateListener {
    void routerCreated(String region, String routerId, int routerIndex, Object userData) throws Exception;

    void routerCreated(RouterCreateEvent event) throws Exception;

    void routerCreateFailed(RouterCreateFailEvent event) throws Exception;
}
