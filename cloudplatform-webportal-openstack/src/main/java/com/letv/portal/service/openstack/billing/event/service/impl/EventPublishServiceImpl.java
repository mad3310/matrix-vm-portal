package com.letv.portal.service.openstack.billing.event.service.impl;

import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.billing.event.ResourceDeleteEvent;
import com.letv.portal.service.openstack.billing.event.service.EventPublishService;
import com.letv.portal.service.openstack.util.ExceptionUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * Created by zhouxianguang on 2015/11/6.
 */
@Service
public class EventPublishServiceImpl implements EventPublishService, ApplicationEventPublisherAware {

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
