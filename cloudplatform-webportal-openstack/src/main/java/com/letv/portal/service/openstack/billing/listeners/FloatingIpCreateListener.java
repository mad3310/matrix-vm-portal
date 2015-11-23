package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.FloatingIpCreateEvent;
import com.letv.portal.service.openstack.billing.listeners.event.FloatingIpCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface FloatingIpCreateListener {
    void floatingIpCreated(String region, String floatingIpId, int floatingIpIndex, Object userData) throws Exception;
    void floatingIpCreated(FloatingIpCreateEvent event) throws Exception;

    void floatingIpCreateFailed(FloatingIpCreateFailEvent event) throws Exception;
}
