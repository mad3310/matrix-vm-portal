package com.letv.lcp.openstack.service.event.impl;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.letv.lcp.openstack.model.billing.ResourceLocator;
import com.letv.lcp.openstack.model.event.ResourceDeleteEvent;
import com.letv.lcp.openstack.service.event.IEventPublishService;
import com.letv.lcp.openstack.util.ExceptionUtil;

/**
 * Created by zhouxianguang on 2015/11/6.
 */
@Service
public class EventPublishServiceImpl implements IEventPublishService, ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    private void publishEvent(ApplicationEvent event) {
        try {
            applicationEventPublisher.publishEvent(event);
        } catch (Exception ex) {
            ExceptionUtil.logAndEmail(ex);
        }
    }

    @Override
    public void onDelete(ResourceLocator locator) {
        publishEvent(new ResourceDeleteEvent(this).locator(locator));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
