package com.letv.portal.service.openstack.model.util;

import com.letv.portal.model.cloudvm.CloudvmServerAddress;
import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.nova.v2_0.domain.Address;

import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/29.
 */
public class CloudvmServerAddressUtil {
    public static boolean equal(CloudvmServerAddress localAddress, Map.Entry<String, Address> mapEntry) {
        String remoteAddressNetworkName = mapEntry.getKey();
        String remoteAddressAddr = mapEntry.getValue().getAddr();
        int remoteAddressVersion = mapEntry.getValue().getVersion();
        if (!StringUtils.equals(localAddress.getNetworkName(), remoteAddressNetworkName)) {
            return false;
        }
        if (!StringUtils.equals(localAddress.getAddr(), remoteAddressAddr)) {
            return false;
        }
        if (localAddress.getVersion() != remoteAddressVersion) {
            return false;
        }
        return true;
    }
}
