package com.letv.lcp.openstack.service.task.check;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Quota;
import org.jclouds.openstack.nova.v2_0.domain.Server;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.UserOperationException;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.local.ILocalCommonQuotaSerivce;
import com.letv.portal.model.common.CommonQuotaType;

/**
 * Created by zhouxianguang on 2015/10/20.
 */
public class CheckNovaQuotaTask implements VmsCreateCheckSubTask {

    @Override
    public void run(MultiVmCreateCheckContext context) throws OpenStackException {
        final String region = context.getVmCreateConf().getRegion();

        int serverTotalCount = 0, serverTotalVcpus = 0, serverTotalRam = 0;
        List<Server> servers = context.getNovaApi().getServerApi(region)
                .listInDetail().concat().toList();
        Map<String, Flavor> idToFlavor = new HashMap<String, Flavor>();
        for (Server server : servers) {
            serverTotalCount++;
            String flavorId = server.getFlavor().getId();
            Flavor flavor = idToFlavor.get(flavorId);
            if (flavor == null) {
                flavor = context.getNovaApi().getFlavorApi(region).get(flavorId);
                idToFlavor.put(flavorId, flavor);
            }
            serverTotalVcpus += flavor.getVcpus();
            serverTotalRam += flavor.getRam();
        }

        ILocalCommonQuotaSerivce localCommonQuotaSerivce = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce();
        Long userId = context.getUserId();
        int count = context.getVmCreateConf().getCount();
        Flavor flavor = context.getFlavor();
        localCommonQuotaSerivce.checkQuota(userId, region, CommonQuotaType.CLOUDVM_VM, servers.size() + count);
        localCommonQuotaSerivce.checkQuota(userId, region, CommonQuotaType.CLOUDVM_CPU, serverTotalVcpus + count * flavor.getVcpus());
        localCommonQuotaSerivce.checkQuota(userId, region, CommonQuotaType.CLOUDVM_MEMORY, (serverTotalRam + count * flavor.getRam()) / 1024);

        Quota novaQuota = context
                .getNovaApi()
                .getQuotaApi(region).get()
                .getByTenant(
                        context.getVmManager().getOpenStackUser().getTenantId());
        if (novaQuota == null) {
            throw new OpenStackException("VM quota is not available.",
                    "虚拟机配额不可用。");
        }

        if (serverTotalCount + context.getVmCreateConf().getCount() > novaQuota
                .getInstances()) {
            throw new UserOperationException("VM count exceeding the quota.",
                    "虚拟机数量超过配额。");
        }

        if (serverTotalVcpus + context.getVmCreateConf().getCount()
                * context.getFlavor().getVcpus() > novaQuota.getCores()) {
            throw new UserOperationException("Vcpu count exceeding the quota.",
                    "虚拟CPU数量超过配额。");
        }

        if (serverTotalRam + context.getVmCreateConf().getCount()
                * context.getFlavor().getRam() > novaQuota.getRam()) {
            throw new UserOperationException(
                    "Ram amounts exceeding the quota.", "内存总量超过配额。");
        }
    }
}
