package com.letv.portal.service.openstack.cronjobs;

import com.letv.common.exception.MatrixException;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.service.openstack.OpenStackTenant;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import org.jclouds.openstack.cinder.v1.domain.Volume;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/22.
 */
public interface VolumeSyncService {
    void sync(int recordsPerPage) throws MatrixException;

    void syncVolumeCreated(OpenStackTenant tenant, String region, List<Volume> cloudvmVolumes, Checker<Volume>
            checker);

    void syncStatus(List<CloudvmVolume> cloudvmVolumes, Checker<Volume>
            checker);

    void syncStatus(CloudvmVolume cloudvmVolume, Checker<Volume>
            checker);

    void syncStatusAfterServerDeleted(long tenantId, String region, String serverId);
}
