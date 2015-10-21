package com.letv.portal.dao.cloudvm;

import com.letv.portal.dao.adminoplog.IntEnumTypeHandler;
import com.letv.portal.model.cloudvm.CloudvmVolumeType;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public class CloudvmVolumeTypeHandler extends IntEnumTypeHandler<CloudvmVolumeType> {
    public CloudvmVolumeTypeHandler() {
        super(CloudvmVolumeType.class);
    }
}
