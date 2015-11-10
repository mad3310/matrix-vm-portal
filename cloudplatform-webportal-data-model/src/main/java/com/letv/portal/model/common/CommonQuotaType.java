package com.letv.portal.model.common;

import com.letv.portal.model.adminoplog.IntEnum;

/**
 * Created by zhouxianguang on 2015/11/10.
 */
public enum CommonQuotaType implements IntEnum {
    CLOUDVM_VM(1, 5L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_CPU(2, 10L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_MEMORY(3, 20L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_VOLUME(4, 20L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_VOLUME_SIZE(5, 500L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_VM_SNAPSHOT(6, 10L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_VOLUME_SNAPSHOT(7, 10L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_NETWORK(8, 2L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_SUBNET(9, 5L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_ROUTER(10, 2L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_FLOATING_IP(11, 2L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_BAND_WIDTH(12, 20L, CommonQuotaModule.CLOUDVM),
    CLOUDVM_KEY_PAIR(13, 10L, CommonQuotaModule.CLOUDVM);

    private Integer code;
    private Long defaultQuota;
    private CommonQuotaModule module;

    CommonQuotaType(Integer code, Long defaultQuota, CommonQuotaModule module) {
        this.code = code;
        this.defaultQuota = defaultQuota;
        this.module = module;
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
