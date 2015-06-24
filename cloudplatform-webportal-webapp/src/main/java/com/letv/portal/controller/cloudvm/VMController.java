package com.letv.portal.controller.cloudvm;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.exception.VMDeleteException;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;
import com.letv.portal.service.openstack.resource.manager.VMCreateConf;
import com.letv.portal.service.openstack.resource.manager.VMManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

	@RequestMapping(value = "/region/{region}", method = RequestMethod.GET)
	public @ResponseBody ResultObject list(@PathVariable String region) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getVMManager()
					.list(region));
		} catch (RegionNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
		} catch (ResourceNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
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
		} catch (RegionNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
		} catch (ResourceNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
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
		} catch (RegionNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
		} catch (ResourceNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/vm-create", method = RequestMethod.POST)
	public @ResponseBody ResultObject create(@PathVariable String region,
			@RequestParam String name, @RequestParam String imageId,
			@RequestParam String flavorId,
			@RequestParam(required = false) String networkIds,
			@RequestParam(required = false) String adminPass) {
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

			List<NetworkResource> networkResources=null;
			if (networkIds != null) {
				String[] networkIdArray = networkIds.split("__");
				networkResources = new ArrayList<NetworkResource>(
						networkIdArray.length);
				for (String networkId : networkIdArray) {
					networkResources.add(networkManager.get(region, networkId));
				}
			}

			VMCreateConf vmCreateConf = new VMCreateConf(name, imageResource,
					flavorResource, networkResources, adminPass);
			VMResource vmResource = vmManager.create(region, vmCreateConf);

			result.setData(vmResource);
		} catch (RegionNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
		} catch (ResourceNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
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
			vmManager.delete(region, vmResource);
		} catch (RegionNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
		} catch (ResourceNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
		} catch (VMDeleteException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
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
			vmManager.start(region, vmResource);
		} catch (RegionNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
		} catch (ResourceNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
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
			vmManager.stop(region, vmResource);
		} catch (RegionNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
		} catch (ResourceNotFoundException e) {
			result.setResult(0);
			result.addMsg(e.getMessage());
		}
		return result;
	}
}
