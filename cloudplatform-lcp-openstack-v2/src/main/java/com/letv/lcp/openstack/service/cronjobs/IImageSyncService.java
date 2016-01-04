package com.letv.lcp.openstack.service.cronjobs;

import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;

import com.letv.common.exception.MatrixException;
import com.letv.lcp.openstack.service.manage.check.Checker;
import com.letv.portal.model.cloudvm.CloudvmImage;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface IImageSyncService {
    void sync(int recordsPerPage) throws MatrixException;

    void syncStatus(CloudvmImage cloudvmImage, Checker<ImageDetails>
            checker);

    void cleanServerIdAfterServerDeleted(long tenantId, String region, String serverId);
}
