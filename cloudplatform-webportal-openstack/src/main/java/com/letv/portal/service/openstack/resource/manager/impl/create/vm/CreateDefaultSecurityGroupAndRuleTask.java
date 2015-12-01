package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;

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
