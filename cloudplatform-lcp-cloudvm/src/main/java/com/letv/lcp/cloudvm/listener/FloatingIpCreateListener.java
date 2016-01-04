package com.letv.lcp.cloudvm.listener;

import com.letv.lcp.cloudvm.model.event.FloatingIpCreateEvent;
import com.letv.lcp.cloudvm.model.event.FloatingIpCreateFailEvent;


/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface FloatingIpCreateListener {
    void floatingIpCreated(String region, String floatingIpId, int floatingIpIndex, Object userData) throws Exception;
    void floatingIpCreated(FloatingIpCreateEvent event) throws Exception;

    void floatingIpCreateFailed(FloatingIpCreateFailEvent event) throws Exception;
}
