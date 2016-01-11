package com.letv.lcp.openstack.service.task.createvm;

import java.util.ArrayList;
import java.util.List;

import com.letv.lcp.cloudvm.listener.VmCreateListener;
import com.letv.lcp.cloudvm.model.event.VmCreateEvent;
import com.letv.lcp.cloudvm.model.event.VmCreateFailEvent;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.UserOperationException;
import com.letv.lcp.openstack.service.manage.impl.NetworkManagerImpl;
import com.letv.lcp.openstack.service.manage.impl.VMManagerImpl;
import com.letv.lcp.openstack.service.manage.impl.VolumeManagerImpl;
import com.letv.lcp.openstack.util.ExceptionUtil;
import com.letv.lcp.openstack.util.RetryUtil;
import com.letv.lcp.openstack.util.ThreadUtil;
import com.letv.lcp.openstack.util.function.Function0;


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
            final MultiVmCreateContext multiVmCreateContext = new MultiVmCreateContext();
            Exception exceptionOfCreating = null;
            try {
                multiVmCreateContext.setVmCreateConf(vmCreateConf);
                multiVmCreateContext.setVmManager(vmManager);
                multiVmCreateContext.setNetworkManager(networkManager);
                multiVmCreateContext.setVolumeManager(volumeManager);
                multiVmCreateContext.setVmCreateListener(vmCreateListener);
                multiVmCreateContext.setListenerUserData(listenerUserData);
                multiVmCreateContext.setUserId(userId);

                List<VmsCreateSubTask> tasks = new ArrayList<VmsCreateSubTask>();
                tasks.add(new CreateVmContextTask());
                tasks.add(new GenerateResourceNameTask());
                tasks.add(new CreateDefaultSecurityGroupAndRuleTask());
                tasks.add(new CheckVmCreateConfTask());
                tasks.add(new CheckNovaQuotaTask());
                tasks.add(new CreateFloatingIpTask());
                tasks.add(new CreateVolumeTask());
                tasks.add(new CreateSubnetPortsTask());
                tasks.add(new CreateVmsTask());
//                tasks.add(new AddVmsCreateListenerTask());
//                BindFloatingIpTask bindFloatingIpTask = new BindFloatingIpTask();
//                AddVolumeTask addVolumeTask = new AddVolumeTask();
//                boolean emailLast = false;
//                if (addVolumeTask.isEnable(multiVmCreateContext) || bindFloatingIpTask.isEnable(multiVmCreateContext)) {
//                    emailLast = true;
//                }
//                if (!emailLast) {
//                    tasks.add(new EmailVmsCreatedTask());
//                }
//                tasks.add(new WaitingVmsCreatedTask());
//                tasks.add(bindFloatingIpTask);
//                tasks.add(addVolumeTask);
//                if (emailLast) {
//                    tasks.add(new EmailVmsCreatedTask());
//                }

                VmsCreateSubTasksExecutor executor = new VmsCreateSubTasksExecutor(
                        tasks, multiVmCreateContext);
                executor.run();
            } catch (Exception ex) {
                exceptionOfCreating = ex;
            }

            notifyListener(multiVmCreateContext, exceptionOfCreating);

            ThreadUtil.asyncExec(new Function0<Void>() {
                @Override
                public Void apply() {
                    BindFloatingIpTask bindFloatingIpTask = new BindFloatingIpTask();
                    AddVolumeTask addVolumeTask = new AddVolumeTask();
                    boolean needAddVolume = addVolumeTask.isEnable(multiVmCreateContext);
                    boolean needBindFloatingIp = bindFloatingIpTask.isEnable(multiVmCreateContext);
                    try {
                        List<VmsCreateSubTask> tasks = new ArrayList<VmsCreateSubTask>();
                        if (needAddVolume || needBindFloatingIp) {
                            tasks.add(new WaitingVmsCreatedTask());
                        }
                        if (needBindFloatingIp) {
                            tasks.add(bindFloatingIpTask);
                        }
                        if (needAddVolume) {
                            tasks.add(addVolumeTask);
                        }
                        tasks.add(new EmailVmsCreatedTask());

                        VmsCreateSubTasksExecutor executor = new VmsCreateSubTasksExecutor(
                                tasks, multiVmCreateContext);
                        executor.run();
                    } catch (Exception ex) {
                        ExceptionUtil.logAndEmail(ex);
                    }
                    return null;
                }
            });

            if (exceptionOfCreating != null) {
                ExceptionUtil.throwException(translateExceptionMessage(exceptionOfCreating));
            }
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

    private void notifyListener(MultiVmCreateContext context, Exception exception) throws OpenStackException {
        String reason;
        if (exception != null) {
            reason = "虚拟机创建失败，原因：" + getUserMessageOfException(translateExceptionMessage(exception));
        } else {
            reason = "后台错误";
        }
        notifyListener(context, reason);
    }

    private void notifyListener(final MultiVmCreateContext context, final String reason) {
        if (context.getVmCreateListener() != null) {
//            ThreadUtil.asyncExec(new Function0<Void>() {
//
//                @Override
//                public Void apply() {
                    for (int i = 0; i < context.getVmCreateConf().getCount(); i++) {
                        try {
                            final int vmIndex = i;
                            RetryUtil.retry(new Function0<Boolean>() {
                                @Override
                                public Boolean apply() throws Exception {
                                    if (context.getVmCreateContexts() != null && context.getVmCreateContexts().size() > vmIndex) {
                                        VmCreateContext vmCreateContext = context.getVmCreateContexts().get(vmIndex);
                                        if (vmCreateContext.getServerCreated() == null) {
                                            context.getVmCreateListener().vmCreateFailed(
                                                    new VmCreateFailEvent(context.getVmCreateConf().getRegion(), vmIndex, reason, context.getListenerUserData()));
                                        } else {
                                            VmCreateEvent vmCreateEvent = new VmCreateEvent(this, context.getVmCreateConf().getRegion(), vmCreateContext.getServerCreated().getId(), vmIndex, vmCreateContext.getResourceName(), context.getListenerUserData());
                                            if (vmCreateContext.getVolume() != null) {
                                                vmCreateEvent.setVolumeId(vmCreateContext.getVolume().getId());
                                            }
                                            if (vmCreateContext.getFloatingIp() != null) {
                                                vmCreateEvent.setFloatingIpId(vmCreateContext.getFloatingIp().getId());
                                            }
                                            context.getVmCreateListener().vmCreated(vmCreateEvent);
                                        }
                                    } else {
                                        context.getVmCreateListener().vmCreateFailed(
                                                new VmCreateFailEvent(context.getVmCreateConf().getRegion(), vmIndex, reason, context.getListenerUserData()));
                                    }
                                    return true;
                                }
                            }, 1, "虚拟机监听器实现方错误：重试超过1次");
                        } catch (Exception ex) {
                            ExceptionUtil.processBillingException(ex);
                        }
                    }
//                    return null;
//                }
//            });
        }
    }

}
