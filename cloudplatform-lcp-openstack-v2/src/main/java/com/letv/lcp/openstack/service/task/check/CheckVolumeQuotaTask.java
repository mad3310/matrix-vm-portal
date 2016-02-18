package com.letv.lcp.openstack.service.task.check;

import java.util.List;

import org.jclouds.openstack.cinder.v1.domain.Snapshot;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.domain.VolumeQuota;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.UserOperationException;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.local.ILocalCommonQuotaSerivce;
import com.letv.lcp.openstack.service.manage.impl.VolumeManagerImpl;
import com.letv.portal.model.common.CommonQuotaType;

/**
 * Created by zhouxianguang on 2015/10/20.
 */
public class CheckVolumeQuotaTask implements VmsCreateCheckSubTask {
    @Override
    public void run(MultiVmCreateCheckContext context) throws OpenStackException {
        if (context.getVmCreateConf().getVolumeSize() == 0) {
            return;
        }

        final String region = context.getVmCreateConf().getRegion();

        List<? extends Volume> volumes = context.getCinderApi().getVolumeApi(region).list().toList();
        List<? extends Snapshot> snapshots = context.getCinderApi().getSnapshotApi(region).list().toList();
        int pureVolumeSize = 0;
        for (Volume volume : volumes) {
            pureVolumeSize += volume.getSize();
        }

        long tenantId = context.getUserId();
        int count = context.getVmCreateConf().getCount();
        ILocalCommonQuotaSerivce localCommonQuotaSerivce = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce();
        localCommonQuotaSerivce.checkQuota(tenantId, region, CommonQuotaType.CLOUDVM_VOLUME, volumes.size() + count);
        localCommonQuotaSerivce.checkQuota(tenantId, region, CommonQuotaType.CLOUDVM_VOLUME_SIZE, pureVolumeSize + count * context.getVmCreateConf().getVolumeSize());

        VolumeQuota quota = context
                .getCinderApi()
                .getQuotaApi(region)
                .getByTenant(
                        context.getVmManager().getOpenStackUser().getOpenStackTenantId());
        if (quota == null) {
            throw new OpenStackException("Cinder quota is not available.",
                    "云硬盘的配额不可用。");
        }
        if (volumes.size() + context.getVmCreateConf().getCount() > quota
                .getVolumes()) {
            throw new UserOperationException(
                    "Volume count exceeding the quota.", "云硬盘数量超过配额。");
        }
        if (VolumeManagerImpl.sumGigabytes(volumes, snapshots) + context.getVmCreateConf().getCount()
                * context.getVmCreateConf().getVolumeSize() > quota
                .getGigabytes()) {
            throw new UserOperationException(
                    "Volumes size exceeding the quota.", "云硬盘总大小超过配额。");
        }
    }
}
