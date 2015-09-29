package com.letv.portal.service.openstack.cronjobs;

import com.letv.common.exception.MatrixException;
import com.letv.portal.model.cloudvm.CloudvmServer;
import org.jclouds.openstack.nova.v2_0.domain.Server;

/**
 * Created by zhouxianguang on 2015/9/28.
 */
public interface VmSyncService {
    void sync(int recordsPerPage) throws MatrixException;

    void update(CloudvmServer cloudvmServer, Server server);

    void delete(CloudvmServer cloudvmServer);
}
