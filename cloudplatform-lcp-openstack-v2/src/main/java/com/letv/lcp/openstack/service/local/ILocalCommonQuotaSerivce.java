package com.letv.lcp.openstack.service.local;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.portal.model.common.CommonQuotaType;

/**
 * Created by zhouxianguang on 2015/11/11.
 */
public interface ILocalCommonQuotaSerivce {
    void checkQuota(long tenantId, String region, CommonQuotaType type, int value) throws OpenStackException;
    /**
      * @Title: addQuotaWithAuditUser
      * @Description: 审批完后增加该申请用户的配额（默认配额为0）
      * @param tenantId
      * @param region
      * @param type
      * @param value
      * @throws OpenStackException void   
      * @throws 
      * @author lisuxiao
      * @date 2016年2月22日 下午1:59:06
      */
    void addQuotaWithAuditUser(long tenantId, String region, CommonQuotaType type, Long value) throws OpenStackException;

//    void updateLocalCommonQuotaService(final long userVoUserId, final String osTenantId, NovaApi novaApi, NeutronApi neutronApi, CinderApi cinderApi) throws OpenStackException;
}
