package com.letv.portal.service.openstack.local.service;

import com.letv.portal.model.common.CommonQuotaType;
import com.letv.portal.service.openstack.exception.OpenStackException;

/**
 * Created by zhouxianguang on 2015/11/11.
 */
public interface LocalCommonQuotaSerivce {
    void checkQuota(long tenantId, String region, CommonQuotaType type, int value) throws OpenStackException;

//    void updateLocalCommonQuotaService(final long userVoUserId, final String osTenantId, NovaApi novaApi, NeutronApi neutronApi, CinderApi cinderApi) throws OpenStackException;
}
