package com.letv.portal.model.cloudvm;

import com.letv.portal.model.adminoplog.IntEnum;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public enum CloudvmVolumeType implements IntEnum {
    SAS(1, "709c9914-7056-4211-a03c-a41b056f3e8c", "性能型"), SATA(2, "d3e50993-e9a7-415b-bbb7-f0b2e4e7dc50", "容量型"), SSD(3, "9f12c2c2-4db4-40ae-b1ef-0f44c2f3c7e8", "高性能型");

    private Integer code;
    private String volumeTypeId;
    private String displayName;

    private CloudvmVolumeType(Integer code, String volumeTypeId, String displayName) {
        this.code = code;
        this.volumeTypeId = volumeTypeId;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getCode() {
        return code;
    }

    public String getVolumeTypeId() {
        return volumeTypeId;
    }

    @Override
    public int toInt() {
        return code;
    }
}
