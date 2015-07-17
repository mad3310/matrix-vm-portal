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
import com.letv.portal.service.openstack.resource.manager.VolumeManager;

@Controller
@RequestMapping("/osv")
public class VolumeController {

	@Autowired
	private SessionServiceImpl sessionService;

	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	public @ResponseBody ResultObject regions() {
		ResultObject result = new ResultObject();
		result.setData(Util.session(sessionService).getVolumeManager()
				.getRegions().toArray(new String[0]));
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
					.listByRegionGroup(region, Util.optPara(name), currentPage,
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
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) Integer count) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getVolumeManager()
					.create(region, size, name, description, count);
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
			volumeManager.delete(region, volumeManager.get(region, volumeId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

}
