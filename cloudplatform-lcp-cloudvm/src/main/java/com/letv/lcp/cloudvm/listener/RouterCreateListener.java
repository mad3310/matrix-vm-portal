package com.letv.lcp.cloudvm.listener;

import java.util.EventListener;

import com.letv.lcp.cloudvm.model.event.RouterCreateEvent;
import com.letv.lcp.cloudvm.model.event.RouterCreateFailEvent;


/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface RouterCreateListener extends EventListener{

    void routerCreated(RouterCreateEvent event) throws Exception;

    void routerCreateFailed(RouterCreateFailEvent event) throws Exception;
}
