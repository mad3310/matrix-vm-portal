package com.letv.portal.model.cloudvm;

import com.letv.portal.model.adminoplog.IntEnum;

/**
 * Created by zhouxianguang on 2015/10/23.
 */
public enum CloudvmImageType implements IntEnum {
    IMAGE(1), SNAPSHOT(2);

    private Integer code;

    CloudvmImageType(Integer code) {
        this.code = code;
    }

    @Override
    public int toInt() {
        return code;
    }
}
