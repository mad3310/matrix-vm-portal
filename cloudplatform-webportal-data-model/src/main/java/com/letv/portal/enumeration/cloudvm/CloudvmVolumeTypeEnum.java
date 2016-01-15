package com.letv.portal.enumeration.cloudvm;

import com.letv.portal.enumeration.IntEnum;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public enum CloudvmVolumeTypeEnum implements IntEnum {
    SATA(2, "d3e50993-e9a7-415b-bbb7-f0b2e4e7dc50", "容量型", 1L, 1L, true, 2000L),
    SAS(1, "709c9914-7056-4211-a03c-a41b056f3e8c", "性能型", 1L, 1L, false, 2000L),
    SSD(3, "9f12c2c2-4db4-40ae-b1ef-0f44c2f3c7e8", "高性能型", 1L, 1L, false, 2000L);

    private Integer code;
    private String volumeTypeId;
    private String displayName;
    private Long throughput;
    private Long iops;
    private Boolean enable;
    private Long capacity;

    CloudvmVolumeTypeEnum(Integer code, String volumeTypeId, String displayName, Long throughput, Long iops, Boolean enable, Long capacity) {
        this.code = code;
        this.volumeTypeId = volumeTypeId;
        this.displayName = displayName;
        this.throughput = throughput;
        this.iops = iops;
        this.enable = enable;
        this.capacity = capacity;
    }
    
    public static CloudvmVolumeTypeEnum fromValue(String id) {
        if (id != null) {
           for (CloudvmVolumeTypeEnum value : CloudvmVolumeTypeEnum.values()) {
             if (id.equalsIgnoreCase(value.getVolumeTypeId())) {
               return value;
             }
           }
           return null;
         }
         return null;
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

    public Long getThroughput() {
        return throughput;
    }

    public Long getIops() {
        return iops;
    }

    public Boolean getEnable() {
        return enable;
    }

    public Long getCapacity() {
        return capacity;
    }

    @Override
    public int toInt() {
        return code;
    }
}
