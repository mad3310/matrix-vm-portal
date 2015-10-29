package com.letv.portal.model.cloudvm;

import com.letv.portal.model.adminoplog.IntEnum;

/**
 * Created by zhouxianguang on 2015/10/29.
 */
public enum CloudvmRcCountType implements IntEnum {
    SERVER(1), FLOATING_IP(2), ROUTER(3);

    private Integer code;

    CloudvmRcCountType(Integer code) {
        this.code = code;
    }

    @Override
    public int toInt() {
        return code;
    }
}
