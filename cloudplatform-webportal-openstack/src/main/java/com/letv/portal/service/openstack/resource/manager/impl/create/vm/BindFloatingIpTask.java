package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.letv.common.email.bean.MailMessage;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;

public class BindFloatingIpTask implements VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		List<Date> bindDates = new LinkedList<Date>();
		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			context.getApiCache()
					.getNovaFloatingIPApi()
					.addToServer(
							vmCreateContext.getFloatingIp()
									.getFloatingIpAddress(),
							vmCreateContext.getServerCreated().getId());
			bindDates.add(new Date());
		}

		emailBindFloatingIp(context, bindDates);
	}

	private void emailBindFloatingIp(MultiVmCreateContext context,
			List<Date> bindDates) throws OpenStackException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Map<String, Object> mailMessageModel = new HashMap<String, Object>();
		mailMessageModel.put("userName", context.getVmManager()
				.getOpenStackUser().getUserName());

		List<Map<String, Object>> vmModelList = new LinkedList<Map<String, Object>>();
		mailMessageModel.put("vmList", vmModelList);

		for (VmCreateContext vmContext : context.getVmCreateContexts()) {
			Map<String, Object> vmModel = new HashMap<String, Object>();
			vmModel.put("region", context.getRegionDisplayName());
			vmModel.put("vmId", vmContext.getServerCreated().getId());
			vmModel.put("vmName", vmContext.getServerCreated().getName());
			vmModel.put("ip", vmContext.getFloatingIp().getFloatingIpAddress());
			vmModel.put("port", 22);
			vmModel.put("bindTime", format.format(bindDates.remove(0)));
			vmModelList.add(vmModel);
		}

		MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统", context
				.getVmManager().getOpenStackUser().getEmail(),
				"乐视云平台web-portal系统通知", "cloudvm/bindFloatingIps.ftl",
				mailMessageModel);
		mailMessage.setHtml(true);
		OpenStackServiceImpl.getOpenStackServiceGroup().getDefaultEmailSender()
				.sendMessage(mailMessage);
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
	}

}
