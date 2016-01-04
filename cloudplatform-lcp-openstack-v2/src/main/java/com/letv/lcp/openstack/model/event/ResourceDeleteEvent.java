package com.letv.lcp.openstack.model.event;

import org.springframework.context.ApplicationEvent;

import com.letv.lcp.openstack.model.billing.ResourceLocator;

/**
 * Created by zhouxianguang on 2015/11/5.
 */
public class ResourceDeleteEvent extends ApplicationEvent {

    private static final long serialVersionUID = -5659158202452222303L;

    private ResourceLocator locator;

    public ResourceDeleteEvent(Object source) {
        super(source);
    }

    public ResourceLocator getLocator() {
        return locator;
    }

    public void setLocator(ResourceLocator locator) {
        this.locator = locator;
    }

    public ResourceLocator locator() {
        return this.locator;
    }

    public ResourceDeleteEvent locator(final ResourceLocator locator) {
        this.locator = locator;
        return this;
    }

}
