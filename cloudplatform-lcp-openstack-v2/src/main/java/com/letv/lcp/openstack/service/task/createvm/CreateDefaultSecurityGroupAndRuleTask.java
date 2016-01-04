package com.letv.lcp.openstack.service.task.createvm;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;

/**
 * Created by zhouxianguang on 2015/12/1.
 */
public class CreateDefaultSecurityGroupAndRuleTask extends VmsCreateSubTask {

    @Override
    void run(MultiVmCreateContext context) throws OpenStackException {
        OpenStackServiceImpl.getOpenStackServiceGroup().getResourceService()
                .createDefaultSecurityGroupAndRule(context.getApiCache().getApiSession().getNeutronApi());
    }
}
