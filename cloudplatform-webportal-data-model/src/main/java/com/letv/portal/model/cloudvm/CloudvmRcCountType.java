package com.letv.portal.model.cloudvm;

import com.letv.portal.enumeration.IntEnum;

/**
 * Created by zhouxianguang on 2015/10/29.
 */
public enum CloudvmRcCountType implements IntEnum {
    SERVER(1), FLOATING_IP(2), ROUTER(3), CPU(4), MEMORY(5)
    , BAND_WIDTH(6), VOLUME_SNAPSHOT(7), PRIVATE_NETWORK(8)
    , PRIVATE_SUBNET(9);

    private Integer code;

    CloudvmRcCountType(Integer code) {
        this.code = code;
    }

    @Override
    public int toInt() {
        return code;
    }
}
