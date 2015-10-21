package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
public class CloudvmTenant extends BaseModel {
    private static final long serialVersionUID = 8191759114814924296L;

    private Long tenantId;
    private String osTenantId;
    private String osTenantName;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getOsTenantId() {
        return osTenantId;
    }

    public void setOsTenantId(String osTenantId) {
        this.osTenantId = osTenantId;
    }

    public String getOsTenantName() {
        return osTenantName;
    }

    public void setOsTenantName(String osTenantName) {
        this.osTenantName = osTenantName;
    }
}
