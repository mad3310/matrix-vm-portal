package com.letv.portal.controller.cloudvm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.letv.common.paging.impl.Page;
import com.letv.portal.constant.Constant;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.local.query.service.VmQueryService;

import com.letv.portal.service.openstack.util.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;
import com.letv.portal.service.openstack.resource.manager.VMCreateConf;
import com.letv.portal.service.openstack.resource.manager.VMManager;
import com.letv.portal.service.openstack.resource.manager.VolumeManager;
import com.letv.portal.service.openstack.resource.manager.impl.create.vm.VMCreateConf2;
import com.letv.portal.service.operate.IRecentOperateService;

@Controller
@RequestMapping("/ecs")
public class VMController {

	@Autowired
	private SessionServiceImpl sessionService;
	
	@Autowired
	private IRecentOperateService recentOperateService;

	@Autowired
	private VmQueryService vmQueryService;

	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	public @ResponseBody ResultObject regions() {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVMManager()
					.getRegions().toArray(new String[0]));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listRegion() {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVMManager()
					.listRegion());
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/regions/group", method = RequestMethod.GET)
	public @ResponseBody ResultObject groupRegions() {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVMManager()
					.getGroupRegions());
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}", method = RequestMethod.GET)
	public @ResponseBody ResultObject list(@PathVariable String region,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer currentPage,
			@RequestParam(required = false) Integer recordsPerPage) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util
					.session(sessionService)
					.getVMManager()
					.listByRegionGroup(region, Util.optPara(name), currentPage,
							recordsPerPage));
//			result.setData(vmQueryService.list(region,name,currentPage,recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region", method = RequestMethod.GET)
	public @ResponseBody ResultObject listAll(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer currentPage,
			@RequestParam(required = false) Integer recordsPerPage) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVMManager()
					.listAll(Util.optPara(name), currentPage, recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm/{vmId}", method = RequestMethod.GET)
	public @ResponseBody ResultObject get(@PathVariable String region,
			@PathVariable String vmId) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVMManager()
					.get(region, vmId));
//			result.setData(vmQueryService.get(region,vmId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/create", method = RequestMethod.POST)
	public @ResponseBody ResultObject create(@RequestParam String region,
			@RequestParam String name, @RequestParam String flavorId,
			@RequestParam String imageId, @RequestParam String snapshotId,
			@RequestParam int volumeSize, @RequestParam String volumeTypeId,
			@RequestParam String privateSubnetId,
			@RequestParam String sharedNetworkId,
			@RequestParam boolean bindFloatingIp, @RequestParam int bandWidth,
			@RequestParam String keyPairName, @RequestParam String adminPass,
			@RequestParam int count) {
		ResultObject result = new ResultObject();
		try {
			VMCreateConf2 conf = new VMCreateConf2();
			conf.setRegion(region);
			conf.setName(name);
			conf.setFlavorId(flavorId);
			conf.setImageId(imageId);
			conf.setSnapshotId(snapshotId);
			conf.setVolumeSize(volumeSize);
			conf.setVolumeTypeId(volumeTypeId);
			conf.setPrivateSubnetId(privateSubnetId);
			conf.setSharedNetworkId(sharedNetworkId);
			conf.setBindFloatingIp(bindFloatingIp);
			conf.setBandWidth(bandWidth);
			conf.setKeyPairName(keyPairName);
			conf.setAdminPass(adminPass);
			conf.setCount(count);
			Util.session(sessionService).getVMManager().create2(conf);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm-create", method = RequestMethod.POST)
	public @ResponseBody ResultObject create(
			@PathVariable String region,
			@RequestParam String name,
			@RequestParam String imageId,
			@RequestParam String flavorId,
			@RequestParam(required = false) String networkIds,
			@RequestParam(required = false) String adminPass,
			@RequestParam(required = false, defaultValue = "false", value = "publish") boolean bindFloatingIP,
			@RequestParam(required = false, value = "volumeSizes") String volumeSizesJson) {
		ResultObject result = new ResultObject();
		try {
			OpenStackSession openStackSession = Util.session(sessionService);

			ImageManager imageManager = openStackSession.getImageManager();
			NetworkManager networkManager = openStackSession
					.getNetworkManager();
			VMManager vmManager = openStackSession.getVMManager();

			ImageResource imageResource = imageManager.get(region, imageId);

			FlavorResource flavorResource = vmManager.getFlavorResource(region,
					flavorId);

			List<NetworkResource> networkResources = null;
			if (networkIds != null) {
				String[] networkIdArray = networkIds.split("__");
				networkResources = new ArrayList<NetworkResource>(
						networkIdArray.length);
				for (String networkId : networkIdArray) {
					networkResources.add(networkManager.get(region, networkId));
				}
			}

			VMCreateConf vmCreateConf = new VMCreateConf(name, imageResource,
					flavorResource, networkResources, adminPass,
					bindFloatingIP, volumeSizesJson);
			VMResource vmResource = vmManager.create(region, vmCreateConf);

			result.setData(vmResource);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm-publish", method = RequestMethod.POST)
	public @ResponseBody ResultObject publish(@PathVariable String region,
			@RequestParam String vmId) {
		ResultObject result = new ResultObject();
		try {
			VMManager vmManager = Util.session(sessionService).getVMManager();
			VMResource vmResource = vmManager.get(region, vmId);
			vmManager.publish(region, vmResource);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm-unpublish", method = RequestMethod.POST)
	public @ResponseBody ResultObject unpublish(@PathVariable String region,
			@RequestParam String vmId) {
		ResultObject result = new ResultObject();
		try {
			VMManager vmManager = Util.session(sessionService).getVMManager();
			VMResource vmResource = vmManager.get(region, vmId);
			vmManager.unpublish(region, vmResource);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm-delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject delete(@PathVariable String region,
			@RequestParam String vmId) {
		ResultObject result = new ResultObject();
		try {
			VMManager vmManager = Util.session(sessionService).getVMManager();
			VMResource vmResource = vmManager.get(region, vmId);
			vmManager.deleteSync(region, vmResource);
			//保存删除操作
			this.recentOperateService.saveInfo(Constant.DELETE_OPENSTACK, vmResource.getName(), this.sessionService.getSession().getUserId(), null);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm-batch-delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject batchDelete(@RequestParam String vms) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVMManager().batchDeleteSync(vms);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm-start", method = RequestMethod.POST)
	public @ResponseBody ResultObject start(@PathVariable String region,
			@RequestParam String vmId) {
		ResultObject result = new ResultObject();
		try {
			VMManager vmManager = Util.session(sessionService).getVMManager();
			VMResource vmResource = vmManager.get(region, vmId);
			vmManager.startSync(region, vmResource);
			//保存启动操作
			this.recentOperateService.saveInfo(Constant.START_OPENSTACK, vmResource.getName(), this.sessionService.getSession().getUserId(), null);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm-batch-start", method = RequestMethod.POST)
	public @ResponseBody ResultObject batchStart(@RequestParam String vms) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVMManager().batchStartSync(vms);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm-stop", method = RequestMethod.POST)
	public @ResponseBody ResultObject stop(@PathVariable String region,
			@RequestParam String vmId) {
		ResultObject result = new ResultObject();
		try {
			VMManager vmManager = Util.session(sessionService).getVMManager();
			VMResource vmResource = vmManager.get(region, vmId);
			vmManager.stopSync(region, vmResource);
			//保存停止操作
			this.recentOperateService.saveInfo(Constant.STOP_OPENSTACK, vmResource.getName(), this.sessionService.getSession().getUserId(), null);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm-batch-stop", method = RequestMethod.POST)
	public @ResponseBody ResultObject batchStop(String vms) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVMManager().batchStopSync(vms);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm-attach-volume", method = RequestMethod.POST)
	public @ResponseBody ResultObject attachVolume(@PathVariable String region,
			@RequestParam String vmId, @RequestParam String volumeId) {
		ResultObject result = new ResultObject();
		try {
			OpenStackSession openStackSession = Util.session(sessionService);
			VMManager vmManager = openStackSession.getVMManager();
			VolumeManager volumeManager = openStackSession.getVolumeManager();
			vmManager.attachVolume(vmManager.get(region, vmId),
					volumeManager.get(region, volumeId));
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm-detach-volume", method = RequestMethod.POST)
	public @ResponseBody ResultObject detachVolume(@PathVariable String region,
			@RequestParam String vmId, @RequestParam String volumeId) {
		ResultObject result = new ResultObject();
		try {
			OpenStackSession openStackSession = Util.session(sessionService);
			VMManager vmManager = openStackSession.getVMManager();
			VolumeManager volumeManager = openStackSession.getVolumeManager();
			vmManager.detachVolume(vmManager.get(region, vmId),
					volumeManager.get(region, volumeId));
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm-open-console", method = RequestMethod.POST)
	public @ResponseBody ResultObject openConsole(@PathVariable String region,
			@RequestParam String vmId) {
		ResultObject result = new ResultObject();
		try {
			OpenStackSession openStackSession = Util.session(sessionService);
			VMManager vmManager = openStackSession.getVMManager();
			result.setData(vmManager.openConsole(vmManager.get(region, vmId)));
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/is-authority", method = RequestMethod.GET)
	public @ResponseBody ResultObject isAuthority() {
		ResultObject result = new ResultObject();
		OpenStackSession openStackSession = Util.session(sessionService);
		result.setData(openStackSession.isAuthority());
		return result;
	}

	@RequestMapping(value = "/vm/floatingip/bind", method = RequestMethod.POST)
	public @ResponseBody ResultObject bindFloatingIp(
			@RequestParam String region, @RequestParam String vmId,
			@RequestParam String floatingIpId) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVMManager()
					.bindFloatingIp(region, vmId, floatingIpId);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/floatingip/unbind", method = RequestMethod.POST)
	public @ResponseBody ResultObject unbindFloatingIp(
			@RequestParam String region, @RequestParam String vmId,
			@RequestParam String floatingIpId) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVMManager()
					.unbindFloatingIp(region, vmId, floatingIpId);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/unbindedfloatingip/list", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject listVmUnbindedFloatingIp(@RequestParam String region) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVMManager()
					.listVmUnbindedFloatingIp(region));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/snapshot/list", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject listVmSnapshot(@RequestParam String region, @RequestParam(required = false) String name,
								@RequestParam(required = false) Integer currentPage,
								@RequestParam(required = false) Integer recordsPerPage) {
		Params obj = new Params().p("id", "fake-id").p("name","fake vm snapshot").p("region","cn-beijing-1").p("createdAt",11111111).p("size",1).p("status","ACTIVE").p("minRam",1).p("minDisk",1);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(obj);
		Page page;
		if (currentPage == null || recordsPerPage == null) {
			page = new Page();
		} else {
			page = new Page(currentPage, recordsPerPage);
		}
		page.setData(list);
		ResultObject result = new ResultObject();
		result.setData(page);
		return result;
	}

	@RequestMapping(value = "/vm/snapshot/create", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject createVmSnapshot(@RequestParam String region, @RequestParam String vmId, @RequestParam String name) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVMManager()
					.createImageFromVm(region, vmId, name);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/snapshot/delete", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject deleteVmSnapshot(@RequestParam String region, @RequestParam String vmSnapshotId) {
		ResultObject result = new ResultObject();
		return result;
	}

}
