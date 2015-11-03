package com.letv.portal.service.openstack.local.resource;

import com.letv.portal.model.cloudvm.CloudvmKeyPair;
import com.letv.portal.service.openstack.resource.KeyPairResource;

/**
 * Created by zhouxianguang on 2015/11/2.
 */
public class LocalKeyPairResource implements KeyPairResource {

    private CloudvmKeyPair keyPair;

    public LocalKeyPairResource(CloudvmKeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @Override
    public String getName() {
        return keyPair.getName();
    }

    @Override
    public String getFingerprint() {
        return keyPair.getFingerprint();
    }

    @Override
    public String getRegion() {
        return keyPair.getRegion();
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public Long getCreated() {
        return keyPair.getCreateTime().getTime();
    }
}
