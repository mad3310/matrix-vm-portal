package com.letv.portal.controller.cloudvm;

import java.text.MessageFormat;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.constant.Constant;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.exception.OpenStackCompositeException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.local.service.LocalImageService;
import com.letv.portal.service.openstack.local.service.LocalKeyPairService;
import com.letv.portal.service.openstack.local.service.LocalVmService;
import com.letv.portal.service.openstack.local.service.LocalVolumeService;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.VolumeResource;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.VMManager;
import com.letv.portal.service.openstack.resource.manager.VmSnapshotCreateConf;
import com.letv.portal.service.openstack.resource.manager.VolumeManager;
import com.letv.portal.service.openstack.resource.service.ResourceServiceFacade;
import com.letv.portal.service.openstack.util.ExceptionUtil;
import com.letv.portal.service.openstack.util.HttpUtil;
import com.letv.portal.service.openstack.util.tuple.Tuple2;
import com.letv.portal.service.operate.IRecentOperateService;
import com.letv.portal.vo.cloudvm.form.keypair.CreateKeyPairForm;
import com.letv.portal.vo.cloudvm.form.vm.ChangeAdminPassForm;
import com.letv.portal.vo.cloudvm.form.vm.RenameVmForm;
import com.letv.portal.vo.cloudvm.form.vm_snapshot.VmSnapshotCreateForm;

@Controller
@RequestMapping("/ecs")
public class VMController {

	@Autowired
	private SessionServiceImpl sessionService;

	@Autowired
	private IRecentOperateService recentOperateService;

	@Autowired
	private LocalVmService vmQueryService;

	@Autowired
	private LocalImageService localImageService;

	@Autowired
	private ResourceServiceFacade resourceServiceFacade;

	@Autowired
	private LocalKeyPairService localKeyPairService;
	
	@Autowired
	private LocalVolumeService localVolumeService;

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
//			result.setData(Util
//					.session(sessionService)
//					.getVMManager()
//					.listByRegionGroup(region, Util.optPara(name), currentPage,
//                            recordsPerPage));
			result.setData(resourceServiceFacade.listVm(region,name,currentPage,recordsPerPage));
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
//			result.setData(Util.session(sessionService).getVMManager()
//					.get(region, vmId));
			result.setData(resourceServiceFacade.getVm(region, vmId));
//			result.setData(vmQueryService.get(region,vmId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

//	@RequestMapping(value = "/vm/create", method = RequestMethod.POST)
//	public @ResponseBody ResultObject create(@RequestParam String region,
//			@RequestParam String name, @RequestParam String flavorId,
//			@RequestParam String imageId, @RequestParam String snapshotId,
//			@RequestParam int volumeSize, @RequestParam String volumeTypeId,
//			@RequestParam String privateSubnetId,
//			@RequestParam String sharedNetworkId,
//			@RequestParam boolean bindFloatingIp, @RequestParam int bandWidth,
//			@RequestParam String keyPairName, @RequestParam String adminPass,
//			@RequestParam int count) {
//		ResultObject result = new ResultObject();
//		try {
//			VMCreateConf2 conf = new VMCreateConf2();
//			conf.setRegion(region);
//			conf.setName(name);
//			conf.setFlavorId(flavorId);
//			conf.setImageId(imageId);
//			conf.setSnapshotId(snapshotId);
//			conf.setVolumeSize(volumeSize);
//			conf.setVolumeTypeId(volumeTypeId);
//			conf.setPrivateSubnetId(privateSubnetId);
//			conf.setSharedNetworkId(sharedNetworkId);
//			conf.setBindFloatingIp(bindFloatingIp);
//			conf.setBandWidth(bandWidth);
//			conf.setKeyPairName(keyPairName);
//			conf.setAdminPass(adminPass);
//			conf.setCount(count);
//			Util.session(sessionService).getVMManager().create2(conf);
//		} catch (UserOperationException e) {
//			result.addMsg(e.getUserMessage());
//			result.setResult(0);
//		} catch (OpenStackException e) {
//			throw e.matrixException();
//		}
//		return result;
//	}

//	@RequestMapping(value = "/region/{region}/vm-create", method = RequestMethod.POST)
//	public @ResponseBody ResultObject create(
//			@PathVariable String region,
//			@RequestParam String name,
//			@RequestParam String imageId,
//			@RequestParam String flavorId,
//			@RequestParam(required = false) String networkIds,
//			@RequestParam(required = false) String adminPass,
//			@RequestParam(required = false, defaultValue = "false", value = "publish") boolean bindFloatingIP,
//			@RequestParam(required = false, value = "volumeSizes") String volumeSizesJson) {
//		ResultObject result = new ResultObject();
//		try {
//			OpenStackSession openStackSession = Util.session(sessionService);
//
//			ImageManager imageManager = openStackSession.getImageManager();
//			NetworkManager networkManager = openStackSession
//					.getNetworkManager();
//			VMManager vmManager = openStackSession.getVMManager();
//
//			ImageResource imageResource = imageManager.get(region, imageId);
//
//			FlavorResource flavorResource = vmManager.getFlavorResource(region,
//					flavorId);
//
//			List<NetworkResource> networkResources = null;
//			if (networkIds != null) {
//				String[] networkIdArray = networkIds.split("__");
//				networkResources = new ArrayList<NetworkResource>(
//						networkIdArray.length);
//				for (String networkId : networkIdArray) {
//					networkResources.add(networkManager.get(region, networkId));
//				}
//			}
//
//			VMCreateConf vmCreateConf = new VMCreateConf(name, imageResource,
//					flavorResource, networkResources, adminPass,
//					bindFloatingIP, volumeSizesJson);
//			VMResource vmResource = vmManager.create(region, vmCreateConf);
//
//			result.setData(vmResource);
//		} catch (UserOperationException e) {
//			result.addMsg(e.getUserMessage());
//			result.setResult(0);
//		} catch (OpenStackException e) {
//			throw e.matrixException();
//		}
//		return result;
//	}

//	@RequestMapping(value = "/region/{region}/vm-publish", method = RequestMethod.POST)
//	public @ResponseBody ResultObject publish(@PathVariable String region,
//			@RequestParam String vmId) {
//		ResultObject result = new ResultObject();
//		try {
//			VMManager vmManager = Util.session(sessionService).getVMManager();
//			VMResource vmResource = vmManager.get(region, vmId);
//			vmManager.publish(region, vmResource);
//		} catch (UserOperationException e) {
//			result.addMsg(e.getUserMessage());
//			result.setResult(0);
//		} catch (OpenStackException e) {
//			throw e.matrixException();
//		}
//		return result;
//	}

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
			VMResource vmResource = vmManager.get(region, vmId);
			VolumeResource volumeResource = volumeManager.get(region, volumeId);
			vmManager.attachVolume(vmResource, volumeResource);
			String volumeResourceName = localVolumeService.get(this.sessionService.getSession().getUserId(),region, volumeId).getName();
			//保存绑定云硬盘操作
			this.recentOperateService.saveInfo(Constant.ATTACH_VOLUME_OPENSTACK, 
					MessageFormat.format(Constant.STYLE_OPERATE_1, StringUtils.isEmpty(volumeResourceName)?Constant.NO_NAME:volumeResourceName, vmResource.getName()));
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
			VMResource vmResource =  vmManager.get(region, vmId);
			VolumeResource volumeResource = volumeManager.get(region, volumeId);
			String volumeResourceName = localVolumeService.get(this.sessionService.getSession().getUserId(),region, volumeId).getName();
			vmManager.detachVolume(vmResource, volumeResource);
			//保存卸载云硬盘操作
			this.recentOperateService.saveInfo(Constant.DETACH_VOLUME_OPENSTACK,
					MessageFormat.format(Constant.STYLE_OPERATE_1, StringUtils.isEmpty(volumeResourceName)?Constant.NO_NAME:volumeResourceName, vmResource.getName()));
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
			VMManager vmManager = Util.session(sessionService).getVMManager();
			resourceServiceFacade.bindFloatingIp(region, vmId, floatingIpId);
			String firName = Util.session(sessionService).getNetworkManager().getFloatingIp(region, floatingIpId).getName();
			//保存云主机绑定公网IP操作
			this.recentOperateService.saveInfo(Constant.BINDED_FLOATINGIP_OPENSTACK,
				MessageFormat.format(Constant.STYLE_OPERATE_1, StringUtils.isEmpty(firName)?Constant.NO_NAME:firName, vmManager.get(region, vmId).getName()));
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
			VMManager vmManager = Util.session(sessionService).getVMManager();
			vmManager.unbindFloatingIp(region, vmId, floatingIpId);
			String firName = Util.session(sessionService).getNetworkManager().getFloatingIp(region, floatingIpId).getName();
			//保存云主机解绑公网IP操作
			this.recentOperateService.saveInfo(Constant.UNBINDED_FLOATINGIP_OPENSTACK,
				MessageFormat.format(Constant.STYLE_OPERATE_1, StringUtils.isEmpty(firName)?Constant.NO_NAME:firName, vmManager.get(region, vmId).getName()));
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
		ResultObject result = new ResultObject();
		try {
			long userId = Util.userId(sessionService);
			result.setData(localImageService.listVmSnapshot(userId, region, name, currentPage, recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/snapshot/detail", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject getVmSnapshot(
			@RequestParam String region, @RequestParam String vmSnapshotId) {
		ResultObject result = new ResultObject();
		long userId = Util.userId(sessionService);
		result.setData(localImageService.getVmSnapshot(userId, region, vmSnapshotId));
		return result;
	}

	@RequestMapping(value = "/vm/snapshot/create", method = RequestMethod.POST)
	public
	@ResponseBody
	ResultObject createVmSnapshot(@Valid VmSnapshotCreateForm form,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		try {
			VmSnapshotCreateConf createConf = new VmSnapshotCreateConf();
			createConf.setRegion(form.getRegion());
			createConf.setVmId(form.getVmId());
			createConf.setName(form.getName());
			VMManager vmManager = Util.session(sessionService).getVMManager();
			vmManager.createImageFromVm(createConf);
			//保存创建快照操作
			this.recentOperateService.saveInfo(Constant.SNAPSHOT_CREATE_OPENSTACK, form.getName());
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/snapshot/delete", method = RequestMethod.POST)
	public
	@ResponseBody
	ResultObject deleteVmSnapshot(@RequestParam String region, @RequestParam String vmSnapshotId) {
		ResultObject result = new ResultObject();
		try {
			long userId = sessionService.getSession().getUserId();
			String vmSnapshotName = localImageService.getVmSnapshot(userId, region, vmSnapshotId).getName();
			ImageManager imageManager = Util.session(sessionService).getImageManager();
			imageManager.delete(region, vmSnapshotId);
			//保存删除快照操作
			this.recentOperateService.saveInfo(Constant.SNAPSHOT_DELETE_OPENSTACK, vmSnapshotName);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/reboot", method = RequestMethod.POST)
	public
	@ResponseBody
	ResultObject reboot(@RequestParam String region, @RequestParam String vmId) {
		ResultObject result = new ResultObject();
		try {
			VMManager vmManager = Util.session(sessionService).getVMManager();
			VMResource vmResource = vmManager.get(region, vmId);
			vmManager.rebootSync(vmResource);
			//保存重启云主机操作
			this.recentOperateService.saveInfo(Constant.REBOOT_OPENSTACK, vmResource.getName());
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/rename", method = RequestMethod.POST)
	public
	@ResponseBody
	ResultObject rename(@Valid RenameVmForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		
		try {
			String vmResourceName = Util.session(sessionService).getVMManager().get(form.getRegion(), form.getVmId()).getName();
			resourceServiceFacade.renameVm(form.getRegion(), form.getVmId(), form.getName());
			//保存修改云主机名称操作
			this.recentOperateService.saveInfo(Constant.EDIT_OPENSTACK, MessageFormat.format(Constant.STYLE_OPERATE_2, 
					vmResourceName, form.getName()));
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/changeAdminPass", method = RequestMethod.POST)
	public
	@ResponseBody
	ResultObject changeAdminPass(@Valid ChangeAdminPassForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		try {
			VMManager vmManager = Util.session(sessionService).getVMManager();
			VMResource vmResource = vmManager.get(form.getRegion(), form.getVmId());
			vmManager.changeAdminPass(vmResource, form.getAdminPass());
			//保存修改密码操作
			this.recentOperateService.saveInfo(Constant.MODIFY_PWD_OPENSTACK, vmResource.getName());
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

    @RequestMapping(value = "/vm/attach/subnet", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultObject attachVmsToSubnet(@RequestParam String region, @RequestParam String vmIds, @RequestParam String subnetId) {
        ResultObject result = new ResultObject();
		Tuple2<List<String>, String> vmNamesAndSubnetName = new Tuple2<List<String>, String>();
		try {
            resourceServiceFacade.attachVmsToSubnet(region, vmIds, subnetId, vmNamesAndSubnetName);
        } catch (OpenStackCompositeException e) {
            result.getMsgs().addAll(e.toMsgs());
            result.setResult(0);
            e.throwMatrixExceptionIfNecessary();
        } catch (UserOperationException e) {
            result.addMsg(e.getUserMessage());
            result.setResult(0);
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
        //保存私有网络绑定云主机操作
		this.recentOperateService.saveInfo(Constant.SUBNET_ATTACH_VM
				, MessageFormat.format(Constant.STYLE_OPERATE_1, vmNamesAndSubnetName._2
				, StringUtils.join(vmNamesAndSubnetName._1, ","))
				, this.sessionService.getSession().getUserId(), null);
		return result;
    }

    @RequestMapping(value = "/vm/detach/subnet", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultObject detachVmsToSubnet(@RequestParam String region, @RequestParam String vmIds, @RequestParam String subnetId) {
        ResultObject result = new ResultObject();
		Tuple2<List<String>, String> vmNamesAndSubnetName = new Tuple2<List<String>, String>();
        try {
            resourceServiceFacade.detachVmsFromSubnet(region, vmIds, subnetId, vmNamesAndSubnetName);
        } catch (OpenStackCompositeException e) {
            result.getMsgs().addAll(e.toMsgs());
            result.setResult(0);
            e.throwMatrixExceptionIfNecessary();
        } catch (UserOperationException e) {
            result.addMsg(e.getUserMessage());
            result.setResult(0);
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
		//保存私有网络解绑云主机操作
		this.recentOperateService.saveInfo(Constant.SUBNET_DETACH_VM
				, MessageFormat.format(Constant.STYLE_OPERATE_1, vmNamesAndSubnetName._2
				, StringUtils.join(vmNamesAndSubnetName._1, ","))
				, this.sessionService.getSession().getUserId(), null);
        return result;
    }

	@RequestMapping(value = "/vm/notInAnyNetwork/list", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject listVmNotInAnyNetwork(@RequestParam String region) {
		ResultObject result = new ResultObject();
		try {
			result.setData(resourceServiceFacade.listVmNotInAnyNetwork(region));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/couldAttachSubnet/list", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject listVmCouldAttachSubnet(@RequestParam String region, @RequestParam String subnetId) {
		ResultObject result = new ResultObject();
		try {
			result.setData(resourceServiceFacade.listVmCouldAttachSubnet(region, subnetId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/vm/attached/subnet/list", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject listVmAttachedSubnet(@RequestParam String region, @RequestParam String subnetId) {
		ResultObject result = new ResultObject();
		try {
			result.setData(resourceServiceFacade.listVmAttachedSubnet(region, subnetId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/keypair/list", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject listKeyPair(@RequestParam String region, @RequestParam(required = false) String name,
					  @RequestParam(required = false) Integer currentPage,
					  @RequestParam(required = false) Integer recordsPerPage) {
		ResultObject result = new ResultObject();
		try {
			long userId = Util.userId(sessionService);
			result.setData(localKeyPairService.list(userId, region, name, currentPage, recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/keypair/create", method = RequestMethod.GET)
	public ResponseEntity<String> createKeyPair(@Valid CreateKeyPairForm form, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return HttpUtil.createResponseEntity(new ResultObject(bindingResult.getAllErrors()));
		}
        try {
            String privateKey = resourceServiceFacade.createKeyPair(form.getRegion(), form.getName());
            //保存密钥创建操作
			this.recentOperateService.saveInfo(Constant.CREATE_KEYPAIR, form.getName());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", form.getName() + ".pem");
            return new ResponseEntity<String>(privateKey, headers, HttpStatus.CREATED);
        } catch (Exception ex){
            return ExceptionUtil.getResponseEntityFromException(ex);
        }
    }

    @RequestMapping(value = "/keypair/create/check", method = RequestMethod.GET)
    public
    @ResponseBody
    ResultObject checkCreateKeyPair(@Valid CreateKeyPairForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return new ResultObject(bindingResult.getAllErrors());
		}
        ResultObject result = new ResultObject();
        try {
            resourceServiceFacade.checkCreateKeyPair(form.getRegion(), form.getName());
        } catch (UserOperationException e) {
            result.addMsg(e.getUserMessage());
            result.setResult(0);
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
        return result;
	}

	@RequestMapping(value = "/keypair/delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject deleteKeyPair(@RequestParam String region, @RequestParam String name){
		ResultObject result = new ResultObject();
		try {
			resourceServiceFacade.deleteKeyPair(region, name);
			//删除密钥创建操作
			this.recentOperateService.saveInfo(Constant.DELETE_KEYPAIR, name);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}
}
