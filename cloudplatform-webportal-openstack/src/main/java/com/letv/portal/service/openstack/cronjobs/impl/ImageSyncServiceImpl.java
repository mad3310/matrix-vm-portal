package com.letv.portal.service.openstack.cronjobs.impl;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.cronjobs.ImageSyncService;
import org.springframework.stereotype.Service;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
@Service
public class ImageSyncServiceImpl extends AbstractSyncServiceImpl implements ImageSyncService {
    @Override
    public void sync(int recordsPerPage) throws MatrixException {

    }
}
