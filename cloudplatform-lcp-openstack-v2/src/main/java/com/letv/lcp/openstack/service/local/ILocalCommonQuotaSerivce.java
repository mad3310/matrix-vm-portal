package com.letv.lcp.openstack.service.local;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.portal.model.common.CommonQuotaType;

/**
 * Created by zhouxianguang on 2015/11/11.
 */
public interface ILocalCommonQuotaSerivce {
    void checkQuota(long tenantId, String region, CommonQuotaType type, int value) throws OpenStackException;

//    void updateLocalCommonQuotaService(final long userVoUserId, final String osTenantId, NovaApi novaApi, NeutronApi neutronApi, CinderApi cinderApi) throws OpenStackException;
}
