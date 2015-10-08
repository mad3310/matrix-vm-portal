package com.letv.portal.service.openstack.cronjobs;

import com.letv.common.exception.MatrixException;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface ImageSyncService {
    void sync(int recordsPerPage) throws MatrixException;
}
