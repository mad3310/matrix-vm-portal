package com.letv.lcp.openstack.service.task.check;

import com.letv.lcp.openstack.exception.OpenStackException;

/**
 * Created by zhouxianguang on 2015/10/20.
 */
public interface VmsCreateCheckSubTask {
    void run(MultiVmCreateCheckContext context) throws OpenStackException;
}
