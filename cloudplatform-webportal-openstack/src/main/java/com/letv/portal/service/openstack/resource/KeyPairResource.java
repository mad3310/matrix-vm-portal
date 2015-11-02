package com.letv.portal.service.openstack.resource;

/**
 * Created by zhouxianguang on 2015/11/2.
 */
public interface KeyPairResource extends Resource {
    String getName();

    String getFingerprint();

    Long getCreated();
}
