package com.letv.portal.model.cloudvm;

import com.letv.portal.model.adminoplog.IntEnum;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public enum CloudvmVolumeType implements IntEnum {
    SAS(1, "709c9914-7056-4211-a03c-a41b056f3e8c"), SATA(2, "d3e50993-e9a7-415b-bbb7-f0b2e4e7dc50"), SSD(3, "9f12c2c2-4db4-40ae-b1ef-0f44c2f3c7e8");

    private Integer code;
    private String volumeTypeId;

    private CloudvmVolumeType(Integer code, String volumeTypeId) {
        this.code = code;
        this.volumeTypeId = volumeTypeId;
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
