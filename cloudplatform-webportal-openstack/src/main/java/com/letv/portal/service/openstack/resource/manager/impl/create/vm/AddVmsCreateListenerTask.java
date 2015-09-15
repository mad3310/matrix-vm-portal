package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;

public class AddVmsCreateListenerTask implements VmsCreateSubTask {

	private Logger logger = LoggerFactory
			.getLogger(AddVmsCreateListenerTask.class);

	@Override
	public void run(final MultiVmCreateContext context)
			throws OpenStackException {
		final List<VmsCreateSubTask> tasks = new LinkedList<VmsCreateSubTask>();
		if (context.getVmCreateConf().getBindFloatingIp()) {
			tasks.add(new BindFloatingIpTask());
		}
		if (context.getVmCreateConf().getVolumeSize() > 0) {
			tasks.add(new AddVolumeTask());
		}
		if (!tasks.isEmpty()) {
			tasks.add(0, new WaitingVmsCreatedTask());
			OpenStackServiceImpl.getOpenStackServiceGroup()
					.getThreadPoolTaskExecutor().execute(new Runnable() {

						@Override
						public void run() {
							try {
								VmsCreateSubTasksExecutor executor = new VmsCreateSubTasksExecutor(
										tasks, context);
								executor.run();
							} catch (Exception e) {
								// TODO send exception email
								logger.error(e.getMessage(), e);
							}
						}
					});
		}
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
	}

}
