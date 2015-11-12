package com.letv.portal.service.openstack.resource.manager.impl.create.vm.check;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VolumeManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.create.vm.*;
import com.letv.portal.service.openstack.util.ExceptionUtil;

import java.util.ArrayList;
import java.util.List;

public class VMCreateCheck {

    private VMCreateConf2 vmCreateConf;
    private VMManagerImpl vmManager;
    private NetworkManagerImpl networkManager;
    private VolumeManagerImpl volumeManager;

    public VMCreateCheck(VMCreateConf2 vmCreateConf, VMManagerImpl vmManager,
                         NetworkManagerImpl networkManager, VolumeManagerImpl volumeManager) {
        this.vmCreateConf = vmCreateConf;
        this.vmManager = vmManager;
        this.networkManager = networkManager;
        this.volumeManager = volumeManager;
    }

    public void run() throws OpenStackException {
        if (vmCreateConf.getCount() > 0) {
            MultiVmCreateCheckContext multiVmCreateCheckContext = new MultiVmCreateCheckContext();
            try {
                multiVmCreateCheckContext.setVmCreateConf(vmCreateConf);
                multiVmCreateCheckContext.setVmManager(vmManager);
                multiVmCreateCheckContext.setNetworkManager(networkManager);
                multiVmCreateCheckContext.setVolumeManager(volumeManager);
                multiVmCreateCheckContext.setUserId(vmManager.getOpenStackUser().getUserVoUserId());

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
