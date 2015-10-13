package com.letv.portal.controller.cloudvm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.exception.VolumeNotFoundException;
import com.letv.portal.service.openstack.resource.manager.VolumeManager;

@Controller
@RequestMapping("/osv")
public class VolumeController {

	@Autowired
	private SessionServiceImpl sessionService;

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
			result.setData(Util.session(sessionService).getVolumeManager()
					.get(region, volumeId));
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
			result.setData(Util
					.session(sessionService)
					.getVolumeManager()
					.list(region, Util.optPara(name), currentPage,
							recordsPerPage));
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

	@RequestMapping(value = "/region/{region}/volume-create", method = RequestMethod.POST)
	public @ResponseBody ResultObject create(@PathVariable String region,
			@RequestParam int size,
			@RequestParam(required = false) String volumeTypeId,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) Integer count) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVolumeManager()
					.create(region, size, volumeTypeId, name, description, count);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/volume-delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject delete(@PathVariable String region,
			@RequestParam String volumeId) {
		ResultObject result = new ResultObject();
		try {
			VolumeManager volumeManager = Util.session(sessionService)
					.getVolumeManager();
			volumeManager.deleteSync(region, volumeManager.get(region, volumeId));
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/volume/type/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listVolumeType(@RequestParam String region) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVolumeManager()
					.listVolumeType(region));
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
	ResultObject createVolumeSnapshot(@RequestParam String region,
						@RequestParam String volumeId,
						@RequestParam(required = false) String name,
						@RequestParam(required = false) String description) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVolumeManager()
					.createVolumeSnapshot(region, volumeId, name, description);
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
