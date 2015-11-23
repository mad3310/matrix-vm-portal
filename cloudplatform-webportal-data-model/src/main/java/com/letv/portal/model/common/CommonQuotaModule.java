package com.letv.portal.model.common;

import com.letv.portal.model.adminoplog.IntEnum;

/**
 * Created by zhouxianguang on 2015/11/10.
 */
public enum CommonQuotaModule implements IntEnum {
    CLOUDVM(1);

    private Integer code;

    CommonQuotaModule(Integer code) {
        this.code = code;
    }

    @Override
    public int toInt() {
        return code;
    }
}
