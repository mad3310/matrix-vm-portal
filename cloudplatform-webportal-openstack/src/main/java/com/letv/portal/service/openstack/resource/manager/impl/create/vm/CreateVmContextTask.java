package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.exception.OpenStackException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/11/20.
 */
public class CreateVmContextTask extends VmsCreateSubTask {

    @Override
    void run(MultiVmCreateContext context) throws OpenStackException {
        List<VmCreateContext> vmCreateContexts = new LinkedList<VmCreateContext>();
        for (int i = 0; i < context.getVmCreateConf().getCount(); i++) {
            vmCreateContexts.add(new VmCreateContext());
        }
        context.setVmCreateContexts(vmCreateContexts);
    }
}
