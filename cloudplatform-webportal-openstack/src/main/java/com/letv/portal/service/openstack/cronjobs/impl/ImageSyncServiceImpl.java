package com.letv.portal.service.openstack.cronjobs.impl;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.cloudvm.ICloudvmImageLinkService;
import com.letv.portal.service.cloudvm.ICloudvmImagePropertyService;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.openstack.cronjobs.ImageSyncService;
import com.letv.portal.service.openstack.cronjobs.impl.cache.SyncLocalApiCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
@Service
public class ImageSyncServiceImpl extends AbstractSyncServiceImpl implements ImageSyncService {

    @Autowired
    private ICloudvmImageService cloudvmImageService;

    @Autowired
    private ICloudvmImagePropertyService cloudvmImagePropertyService;

    @Autowired
    private ICloudvmImageLinkService cloudvmImageLinkService;

    @Override
    public void sync(int recordsPerPage) throws MatrixException {
        SyncLocalApiCache apiCache = new SyncLocalApiCache();
        try {

        } finally {
            apiCache.close();
        }
    }
}
