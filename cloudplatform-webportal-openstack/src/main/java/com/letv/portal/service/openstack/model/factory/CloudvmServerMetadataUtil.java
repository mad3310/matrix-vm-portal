package com.letv.portal.service.openstack.model.factory;

import com.letv.portal.model.cloudvm.CloudvmServerMetadata;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/29.
 */
public class CloudvmServerMetadataUtil {
    public static boolean equal(CloudvmServerMetadata localMetadata, Map.Entry<String, String> mapEntry) {
        String remoteMetadataKey = mapEntry.getKey();
        String remoteMetadataValue = mapEntry.getValue();
        if (!StringUtils.equals(localMetadata.getKey(), remoteMetadataKey)) {
            return false;
        }
        if (!StringUtils.equals(localMetadata.getValue(), remoteMetadataValue)) {
            return false;
        }
        return true;
    }
}
