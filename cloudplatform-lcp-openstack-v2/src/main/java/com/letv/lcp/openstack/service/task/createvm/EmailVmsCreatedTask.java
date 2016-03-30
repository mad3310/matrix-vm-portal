package com.letv.lcp.openstack.service.task.createvm;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.letv.common.email.bean.MailMessage;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;

public class EmailVmsCreatedTask extends VmsCreateSubTask {

    @Override
    public void run(MultiVmCreateContext context) throws OpenStackException {
        if (context.getVmCreateContexts() != null && !context.getVmCreateContexts().isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Map<String, Object> mailMessageModel = new HashMap<String, Object>();
            mailMessageModel.put("userName", context.getVmManager()
                    .getOpenStackUser().getUserName());

            List<Map<String, Object>> vmModelList = new LinkedList<Map<String, Object>>();
            mailMessageModel.put("vmList", vmModelList);

            for (VmCreateContext vmContext : context.getVmCreateContexts()) {
                if (vmContext.getServerCreated() != null) {
                    Map<String, Object> vmModel = new HashMap<String, Object>();
                    vmModel.put("region", context.getRegionDisplayName());
                    vmModel.put("vmId", vmContext.getServerCreated().getId());
                    vmModel.put("vmName", vmContext.getServer().getName());
                    vmModel.put("adminUserName", "root");
                    if (context.getKeyPair() != null) {
                        vmModel.put("keyPairName", context.getKeyPair().getName());
                    } else {
                        vmModel.put("password", context.getVmCreateConf()
                                .getAdminPass());
                    }
                    vmModel.put("createTime", format.format((vmContext.getServer()
                            .getCreated())));
                    if (vmContext.getFloatingIp() != null && vmContext.getFloatingIpBindDate() != null) {
                        vmModel.put("ip", vmContext.getFloatingIp().getFloatingIpAddress());
                        vmModel.put("port", 22);
                        vmModel.put("bindTime", format.format(vmContext.getFloatingIpBindDate()));
                    }
                    vmModelList.add(vmModel);
                }
            }

            if (!vmModelList.isEmpty()) {
                MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统", context
                        .getVmManager().getOpenStackUser().getTenantEmail(),
                        "乐视云平台web-portal系统通知", "cloudvm/createVms.ftl",
                        mailMessageModel);
                mailMessage.setHtml(true);
                OpenStackServiceImpl.getOpenStackServiceGroup().getDefaultEmailSender()
                        .sendMessage(mailMessage);
            }
        }
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
