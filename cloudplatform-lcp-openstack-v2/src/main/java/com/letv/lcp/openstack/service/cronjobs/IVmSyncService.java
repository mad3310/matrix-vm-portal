package com.letv.lcp.openstack.service.cronjobs;

import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Server;

import com.letv.common.exception.MatrixException;
import com.letv.lcp.openstack.exception.OpenStackException;

/**
 * Created by zhouxianguang on 2015/9/28.
 */
public interface IVmSyncService {
    void sync(int recordsPerPage) throws MatrixException;

    void create(long userId, String region, Server server);

    void update(String region, Server server);

    void delete(String region, String vmId);

    void recordVmDeleted(long userId, String region, String vmId, Flavor flavor) throws OpenStackException;

    void onVmRenamed(long tenantId, String region, String vmId, String name);
}
