package com.letv.lcp.openstack.service.cronjobs;

import java.util.List;

import org.jclouds.openstack.cinder.v1.domain.Volume;

import com.letv.common.exception.MatrixException;
import com.letv.lcp.openstack.model.user.OpenStackTenant;
import com.letv.lcp.openstack.service.manage.check.Checker;
import com.letv.portal.model.cloudvm.CloudvmVolume;

/**
 * Created by zhouxianguang on 2015/10/22.
 */
public interface IVolumeSyncService {
    void sync(int recordsPerPage) throws MatrixException;

    void syncVolumeCreated(OpenStackTenant tenant, String region, List<Volume> cloudvmVolumes, Checker<Volume>
            checker);

    void syncStatus(List<CloudvmVolume> cloudvmVolumes, Checker<Volume>
            checker);

    void syncStatus(CloudvmVolume cloudvmVolume, Checker<Volume>
            checker);

    void syncStatusAfterServerDeleted(long tenantId, String region, String serverId);
}
