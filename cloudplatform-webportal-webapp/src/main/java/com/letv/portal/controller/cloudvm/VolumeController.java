package com.letv.portal.controller.cloudvm;

import com.letv.portal.vo.cloudvm.form.volume.VolumeEditForm;
import com.letv.portal.vo.cloudvm.form.volume_snapshot.VolumeSnapshotCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.exception.VolumeNotFoundException;
import com.letv.portal.service.openstack.local.service.LocalVolumeService;
import com.letv.portal.service.openstack.local.service.LocalVolumeTypeService;
import com.letv.portal.service.openstack.resource.VolumeResource;
import com.letv.portal.service.openstack.resource.manager.VolumeManager;
import com.letv.portal.service.operate.IRecentOperateService;

import javax.validation.Valid;

@Controller
@RequestMapping("/osv")
public class VolumeController {

	@Autowired
	private SessionServiceImpl sessionService;

	@Autowired
	private LocalVolumeService localVolumeService;

	@Autowired
	private LocalVolumeTypeService localVolumeTypeService;
	@Autowired
	private IRecentOperateService recentOperateService;

	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	public @ResponseBody ResultObject regions() {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVolumeManager()
					.getRegions().toArray(new String[0]));
		} catch (OpenStackException e) {
			e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/regions/group", method = RequestMethod.GET)
	public @ResponseBody ResultObject groupRegions() {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVolumeManager()
					.getGroupRegions());
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/volume/{volumeId}", method = RequestMethod.GET)
	public @ResponseBody ResultObject get(@PathVariable String region,
			@PathVariable String volumeId) {
		ResultObject result = new ResultObject();
		try {
			result.setData(localVolumeService.get(Util.userId(sessionService),region,volumeId));
		} catch (VolumeNotFoundException e) {
			result.addMsg("没有此云硬盘");
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
			result.setData(localVolumeService.list(Util.userId(sessionService),region,name,currentPage,recordsPerPage));
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
			result.setData(Util.session(sessionService).getVolumeManager()
					.listAll(Util.optPara(name), currentPage, recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

//	@RequestMapping(value = "/volume/create", method = RequestMethod.POST)
//	public @ResponseBody ResultObject create(@RequestParam String region,
//			@RequestParam int size,
//			@RequestParam(required = false) String volumeTypeId,
//			@RequestParam(required = false) String volumeSnapshotId,
//			@RequestParam(required = false) String name,
//			@RequestParam(required = false) String description,
//			@RequestParam(required = false) Integer count) {
//		ResultObject result = new ResultObject();
//		try {
//			VolumeCreateConf volumeCreateConf =new VolumeCreateConf();
//			volumeCreateConf.setRegion(region);
//			volumeCreateConf.setSize(size);
//			volumeCreateConf.setVolumeTypeId(volumeTypeId);
//			volumeCreateConf.setVolumeSnapshotId(volumeSnapshotId);
//			volumeCreateConf.setName(name);
//			volumeCreateConf.setDescription(description);
//			volumeCreateConf.setCount(count);
//			Util.session(sessionService).getVolumeManager()
//					.create(volumeCreateConf);
//		} catch (UserOperationException e) {
//			result.addMsg(e.getUserMessage());
//			result.setResult(0);
//		} catch (OpenStackException e) {
//			throw e.matrixException();
//		}
//		return result;
//	}

	@RequestMapping(value = "/region/{region}/volume-delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject delete(@PathVariable String region,
			@RequestParam String volumeId) {
		ResultObject result = new ResultObject();
		try {
			VolumeManager volumeManager = Util.session(sessionService)
					.getVolumeManager();
			VolumeResource volumeResource = volumeManager.get(region, volumeId);
			volumeManager.deleteSync(region, volumeResource);
			//保存硬盘删除操作
			this.recentOperateService.saveInfo(Constant.DELETE_VOLUME, volumeResource.getName());
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/volume/edit", method = RequestMethod.POST)
	public
	@ResponseBody
	ResultObject edit(@Valid VolumeEditForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		long userId = Util.userId(sessionService);
		localVolumeService.updateNameAndDesc(userId, userId, form.getRegion(), form.getVolumeId(), form.getName(), form.getDescription());
		return result;
	}

	@RequestMapping(value = "/volume/type/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listVolumeType(@RequestParam String region) {
		ResultObject result = new ResultObject();
		try {
			result.setData(localVolumeTypeService.list(region));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/volume/snapshot/list", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject listVolumeSnapshot(@RequestParam String region,
									@RequestParam(required = false) String name,
									@RequestParam(required = false) Integer currentPage,
									@RequestParam(required = false) Integer recordsPerPage) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util
					.session(sessionService)
					.getVolumeManager()
					.listVolumeSnapshot(region, name, currentPage,
							recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/volume/snapshot/create", method = RequestMethod.POST)
	public
	@ResponseBody
	ResultObject createVolumeSnapshot(@Valid VolumeSnapshotCreateForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVolumeManager()
					.createVolumeSnapshot(form.getRegion(), form.getVolumeId(), form.getName(), form.getDescription());
			//保存启动操作
			this.recentOperateService.saveInfo(Constant.SNAPSHOT_CREATE_VOLUME, form.getName()+"=="+Util.session(sessionService).getVolumeManager().get(form.getRegion(), form.getVolumeId()).getName());
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/volume/snapshot/delete", method = RequestMethod.POST)
	public
	@ResponseBody
	ResultObject deleteVolumeSnapshot(@RequestParam String region, @RequestParam String snapshotId) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVolumeManager()
					.deleteVolumeSnapshot(region, snapshotId);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}
}
