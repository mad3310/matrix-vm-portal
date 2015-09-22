package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.ArrayList;
import java.util.List;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VolumeManagerImpl;

public class VMCreate {

	private VMCreateConf2 vmCreateConf;
	private VMManagerImpl vmManager;
	private NetworkManagerImpl networkManager;
	private VolumeManagerImpl volumeManager;

	public VMCreate(VMCreateConf2 vmCreateConf, VMManagerImpl vmManager,
			NetworkManagerImpl networkManager, VolumeManagerImpl volumeManager) {
		this.vmCreateConf = vmCreateConf;
		this.vmManager = vmManager;
		this.networkManager = networkManager;
		this.volumeManager = volumeManager;
	}

	public MultiVmCreateContext run() throws OpenStackException {
		if (vmCreateConf.getCount() > 0) {
			try {
				MultiVmCreateContext multiVmCreateContext = new MultiVmCreateContext();
				multiVmCreateContext.setVmCreateConf(vmCreateConf);
				multiVmCreateContext.setVmManager(vmManager);
				multiVmCreateContext.setNetworkManager(networkManager);
				multiVmCreateContext.setVolumeManager(volumeManager);

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
				return multiVmCreateContext;
			} catch (OpenStackException ex) {
				throw ex;
			} catch (Exception ex) {
				if (ex.getMessage() != null
						&& ex.getMessage()
								.contains(
										"Flavor's disk is too small for requested image.")) {
					throw new UserOperationException("硬件配置过低，不满足镜像的要求。", ex);
				}
				throw new OpenStackException("后台错误", ex);
			}
		} else {
			throw new UserOperationException(
					"Virtual machine number cannot be less than or equal to 0.",
					"虚拟机数量不能小于或等于0");
		}
	}

}
