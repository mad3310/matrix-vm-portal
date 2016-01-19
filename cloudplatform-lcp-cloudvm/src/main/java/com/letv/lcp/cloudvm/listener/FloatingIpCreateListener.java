package com.letv.lcp.cloudvm.listener;

import java.util.EventListener;

import com.letv.lcp.cloudvm.model.event.FloatingIpCreateEvent;
import com.letv.lcp.cloudvm.model.event.FloatingIpCreateFailEvent;


/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface FloatingIpCreateListener extends EventListener{
	
    void floatingIpCreated(FloatingIpCreateEvent event) throws Exception;

    void floatingIpCreateFailed(FloatingIpCreateFailEvent event) throws Exception;
}
