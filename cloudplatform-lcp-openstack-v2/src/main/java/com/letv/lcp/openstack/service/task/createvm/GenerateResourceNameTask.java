package com.letv.lcp.openstack.service.task.createvm;

import java.util.List;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.util.NameUtil;

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
                vmCreateContext.setResourceName(NameUtil.nameAddNumber(sourceName, i++));
            }
        }
    }
}
