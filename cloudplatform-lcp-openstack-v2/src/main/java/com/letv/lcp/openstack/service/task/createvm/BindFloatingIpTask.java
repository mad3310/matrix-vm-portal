package com.letv.lcp.openstack.service.task.createvm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.nova.v2_0.domain.InterfaceAttachment;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.extensions.AttachInterfaceApi;

import com.letv.common.email.bean.MailMessage;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;

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
//					context.getApiCache()
//							.getNovaFloatingIPApi()
//							.addToServer(
//									vmCreateContext.getFloatingIp()
//											.getFloatingIpAddress(),
//									vmCreateContext.getServerCreated().getId());
					AttachInterfaceApi attachInterfaceApi = context.getApiCache().getAttachInterfaceApi();
					FloatingIPApi floatingIPApi = context.getApiCache().getNeutronFloatingIpApi();
					String floatingIpId = floatingIP.getId();
					List<InterfaceAttachment> interfaceAttachmentList = attachInterfaceApi.list(server.getId()).toList();
					if (!interfaceAttachmentList.isEmpty()) {
						String portId = interfaceAttachmentList.get(0).getPortId();
						floatingIPApi.update(floatingIpId
								, org.jclouds.openstack.neutron.v2.domain.FloatingIP.UpdateFloatingIP.updateBuilder().portId(
								portId).build());
						vmCreateContext.setFloatingIpBindDate(new Date());
					}
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
