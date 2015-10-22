package com.letv.portal.model.cloudvm;

import com.letv.portal.model.adminoplog.IntEnum;

/**
 * Created by zhouxianguang on 2015/10/22.
 */
public enum CloudvmServerStatus implements IntEnum {
    ACTIVE(1), BUILD(2), REBUILD(3), SUSPENDED(4), PAUSED(5), RESIZE(6), VERIFY_RESIZE(7),
    REVERT_RESIZE(8), PASSWORD(9), REBOOT(10), HARD_REBOOT(11), DELETED(12), UNKNOWN(13),
    ERROR(14), STOPPED(15), UNRECOGNIZED(16), MIGRATING(17), SHUTOFF(18), RESCUE(19),
    SOFT_DELETED(20), SHELVED(21), SHELVED_OFFLOADED(22), NIL(0);

    private Integer code;

    private CloudvmServerStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public int toInt() {
        return code;
    }
}
