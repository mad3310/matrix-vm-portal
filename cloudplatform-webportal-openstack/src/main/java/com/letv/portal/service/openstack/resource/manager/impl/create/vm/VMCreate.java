package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.ArrayList;
import java.util.List;

import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.service.openstack.billing.listeners.VmCreateListener;
import com.letv.portal.service.openstack.billing.listeners.event.VmCreateEvent;
import com.letv.portal.service.openstack.billing.listeners.event.VmCreateFailEvent;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VolumeManagerImpl;
import com.letv.portal.service.openstack.util.Util;
import org.jclouds.openstack.cinder.v1.domain.Volume;

public class VMCreate {

    private VMCreateConf2 vmCreateConf;
    private VmCreateListener vmCreateListener;
    private Object listenerUserData;
    private VMManagerImpl vmManager;
    private NetworkManagerImpl networkManager;
    private VolumeManagerImpl volumeManager;
    private Long userId;

    public VMCreate(VMCreateConf2 vmCreateConf, VMManagerImpl vmManager,
                    NetworkManagerImpl networkManager, VolumeManagerImpl volumeManager) {
        this(null, vmCreateConf, vmManager, networkManager, volumeManager, null, null);
    }

    public VMCreate(Long userId, VMCreateConf2 vmCreateConf, VMManagerImpl vmManager,
                    NetworkManagerImpl networkManager, VolumeManagerImpl volumeManager, VmCreateListener vmCreateListener, Object listenerUserData) {
        this.userId = userId;
        this.vmCreateConf = vmCreateConf;
        this.vmCreateListener = vmCreateListener;
        this.listenerUserData = listenerUserData;
        this.vmManager = vmManager;
        this.networkManager = networkManager;
        this.volumeManager = volumeManager;
    }

    public void run() throws OpenStackException {
        if (vmCreateConf.getCount() > 0) {
            MultiVmCreateContext multiVmCreateContext = new MultiVmCreateContext();
            try {
                multiVmCreateContext.setVmCreateConf(vmCreateConf);
                multiVmCreateContext.setVmManager(vmManager);
                multiVmCreateContext.setNetworkManager(networkManager);
                multiVmCreateContext.setVolumeManager(volumeManager);
                multiVmCreateContext.setVmCreateListener(vmCreateListener);
                multiVmCreateContext.setListenerUserData(listenerUserData);
                multiVmCreateContext.setUserId(userId);

                List<VmsCreateSubTask> tasks = new ArrayList<VmsCreateSubTask>();
                tasks.add(new CheckVmCreateConfTask());
                tasks.add(new CheckNovaQuotaTask());
                tasks.add(new CreateFloatingIpTask());
                tasks.add(new CreateVolumeTask());
                tasks.add(new CreateSubnetPortsTask());
                tasks.add(new CreateVmsTask());
                tasks.add(new AddVmsCreateListenerTask());
                tasks.add(new EmailVmsCreatedTask());

                VmsCreateSubTasksExecutor executor = new VmsCreateSubTasksExecutor(
                        tasks, multiVmCreateContext);
                executor.run();
            } catch (Exception ex) {
                checkVmCreateFail(multiVmCreateContext, ex);
                Util.throwException(translateExceptionMessage(ex));
            }
            notifyListener(multiVmCreateContext, "后台错误");
        } else {
            throw new UserOperationException(
                    "Virtual machine number cannot be less than or equal to 0.",
                    "虚拟机数量不能小于或等于0");
        }
    }

    private Exception translateExceptionMessage(Exception exception) {
        if (exception.getMessage() != null
                && exception.getMessage()
                .contains(
                        "Flavor's disk is too small for requested image.")) {
            return new UserOperationException("硬件配置过低，不满足镜像的要求。", exception);
        }
        return exception;
    }

    private String getUserMessageOfException(Exception exception) {
        String userMessage;
        if (exception instanceof OpenStackException) {
            userMessage = ((OpenStackException) exception).getUserMessage();
        } else {
            userMessage = exception.getMessage();
        }
        return userMessage;
    }

    private void checkVmCreateFail(MultiVmCreateContext context, Exception exception) throws OpenStackException {
        final String reason = "虚拟机创建失败，原因：" + getUserMessageOfException(translateExceptionMessage(exception));
        notifyListener(context, reason);
    }

    private void notifyListener(MultiVmCreateContext context, String reason) {
        if (context.getVmCreateListener() != null) {
            for (int i = 0; i < context.getVmCreateConf().getCount(); i++) {
                try {
                    if (context.getVmCreateContexts() != null && context.getVmCreateContexts().size() > i) {
                        VmCreateContext vmCreateContext = context.getVmCreateContexts().get(i);
                        if (vmCreateContext.getServerCreated() == null) {
                            context.getVmCreateListener().vmCreateFailed(
                                    new VmCreateFailEvent(context.getVmCreateConf().getRegion(), i, reason, context.getListenerUserData()));
                        } else {
                            context.getVmCreateListener().vmCreated(new VmCreateEvent(context.getVmCreateConf().getRegion(), vmCreateContext.getServerCreated().getId(), i, context.getListenerUserData()));
                        }
                    } else{
                        context.getVmCreateListener().vmCreateFailed(
                                new VmCreateFailEvent(context.getVmCreateConf().getRegion(), i, reason, context.getListenerUserData()));
                    }
                } catch (Exception ex) {
                    Util.processBillingException(ex);
                }
            }
        }
    }

}
