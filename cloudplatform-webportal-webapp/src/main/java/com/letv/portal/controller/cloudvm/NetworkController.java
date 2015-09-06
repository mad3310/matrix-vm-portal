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

@Controller
@RequestMapping("/osn")
public class NetworkController {

	@Autowired
	private SessionServiceImpl sessionService;

	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	public @ResponseBody ResultObject regions() {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.getRegions().toArray(new String[0]));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}", method = RequestMethod.GET)
	public @ResponseBody ResultObject list(@PathVariable String region) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.list(region));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/regions/group", method = RequestMethod.GET)
	public @ResponseBody ResultObject groupRegions() {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.getGroupRegions());
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/network/{networkId}", method = RequestMethod.GET)
	public @ResponseBody ResultObject get(@PathVariable String region,
			@PathVariable String networkId) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.get(region, networkId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/network/private/{networkId}", method = RequestMethod.GET)
	public @ResponseBody ResultObject getPrivate(@PathVariable String region,
			@PathVariable String networkId) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.getPrivate(region, networkId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/network/private/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listPrivate(
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer currentPage,
			@RequestParam(required = false) Integer recordsPerPage) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.listPrivate(region, name, currentPage, recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/network/private/create", method = RequestMethod.POST)
	public @ResponseBody ResultObject createPrivate(
			@RequestParam String region, @RequestParam String name) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getNetworkManager()
					.createPrivate(region, name);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/network/private/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject editPrivate(@RequestParam String region,
			@RequestParam String networkId, @RequestParam String name) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getNetworkManager()
					.editPrivate(region, networkId, name);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/network/private/delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject deletePrivate(
			@RequestParam String region, @RequestParam String networkId) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getNetworkManager()
					.deletePrivate(region, networkId);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/subnet/private/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listPrivateSubnet(
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer currentPage,
			@RequestParam(required = false) Integer recordsPerPage) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util
					.session(sessionService)
					.getNetworkManager()
					.listPrivateSubnet(region, name, currentPage,
							recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/subnet/private/create", method = RequestMethod.POST)
	public @ResponseBody ResultObject createPrivateSubnet(
			@RequestParam String region, @RequestParam String networkId,
			@RequestParam String name, @RequestParam String cidr,
			@RequestParam boolean autoGatewayIp, @RequestParam String gatewayIp) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService)
					.getNetworkManager()
					.createPrivateSubnet(region, networkId, name, cidr,
							autoGatewayIp, gatewayIp, false);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/subnet/private/{subnetId}", method = RequestMethod.GET)
	public @ResponseBody ResultObject getPrivateSubnet(
			@PathVariable String region, @PathVariable String subnetId) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.getPrivateSubnet(region, subnetId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/subnet/private/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject editPrivateSubnet(
			@RequestParam String region, @RequestParam String subnetId,
			@RequestParam String name, @RequestParam String gatewayIp) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService)
					.getNetworkManager()
					.editPrivateSubnet(region, subnetId, name, gatewayIp, false);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/subnet/private/delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject deletePrivateSubnet(
			@RequestParam String region, @RequestParam String subnetId) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getNetworkManager()
					.deletePrivateSubnet(region, subnetId);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/router/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listRouter(
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer currentPage,
			@RequestParam(required = false) Integer recordsPerPage) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.listRouter(region, name, currentPage, recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/router/{routerId}", method = RequestMethod.GET)
	public @ResponseBody ResultObject getRouter(@PathVariable String region,
			@PathVariable String routerId) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.getRouter(region, routerId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/router/create", method = RequestMethod.POST)
	public @ResponseBody ResultObject createRouter(@RequestParam String region,
			@RequestParam String name,
			@RequestParam boolean enablePublicNetworkGateway) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getNetworkManager()
					.createRouter(region, name, enablePublicNetworkGateway);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/router/delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject deleteRouter(@RequestParam String region,
			@RequestParam String routerId) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getNetworkManager()
					.deleteRouter(region, routerId);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/router/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject editRouter(@RequestParam String region,
			@RequestParam String routerId, @RequestParam String name,
			@RequestParam boolean enablePublicNetworkGateway) {
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService)
					.getNetworkManager()
					.editRouter(region, routerId, name,
							enablePublicNetworkGateway);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/port/{portId}", method = RequestMethod.GET)
	public @ResponseBody ResultObject getPort(@PathVariable String region,
			@PathVariable String portId) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.getPort(region, portId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value="/network/private/available_for_router_interface/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listAvailableSubnetsForRouterInterface(@RequestParam String region){
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.listAvailableSubnetsForRouterInterface(region));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}
}
