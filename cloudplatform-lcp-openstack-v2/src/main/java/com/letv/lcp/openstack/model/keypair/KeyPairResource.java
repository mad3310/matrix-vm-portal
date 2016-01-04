package com.letv.lcp.openstack.model.keypair;

import com.letv.lcp.openstack.model.base.Resource;

/**
 * Created by zhouxianguang on 2015/11/2.
 */
public interface KeyPairResource extends Resource {
    String getName();

    String getFingerprint();

    Long getCreated();
}
