package com.letv.portal.service.openstack.billing;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface RouterCreateListener {
    void routerCreated(String region, String routerId, Object userData) throws Exception;
}
