package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/10/29.
 */
public class CloudvmRcCount extends BaseModel {

    private static final long serialVersionUID = 2437609928884379927L;

    private String region;
    private CloudvmRcCountType type;
    private Long tenantId;
    private Long count;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public CloudvmRcCountType getType() {
        return type;
    }

    public void setType(CloudvmRcCountType type) {
        this.type = type;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
