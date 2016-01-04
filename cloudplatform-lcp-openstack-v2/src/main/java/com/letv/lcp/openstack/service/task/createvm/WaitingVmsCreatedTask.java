package com.letv.lcp.openstack.service.task.createvm;

import java.util.LinkedList;
import java.util.List;

import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerExtendedStatus;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.PollingInterruptedException;

public class WaitingVmsCreatedTask extends VmsCreateSubTask {

    @Override
    public void run(MultiVmCreateContext context) throws OpenStackException {
        if (context.getVmCreateContexts() != null) {
            try {
                List<VmCreateContext> unFinishedVms = new LinkedList<VmCreateContext>();
                for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
                    if (vmCreateContext.getServerCreated() != null) {
                        unFinishedVms.add(vmCreateContext);
                    }
                }

                final String vmNetworkName;
                if (context.getPrivateNetwork() != null) {
                    vmNetworkName = context.getPrivateNetwork().getName();
                } else {
                    vmNetworkName = context.getSharedNetwork().getName();
                }
                while (!unFinishedVms.isEmpty()) {
                    for (VmCreateContext vmCreateContext : unFinishedVms
                            .toArray(new VmCreateContext[0])) {
                        Server server = context.getApiCache().getServerApi()
                                .get(vmCreateContext.getServerCreated().getId());
                        if (server == null) {
                            unFinishedVms.remove(vmCreateContext);
                        } else {
                            ServerExtendedStatus serverExtendedStatus = server.getExtendedStatus().get();
                            String taskState = serverExtendedStatus.getTaskState();
                            String vmState = serverExtendedStatus.getVmState();
                            Server.Status serverStatus = server.getStatus();
                            if (serverStatus == Server.Status.ERROR || serverStatus == Server.Status.SHUTOFF) {
                                unFinishedVms.remove(vmCreateContext);
                            } else if (serverStatus == Server.Status.ACTIVE && taskState == null && "active".equals(vmState) && !server.getAddresses().get(vmNetworkName).isEmpty()) {
                                unFinishedVms.remove(vmCreateContext);
                            }
                        }
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new PollingInterruptedException(e);
            }
        }
    }

    @Override
    public void rollback(MultiVmCreateContext context)
            throws OpenStackException {
    }

}
