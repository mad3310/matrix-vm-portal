package com.letv.portal.model.cloudvm;

import com.letv.portal.enumeration.IntEnum;

/**
 * Created by zhouxianguang on 2015/10/23.
 */
public enum CloudvmImageStatus implements IntEnum{
    NIL(0), UNRECOGNIZED(1), ACTIVE(2), SAVING(3), QUEUED(4), KILLED(5), PENDING_DELETE(6)
    , DELETED(7);

    private Integer code;

    CloudvmImageStatus(Integer code) {
        this.code = code;
    }

    @Override
    public int toInt() {
        return code;
    }
}
