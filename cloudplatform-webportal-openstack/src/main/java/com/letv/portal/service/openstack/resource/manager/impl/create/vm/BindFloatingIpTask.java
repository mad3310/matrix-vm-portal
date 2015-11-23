package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.common.email.bean.MailMessage;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;

import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.nova.v2_0.domain.Server;

import java.text.SimpleDateFormat;
import java.util.*;

public class BindFloatingIpTask extends VmsCreateSubTask {

	@Override
	boolean isEnable(MultiVmCreateContext context) {
		return context.getVmCreateConf().getBindFloatingIp();
	}

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		if (isEnable(context) && context.getVmCreateContexts() != null) {

			for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
				Server server = context.getApiCache().getServerApi().get(vmCreateContext.getServerCreated().getId());
				FloatingIP floatingIP = context.getApiCache().getNovaFloatingIPApi().get(vmCreateContext.getFloatingIp().getId());
				if (server != null && server.getStatus() != Server.Status.ERROR && floatingIP != null && floatingIP.getInstanceId() == null) {
					context.getApiCache()
							.getNovaFloatingIPApi()
							.addToServer(
									vmCreateContext.getFloatingIp()
											.getFloatingIpAddress(),
									vmCreateContext.getServerCreated().getId());
					vmCreateContext.setFloatingIpBindDate(new Date());
				}
			}

//		emailBindFloatingIp(context, floatingIpIdToBindDate);
		}
	}

	@SuppressWarnings("unused")
	private void emailBindFloatingIp(MultiVmCreateContext context,
									 Map<String,Date> floatingIpIdToBindDate) throws OpenStackException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Map<String, Object> mailMessageModel = new HashMap<String, Object>();
		mailMessageModel.put("userName", context.getVmManager()
				.getOpenStackUser().getUserName());

		List<Map<String, Object>> vmModelList = new LinkedList<Map<String, Object>>();
		mailMessageModel.put("vmList", vmModelList);

		for (VmCreateContext vmContext : context.getVmCreateContexts()) {
			Date bindTime = floatingIpIdToBindDate.get(vmContext.getFloatingIp().getId());
			if (bindTime != null) {
				Map<String, Object> vmModel = new HashMap<String, Object>();
				vmModel.put("region", context.getRegionDisplayName());
				vmModel.put("vmId", vmContext.getServerCreated().getId());
				vmModel.put("vmName", vmContext.getServer().getName());
				vmModel.put("ip", vmContext.getFloatingIp().getFloatingIpAddress());
				vmModel.put("port", 22);
				vmModel.put("bindTime", format.format(bindTime));
				vmModelList.add(vmModel);
			}
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

	@Override
	boolean needContinueAfterException() {
		return true;
	}
}
