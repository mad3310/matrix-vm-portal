package com.letv.portal.model.common;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/11/10.
 */
public class CommonQuota extends BaseModel {
    private static final long serialVersionUID = -6366569558713596937L;

    private String region;
    private CommonQuotaModule module;
    private CommonQuotaType type;
    private Long value;
    private Long tenantId;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public CommonQuotaModule getModule() {
        return module;
    }

    public void setModule(CommonQuotaModule module) {
        this.module = module;
    }

    public CommonQuotaType getType() {
        return type;
    }

    public void setType(CommonQuotaType type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
