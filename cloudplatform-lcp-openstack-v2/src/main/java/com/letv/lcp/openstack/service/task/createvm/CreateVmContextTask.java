package com.letv.lcp.openstack.service.task.createvm;

import java.util.LinkedList;
import java.util.List;

import com.letv.lcp.openstack.exception.OpenStackException;

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
