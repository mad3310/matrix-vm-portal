package com.letv.portal.model.common;

import com.letv.portal.model.adminoplog.IntEnum;

/**
 * Created by zhouxianguang on 2015/11/10.
 */
public enum CommonQuotaType implements IntEnum {
    CLOUDVM_VM(1, 5L, CommonQuotaModule.CLOUDVM, "虚拟机数量", "个"),
    CLOUDVM_CPU(2, 10L, CommonQuotaModule.CLOUDVM, "CPU", "核"),
    CLOUDVM_MEMORY(3, 20L, CommonQuotaModule.CLOUDVM, "内存", "GB"),
    CLOUDVM_VOLUME(4, 20L, CommonQuotaModule.CLOUDVM, "云硬盘数量", "个"),
    CLOUDVM_VOLUME_SIZE(5, 500L, CommonQuotaModule.CLOUDVM, "云硬盘容量", "GB"),
    CLOUDVM_VM_SNAPSHOT(6, 10L, CommonQuotaModule.CLOUDVM, "虚拟机快照数量", "个"),
    CLOUDVM_VOLUME_SNAPSHOT(7, 10L, CommonQuotaModule.CLOUDVM, "云硬盘快照数量", "个"),
    CLOUDVM_NETWORK(8, 2L, CommonQuotaModule.CLOUDVM, "私有网络数量", "个"),
    CLOUDVM_SUBNET(9, 5L, CommonQuotaModule.CLOUDVM, "私有子网数量", "个"),
    CLOUDVM_ROUTER(10, 2L, CommonQuotaModule.CLOUDVM, "路由数量", "个"),
    CLOUDVM_FLOATING_IP(11, 2L, CommonQuotaModule.CLOUDVM, "公网IP数量", "个"),
    CLOUDVM_BAND_WIDTH(12, 20L, CommonQuotaModule.CLOUDVM, "带宽", "Mbps"),
    CLOUDVM_KEY_PAIR(13, 10L, CommonQuotaModule.CLOUDVM, "密钥数量", "个");

    private Integer code;
    private Long defaultQuota;
    private CommonQuotaModule module;
    private String name;
    private String unit;

    CommonQuotaType(Integer code, Long defaultQuota, CommonQuotaModule module, String name, String unit) {
        this.code = code;
        this.defaultQuota = defaultQuota;
        this.module = module;
        this.name = name;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    public Long getDefaultQuota() {
        return defaultQuota;
    }

    public CommonQuotaModule getModule() {
        return module;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public int toInt() {
        return code;
    }

}
