package com.letv.lcp.openstack.service.task.check;


import java.util.List;

import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.jclouds.IApiService;

/**
 * Created by zhouxianguang on 2015/10/20.
 */
public class VmsCreateCheckSubTasksExecutor {
    private List<VmsCreateCheckSubTask> tasks;
    private MultiVmCreateCheckContext context;

    public VmsCreateCheckSubTasksExecutor(List<VmsCreateCheckSubTask> tasks,
                                          MultiVmCreateCheckContext context) {
        this.tasks = tasks;
        this.context = context;
    }

    public void run() throws Exception {
        IApiService apiService = OpenStackServiceImpl.getOpenStackServiceGroup().getApiService();
        context.setNovaApi(apiService.getNovaApi());
        context.setNeutronApi(apiService.getNeutronApi());
        context.setCinderApi(apiService.getCinderApi());
        context.setGlanceApi(apiService.getGlanceApi());
        for (int taskIndex = 0; taskIndex < tasks.size(); taskIndex++) {
            tasks.get(taskIndex).run(context);
        }
    }
}
