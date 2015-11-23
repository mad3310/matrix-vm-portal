package com.letv.portal.service.openstack.cronjobs;

import com.letv.common.exception.MatrixException;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface ImageSyncService {
    void sync(int recordsPerPage) throws MatrixException;

    void syncStatus(CloudvmImage cloudvmImage, Checker<ImageDetails>
            checker);

    void cleanServerIdAfterServerDeleted(long tenantId, String region, String serverId);
}
