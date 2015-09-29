package com.letv.portal.service.openstack.cronjobs;

import com.letv.common.exception.MatrixException;
import com.letv.portal.model.cloudvm.CloudvmServer;
import com.letv.portal.service.openstack.exception.OpenStackException;
import org.jclouds.openstack.nova.v2_0.domain.Server;

/**
 * Created by zhouxianguang on 2015/9/28.
 */
public interface VmSyncService {
    void sync(int recordsPerPage) throws MatrixException;

    void create(long userId, String region, Server server);

    void update(String region, Server server);

    void delete(String region, String vmId);
}
