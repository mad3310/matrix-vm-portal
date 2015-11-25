package com.letv.portal.service.openstack.local.resource;

import com.letv.portal.model.cloudvm.CloudvmSubnetOption;

/**
 * Created by zhouxianguang on 2015/11/25.
 */
public class LocalSubnetOptionResource {
    private CloudvmSubnetOption cloudvmSubnetOption;

    public LocalSubnetOptionResource(CloudvmSubnetOption cloudvmSubnetOption) {
        this.cloudvmSubnetOption = cloudvmSubnetOption;
    }

    public String getCidr() {
        return cloudvmSubnetOption.getCidr();
    }

    public String getGatewayIp() {
        return cloudvmSubnetOption.getGatewayIp();
    }
}
