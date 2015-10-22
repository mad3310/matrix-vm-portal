package com.letv.portal.service.openstack.cronjobs;

import com.letv.common.exception.MatrixException;

/**
 * Created by zhouxianguang on 2015/10/22.
 */
public interface VolumeSyncService {
    void sync(int recordsPerPage) throws MatrixException;
}
