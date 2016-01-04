package com.letv.lcp.openstack.service.task.createvm;

import java.util.List;

public class VmsCreateSubTasksExecutor {
	private List<VmsCreateSubTask> tasks;
	private MultiVmCreateContext context;

	public VmsCreateSubTasksExecutor(List<VmsCreateSubTask> tasks,
			MultiVmCreateContext context) {
		this.tasks = tasks;
		this.context = context;
	}

	private void runTasksFromIndex(final int taskBeginIndex) throws Exception {
		int taskIndex = taskBeginIndex;
		try {
			for (; taskIndex < tasks.size(); taskIndex++) {
				tasks.get(taskIndex).run(context);
			}
		} catch (Exception ex) {
			final int exceptionTaskIndex = taskIndex;
			boolean needContinueAfterException = tasks.get(exceptionTaskIndex)
					.needContinueAfterException();
			for (; taskIndex >= taskBeginIndex; taskIndex--) {
				tasks.get(taskIndex).rollback(context);
			}
			if (needContinueAfterException) {
				runTasksFromIndex(exceptionTaskIndex + 1);
			}
			throw ex;
		}
	}

	public void run() throws Exception {
		ApiSession apiSession = new ApiSession(context.getVmManager()
				.getOpenStackConf(), context.getVmManager().getOpenStackUser());
		try {
			tasks.add(0, new CreateApiCacheTask(apiSession));
			runTasksFromIndex(0);
		} finally {
			apiSession.close();
		}
	}
}
