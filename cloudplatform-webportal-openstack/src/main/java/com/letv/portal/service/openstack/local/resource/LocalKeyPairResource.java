package com.letv.portal.service.openstack.local.resource;

import com.letv.portal.service.openstack.resource.KeyPairResource;
import com.letv.portal.service.openstack.resource.impl.AbstractResource;

import java.util.Date;

/**
 * Created by zhouxianguang on 2015/11/2.
 */
public class LocalKeyPairResource extends AbstractResource implements KeyPairResource {

    @Override
    public String getName() {
        return "test1";
    }

    @Override
    public String getFingerprint() {
        return "28:75:7f:3e:f8:b7:c5:d5:09:b3:b7:22:76:59:e1:b7";
    }

    @Override
    public String getRegion() {
        return "cn-beijing-1";
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public Long getCreated() {
        return new Date().getTime();
    }
}
