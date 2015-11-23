package com.letv.portal.service.openstack.resource.manager.impl.create.vm.check;

import com.letv.portal.service.openstack.exception.OpenStackException;

/**
 * Created by zhouxianguang on 2015/10/20.
 */
public interface VmsCreateCheckSubTask {
    void run(MultiVmCreateCheckContext context) throws OpenStackException;
}
