package com.letv.lcp.openstack.service.task.check;

import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.Quota;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.UserOperationException;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.manage.impl.NetworkManagerImpl;
import com.letv.portal.model.common.CommonQuotaType;

/**
 * Created by zhouxianguang on 2015/10/20.
 */
public class CheckFloatingIpQuotaTask implements VmsCreateCheckSubTask {
    @Override
    public void run(MultiVmCreateCheckContext context) throws OpenStackException {
        if (!context.getVmCreateConf().getBindFloatingIp()) {
            return;
        }

        final String region = context.getVmCreateConf().getRegion();

        int floatingIpTotalCount = 0, floatingIpTotalBandWidth = 0;
        for (FloatingIP floatingIP : context.getNeutronApi()
                .getFloatingIPApi(region).get().list().concat().toList()) {
            if (!StringUtils.equals(floatingIP.getFixedIpAddress(),
                    floatingIP.getFloatingIpAddress())) {
                floatingIpTotalBandWidth += NetworkManagerImpl
                        .getBandWidth(floatingIP.getFipQos());
                floatingIpTotalCount++;
            }
        }

        Quota quota = context
                .getNeutronApi()
                .getQuotaApi(region).get()
                .getByTenant(
                        context.getVmManager().getOpenStackUser().getTenantId());
        if (quota == null) {
            throw new OpenStackException("Floating IP quota is not available.",
                    "公网IP配额不可用。");
        }
        OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
                .checkQuota(context.getUserId(), context.getVmCreateConf().getRegion(), CommonQuotaType.CLOUDVM_FLOATING_IP, floatingIpTotalCount + context.getVmCreateConf().getCount());
        if (floatingIpTotalCount + context.getVmCreateConf().getCount() > quota
                .getFloatingIp()) {
            throw new UserOperationException(
                    "Floating IP count exceeding the quota.", "公网IP数量超过配额。");
        }
        OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
                .checkQuota(context.getUserId(), context.getVmCreateConf().getRegion(), CommonQuotaType.CLOUDVM_BAND_WIDTH, floatingIpTotalBandWidth + context.getVmCreateConf().getCount()
                        * context.getVmCreateConf().getBandWidth());
        if (floatingIpTotalBandWidth + context.getVmCreateConf().getCount()
                * context.getVmCreateConf().getBandWidth() > quota
                .getBandWidth()) {
            throw new UserOperationException(
                    "Floating IP band width exceeding the quota.",
                    "公网IP带宽超过配额。");
        }
    }
}
