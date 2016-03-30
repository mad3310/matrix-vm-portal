package com.letv.lcp.openstack.service.task.check;

import java.util.ArrayList;
import java.util.List;

import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.UserOperationException;
import com.letv.lcp.openstack.service.manage.impl.NetworkManagerImpl;
import com.letv.lcp.openstack.service.manage.impl.VMManagerImpl;
import com.letv.lcp.openstack.service.manage.impl.VolumeManagerImpl;
import com.letv.lcp.openstack.util.ExceptionUtil;

public class VMCreateCheck {

    private VMCreateConf2 vmCreateConf;
    private VMManagerImpl vmManager;
    private NetworkManagerImpl networkManager;
    private VolumeManagerImpl volumeManager;
    private boolean auditUser;

    public VMCreateCheck(VMCreateConf2 vmCreateConf, VMManagerImpl vmManager,
                         NetworkManagerImpl networkManager, VolumeManagerImpl volumeManager, boolean auditUser) {
        this.vmCreateConf = vmCreateConf;
        this.vmManager = vmManager;
        this.networkManager = networkManager;
        this.volumeManager = volumeManager;
        this.auditUser = auditUser;
    }

    public void run() throws OpenStackException {
        if (vmCreateConf.getCount() > 0) {
            MultiVmCreateCheckContext multiVmCreateCheckContext = new MultiVmCreateCheckContext();
            try {
                multiVmCreateCheckContext.setVmCreateConf(vmCreateConf);
                multiVmCreateCheckContext.setVmManager(vmManager);
                multiVmCreateCheckContext.setNetworkManager(networkManager);
                multiVmCreateCheckContext.setVolumeManager(volumeManager);
                multiVmCreateCheckContext.setUserId(vmManager.getOpenStackUser().getApplyUserId());
                multiVmCreateCheckContext.setAuditUser(auditUser);

                List<VmsCreateCheckSubTask> tasks = new ArrayList<VmsCreateCheckSubTask>();
                tasks.add(new CheckVmCreateConfTask());
                tasks.add(new CheckNovaQuotaTask());
                tasks.add(new CheckFloatingIpQuotaTask());
                tasks.add(new CheckVolumeQuotaTask());

                VmsCreateCheckSubTasksExecutor executor = new VmsCreateCheckSubTasksExecutor(
                        tasks, multiVmCreateCheckContext);
                executor.run();
            } catch (Exception ex) {
                ExceptionUtil.throwException(ex);
            }
        } else {
            throw new UserOperationException(
                    "Virtual machine number cannot be less than or equal to 0.",
                    "虚拟机数量不能小于或等于0");
        }
    }

}
