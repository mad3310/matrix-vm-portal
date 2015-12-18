package com.letv.portal.dao.cloudvm;

import com.letv.portal.enumeration.IntEnumTypeHandler;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public class CloudvmVolumeStatusHandler extends IntEnumTypeHandler<CloudvmVolumeStatus> {
    public CloudvmVolumeStatusHandler() {
        super(CloudvmVolumeStatus.class);
    }
}
