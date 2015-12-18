package com.letv.portal.model.cloudvm;

import com.letv.portal.enumeration.IntEnum;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public enum CloudvmVolumeStatus implements IntEnum {
    CREATING(1), AVAILABLE(2), ATTACHING(3), IN_USE(4), DELETING(5), ERROR(6), ERROR_DELETING(7),
    UNRECOGNIZED(8), NIL(0), WAITING_ATTACHING(9), DETTACHING(10);

    private Integer code;

    private CloudvmVolumeStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public int toInt() {
        return code;
    }

    public static CloudvmVolumeStatus[] matrixStatus = {NIL, DETTACHING};
}
