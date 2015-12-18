package com.letv.portal.service.common;

import com.letv.portal.model.common.CommonQuota;
import com.letv.portal.model.common.CommonQuotaModule;
import com.letv.portal.model.common.CommonQuotaType;

import java.util.List;
import java.util.Map;

public interface ICommonQuotaService extends IBaseService<CommonQuota> {
    List<CommonQuota> selectByRegion(long tenantId, String region);

    List<CommonQuota> selectByRegionAndModule(long tenantId, String region, CommonQuotaModule module);

    CommonQuota get(long tenantId, String region, CommonQuotaModule module, CommonQuotaType type);

    CommonQuota insertDefaultAndGet(long tenantId, String region, CommonQuotaModule module, CommonQuotaType type);

    List<CommonQuota> insertDefaultAndSelectByRegion(long tenantId, String region);

    Map<String,Long> insertDefaultAndSelectMapByRegion(long tenantId, String region);
}
