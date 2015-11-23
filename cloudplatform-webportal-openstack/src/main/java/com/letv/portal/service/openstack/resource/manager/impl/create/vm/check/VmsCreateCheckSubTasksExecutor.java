package com.letv.portal.service.openstack.resource.manager.impl.create.vm.check;

import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.jclouds.service.ApiService;

import java.util.List;

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
        ApiService apiService = OpenStackServiceImpl.getOpenStackServiceGroup().getApiService();
        context.setNovaApi(apiService.getNovaApi());
        context.setNeutronApi(apiService.getNeutronApi());
        context.setCinderApi(apiService.getCinderApi());
        context.setGlanceApi(apiService.getGlanceApi());
        for (int taskIndex = 0; taskIndex < tasks.size(); taskIndex++) {
            tasks.get(taskIndex).run(context);
        }
    }
}
