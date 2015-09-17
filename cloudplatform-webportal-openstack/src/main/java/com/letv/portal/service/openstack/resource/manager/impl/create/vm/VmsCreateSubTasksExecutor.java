package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.List;

public class VmsCreateSubTasksExecutor {
	private List<VmsCreateSubTask> tasks;
	private MultiVmCreateContext context;

	public VmsCreateSubTasksExecutor(List<VmsCreateSubTask> tasks,
			MultiVmCreateContext context) {
		this.tasks = tasks;
		this.context = context;
	}

	public void run() throws Exception {
		ApiSession apiSession = new ApiSession(context.getVmManager()
				.getOpenStackConf(), context.getVmManager().getOpenStackUser());
		try {
			tasks.add(0, new CreateApiCacheTask(apiSession));
			int taskIndex = 0;
			try {
				for (; taskIndex < tasks.size(); taskIndex++) {
					tasks.get(taskIndex).run(context);
				}
			} catch (Exception ex) {
				for (; taskIndex >= 0; taskIndex--) {
					tasks.get(taskIndex).rollback(context);
				}
				throw ex;
			}
		} finally {
			apiSession.close();
		}
	}
}
