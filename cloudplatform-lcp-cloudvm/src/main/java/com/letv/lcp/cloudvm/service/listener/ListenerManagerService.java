package com.letv.lcp.cloudvm.service.listener;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.lcp.cloudvm.enumeration.ListenerTypeEnum;
import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.listener.RouterCreateListener;
import com.letv.lcp.cloudvm.listener.VmCreateListener;
import com.letv.lcp.cloudvm.listener.VolumeCreateListener;
import com.letv.lcp.cloudvm.model.event.FloatingIpCreateEvent;
import com.letv.lcp.cloudvm.model.event.RouterCreateEvent;
import com.letv.lcp.cloudvm.model.event.VmCreateEvent;
import com.letv.lcp.cloudvm.model.event.VolumeCreateEvent;

@Service("listenerManagerService")
public class ListenerManagerService {
	
	private static final Logger logger = LoggerFactory.getLogger(ListenerManagerService.class);
	
	private static Map<String, EventListener> listeners = new ConcurrentHashMap<String, EventListener>();
	
	public void addListener(String key, EventListener listener){ 
		listeners.put(key, listener);
    }
	
	public void removeListener(String key){ 
		listeners.remove(key);
    }
	
	/**
     * 创建成功通知Listener
     */
    public String notifyListenersCreated(String key, ListenerTypeEnum listenerType, EventObject event) {
    	try {
			if(listenerType.toInt() == ListenerTypeEnum.FloatingIpCreateListener.getCode()) {
				FloatingIpCreateListener listener = (FloatingIpCreateListener)listeners.get(key);
				listener.floatingIpCreated((FloatingIpCreateEvent)event);
			} else if(listenerType.toInt() == ListenerTypeEnum.RouterCreateListener.getCode()) {
				RouterCreateListener listener = (RouterCreateListener)listeners.get(key);
				listener.routerCreated((RouterCreateEvent)event);
			} else if(listenerType.toInt() == ListenerTypeEnum.VmCreateListener.getCode()) {
				VmCreateListener listener = (VmCreateListener)listeners.get(key);
				listener.vmCreated((VmCreateEvent)event);
			} else if(listenerType.toInt() == ListenerTypeEnum.VolumeCreateListener.getCode()) {
				VolumeCreateListener listener = (VolumeCreateListener)listeners.get(key);
				listener.volumeCreated((VolumeCreateEvent)event);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
	    	return e.getMessage();
		}
    	return "success";
    }
    
}
