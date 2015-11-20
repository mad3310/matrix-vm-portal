package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.exception.OpenStackException;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/11/20.
 */
public class GenerateResourceNameTask extends VmsCreateSubTask {

    @Override
    void run(MultiVmCreateContext context) throws OpenStackException {
        List<VmCreateContext> vmCreateContexts = context.getVmCreateContexts();
        int vmCreateContextsCount = vmCreateContexts.size();
        if (vmCreateContextsCount == 1) {
            vmCreateContexts.get(0).setResourceName(context.getVmCreateConf().getName());
        } else if (vmCreateContextsCount > 1) {
            String sourceName = context.getVmCreateConf().getName();
            int i = 1;
            for (VmCreateContext vmCreateContext : vmCreateContexts) {
                vmCreateContext.setResourceName(MessageFormat.format("{0}({1})", sourceName, i++));
            }
        }
    }
}
