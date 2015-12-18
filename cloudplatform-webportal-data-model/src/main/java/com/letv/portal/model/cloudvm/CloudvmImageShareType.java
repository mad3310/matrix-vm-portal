package com.letv.portal.model.cloudvm;


import com.letv.portal.enumeration.IntEnum;

/**
 * Created by zhouxianguang on 2015/10/23.
 */
public enum CloudvmImageShareType implements IntEnum {
    PUBLIC(1, "公共的"), PRIVATE(2, "私有的");

    private Integer code;
    private String displayName;

    CloudvmImageShareType(Integer code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int toInt() {
        return code;
    }
}
