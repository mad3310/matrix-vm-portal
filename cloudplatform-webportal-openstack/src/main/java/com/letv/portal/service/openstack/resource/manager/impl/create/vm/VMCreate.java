package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.ArrayList;
import java.util.List;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;

public class VMCreate {

	private VMCreateConf2 vmCreateConf;
	private VMManagerImpl vmManager;
	private NetworkManagerImpl networkManager;

	public VMCreate(VMCreateConf2 vmCreateConf, VMManagerImpl vmManager,
			NetworkManagerImpl networkManager) {
		this.vmCreateConf = vmCreateConf;
		this.vmManager = vmManager;
		this.networkManager = networkManager;
	}

	public void run() throws OpenStackException {
		if (vmCreateConf.getCount() > 0) {
			try {
				MultiVmCreateContext multiVmCreateContext = new MultiVmCreateContext();
				multiVmCreateContext.setVmCreateConf(vmCreateConf);
				multiVmCreateContext.setVmManager(vmManager);
				multiVmCreateContext.setNetworkManager(networkManager);

				List<VmsCreateSubTask> tasks = new ArrayList<VmsCreateSubTask>();
				tasks.add(new CheckVmCreateConfTask());
				tasks.add(new CheckNovaQuotaTask());
				tasks.add(new CheckFloatingIpQuotaTask());
				tasks.add(new CheckVolumeQuotaTask());
				tasks.add(new CreateSubnetPortsTask());
				tasks.add(new CreateVmsTask());
				tasks.add(new AddVmsCreateListenerTask());
				tasks.add(new EmailVmsCreatedTask());

				VmsCreateSubTasksExecutor executor = new VmsCreateSubTasksExecutor(
						tasks, multiVmCreateContext);
				executor.run();
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
