package com.letv.portal.controller.cloudvm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;
import com.letv.portal.service.openstack.resource.manager.VMCreateConf;
import com.letv.portal.service.openstack.resource.manager.VMManager;

@Controller
@RequestMapping("/ecs")
public class VMController {

	@Autowired
	private SessionServiceImpl sessionService;

	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	public @ResponseBody ResultObject regions() {
		ResultObject result = new ResultObject();
		result.setData(Util.session(sessionService).getVMManager().getRegions()
				.toArray(new String[0]));
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
	public @ResponseBody ResultObject list(@PathVariable String region) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVMManager()
					.list(region));
		} catch (RegionNotFoundException e) {
			throw e.matrixException();
		} catch (ResourceNotFoundException e) {
			throw e.matrixException();
		} catch (APINotAvailableException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region", method = RequestMethod.GET)
	public @ResponseBody ResultObject listAll() {
		ResultObject result = new ResultObject();
		try {
			VMManager vmManager = Util.session(sessionService).getVMManager();
			Set<String> regions = vmManager.getRegions();
			List<VMResource> vmResources = new LinkedList<VMResource>();
			for (String region : regions) {
				vmResources.addAll(vmManager.list(region));
			}
			result.setData(vmResources);
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
			@RequestParam(required = false, defaultValue = "false", value = "publish") boolean bindFloatingIP) {
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
					flavorResource, networkResources, adminPass, bindFloatingIP);
			VMResource vmResource = vmManager.create(region, vmCreateConf);

			result.setData(vmResource);
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
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}
}
