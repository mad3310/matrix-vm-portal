package com.letv.portal.service.openstack.billing;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface FloatingIpCreateListener {
    void floatingIpCreated(String region, String floatingIpId, int floatingIpIndex, Object userData) throws Exception;
}
