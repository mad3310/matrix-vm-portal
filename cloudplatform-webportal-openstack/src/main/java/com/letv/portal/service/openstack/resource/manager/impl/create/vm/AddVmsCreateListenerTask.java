package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.common.email.bean.MailMessage;
import com.letv.common.util.ExceptionUtils;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;

public class AddVmsCreateListenerTask extends VmsCreateSubTask {

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
								logger.error(e.getMessage(), e);
								sendExceptionEmail(context, e);
							}
						}
					});
		}
	}

	private void sendExceptionEmail(MultiVmCreateContext context,
			Exception exception) {
		Map<String, Object> mailMessageModel = new HashMap<String, Object>();
		mailMessageModel.put("requestUrl", "/ecs/vm/create");
		mailMessageModel.put("exceptionId", context.getVmManager()
				.getOpenStackUser().getUserName());
		mailMessageModel.put("exceptionParams", context.getVmCreateConf()
				.toString());
		String exceptionMessage = exception.getMessage();
		if (exception instanceof OpenStackException) {
			exceptionMessage += (" " + ((OpenStackException) exception)
					.getUserMessage());
		}
		mailMessageModel.put("exceptionMessage", exceptionMessage);
		mailMessageModel.put("exceptionContent",
				ExceptionUtils.getRootCauseStackTrace(exception));

		OpenStackServiceImpl.getOpenStackServiceGroup().getErrorEmailService().sendErrorEmail(mailMessageModel);
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
	}

}
