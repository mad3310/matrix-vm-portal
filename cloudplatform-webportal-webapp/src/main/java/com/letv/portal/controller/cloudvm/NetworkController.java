package com.letv.portal.controller.cloudvm;

import java.text.MessageFormat;

import javax.validation.Valid;

import com.letv.portal.service.openstack.local.service.LocalSubnetOptionService;
import com.letv.portal.service.openstack.resource.service.ResourceServiceFacade;
import org.apache.commons.lang3.StringUtils;
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
import com.letv.portal.service.openstack.resource.manager.NetworkManager;
import com.letv.portal.service.operate.IRecentOperateService;
import com.letv.portal.vo.cloudvm.form.floatingip.EditFloatingIpForm;
import com.letv.portal.vo.cloudvm.form.network.CreatePrivateNetworkAndSubnetForm;
import com.letv.portal.vo.cloudvm.form.network.CreatePrivateNetworkForm;
import com.letv.portal.vo.cloudvm.form.network.EditPrivateNetworkForm;
import com.letv.portal.vo.cloudvm.form.router.EditRouterForm;
import com.letv.portal.vo.cloudvm.form.subnet.CreatePrivateSubnetForm;
import com.letv.portal.vo.cloudvm.form.subnet.EditPrivateSubnetForm;

@Controller
@RequestMapping("/osn")
public class NetworkController {

	@Autowired
	private SessionServiceImpl sessionService;
	@Autowired
	private IRecentOperateService recentOperateService;
	@Autowired
	private LocalSubnetOptionService localSubnetOptionService;
	@Autowired
	private ResourceServiceFacade resourceServiceFacade;

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
			@Valid CreatePrivateNetworkForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService).getNetworkManager()
					.createPrivate(form.getRegion(), form.getName());
			//保存创建私网操作
			this.recentOperateService.saveInfo(Constant.CREATE_PRIVATE_NET, form.getName());
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/network/private/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject editPrivate(@Valid EditPrivateNetworkForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		try {
			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
			String oldName = neworkManager.getPrivate(form.getRegion(), form.getNetworkId()).getName();
			neworkManager.editPrivate(form.getRegion(), form.getNetworkId(), form.getName());
			//保存编辑私网操作
			this.recentOperateService.saveInfo(Constant.EDIT_PRIVATE_NET, 
					MessageFormat.format(Constant.STYLE_OPERATE_1, oldName, Constant.RENAME, form.getName()));
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
			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
			String privateName = neworkManager.getPrivate(region, networkId).getName();
			neworkManager.deletePrivate(region, networkId);
			//保存删除私网操作
			this.recentOperateService.saveInfo(Constant.DELETE_PRIVATE_NET, privateName);
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
//			result.setData(Util
//					.session(sessionService)
//					.getNetworkManager()
//					.listPrivateSubnet(region, name, currentPage,
//							recordsPerPage));
			result.setData(resourceServiceFacade.listPrivateSubnet(region, name, currentPage, recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/subnet/private/create", method = RequestMethod.POST)
	public @ResponseBody ResultObject createPrivateSubnet(
			@Valid CreatePrivateSubnetForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService)
					.getNetworkManager()
					.createPrivateSubnet(form.getRegion(), form.getNetworkId(), form.getName(), form.getCidr(),
							form.getAutoGatewayIp(), form.getGatewayIp(), true);
			//保存创建私网操作
			this.recentOperateService.saveInfo(Constant.CREATE_SUBNET, form.getName());
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/network/subnet/private/create", method = RequestMethod.POST)
	public
	@ResponseBody
	ResultObject createPrivateNetworkAndSubnet(
			@Valid CreatePrivateNetworkAndSubnetForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		try {
			Util.session(sessionService)
					.getNetworkManager()
					.createPrivateNetworkAndSubnet(form.getRegion(), form.getNetworkName(), form.getSubnetName(), form.getCidr(), form.getAutoGatewayIp(), form.getGatewayIp(), true);
			//保存创建vpc操作
			this.recentOperateService.saveInfo(Constant.CREATE_PRIVATE_NET, form.getNetworkName());
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
			@Valid EditPrivateSubnetForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		try {
			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
			String oldName = neworkManager.getPrivateSubnet(form.getRegion(),form.getSubnetId()).getName();
			neworkManager.editPrivateSubnet(form.getRegion(), form.getSubnetId(), form.getName(), form.getGatewayIp(), false);
			//保存编辑子网操作
			this.recentOperateService.saveInfo(Constant.EDIT_SUBNET, 
					MessageFormat.format(Constant.STYLE_OPERATE_1, oldName, Constant.RENAME, form.getName()));
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
			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
			String privateSubnetName = neworkManager.getPrivateSubnet(region, subnetId).getName();
			neworkManager.deletePrivateSubnet(region, subnetId);
			//保存删除子网操作
			this.recentOperateService.saveInfo(Constant.DELETE_SUBNET, privateSubnetName);
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

//	@RequestMapping(value = "/router/create", method = RequestMethod.POST)
//	public @ResponseBody ResultObject createRouter(@RequestParam String region,
//			@RequestParam String name,
//			@RequestParam boolean enablePublicNetworkGateway,
//			@RequestParam String publicNetworkId) {
//		ResultObject result = new ResultObject();
//		try {
//			RouterCreateConf routerCreateConf = new RouterCreateConf();
//			routerCreateConf.setRegion(region);
//			routerCreateConf.setName(name);
//			routerCreateConf.setEnablePublicNetworkGateway(enablePublicNetworkGateway);
//			routerCreateConf.setPublicNetworkId(publicNetworkId);
//			Util.session(sessionService)
//					.getNetworkManager()
//					.createRouter(routerCreateConf);
//			//保存创建路由操作
//			this.recentOperateService.saveInfo(Constant.CREATE_ROUTER, name);
//		} catch (UserOperationException e) {
//			result.addMsg(e.getUserMessage());
//			result.setResult(0);
//		} catch (OpenStackException e) {
//			throw e.matrixException();
//		}
//		return result;
//	}

	@RequestMapping(value = "/router/delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject deleteRouter(@RequestParam String region,
			@RequestParam String routerId) {
		ResultObject result = new ResultObject();
		try {
			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
			String routerName = neworkManager.getRouter(region, routerId).getName();
			neworkManager.deleteRouter(region, routerId);
			//保存删除路由操作
			this.recentOperateService.saveInfo(Constant.DELETE_ROUTER, routerName);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/router/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject editRouter(@Valid EditRouterForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		try {
			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
			String oldName= neworkManager.getRouter(form.getRegion(), form.getRouterId()).getName();
//			neworkManager.editRouter(form.getRegion(), form.getRouterId(), form.getName(),
//							form.getEnablePublicNetworkGateway(), form.getPublicNetworkId());
			resourceServiceFacade.editRouter(form.getRegion(), form.getRouterId(), form.getName(),
					form.getEnablePublicNetworkGateway(), form.getPublicNetworkId());
			//保存编辑路由操作
			this.recentOperateService.saveInfo(Constant.EDIT_ROUTER, 
					MessageFormat.format(Constant.STYLE_OPERATE_1, oldName, Constant.RENAME, form.getName()));
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

	@RequestMapping(value = "/network/private/available_for_router_interface/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listAvailableSubnetsForRouterInterface(
			@RequestParam String region) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.listAvailableSubnetsForRouterInterface(region));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/router/subnet/associate", method = RequestMethod.POST)
	public @ResponseBody ResultObject associateSubnetWithRouter(
			@RequestParam String region, @RequestParam String routerId,
			@RequestParam String subnetId) {
		ResultObject result = new ResultObject();
		try {
			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
			neworkManager.associateSubnetWithRouter(region, routerId, subnetId);
			//保存路由器关联子网操作
			this.recentOperateService.saveInfo(Constant.ROUTER+Constant.BINDED_SUBNET_ROUTER, 
					MessageFormat.format(Constant.STYLE_OPERATE_1, neworkManager.getRouter(region, routerId).getName(), Constant.BINDED_SUBNET_ROUTER,
							neworkManager.getPrivateSubnet(region, subnetId).getName()));
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/router/subnet/separate", method = RequestMethod.POST)
	public @ResponseBody ResultObject separateSubnetFromRouter(
			@RequestParam String region, @RequestParam String routerId,
			@RequestParam String subnetId) {
		ResultObject result = new ResultObject();
		try {
			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
//			neworkManager.separateSubnetFromRouter(region, routerId, subnetId);
			resourceServiceFacade.separateSubnetFromRouter(region, routerId, subnetId);
			//保存路由器解除关联子网操作
			this.recentOperateService.saveInfo(Constant.ROUTER+Constant.UNBINDED_SUBNET_ROUTER, 
					MessageFormat.format(Constant.STYLE_OPERATE_1, neworkManager.getRouter(region, routerId).getName(), Constant.UNBINDED_SUBNET_ROUTER,
							neworkManager.getPrivateSubnet(region, subnetId).getName()));
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/region/{region}/floatingip/{floatingIpId}", method = RequestMethod.GET)
	public @ResponseBody ResultObject getFloatingIp(
			@PathVariable String region, @PathVariable String floatingIpId) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.getFloatingIp(region, floatingIpId));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/floatingip/delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject deleteFloatingIp(
			@RequestParam String region, @RequestParam String floatingIpId) {
		ResultObject result = new ResultObject();
		try {
			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
			String floatingIpName = neworkManager.getFloatingIp(region, floatingIpId).getName();
			neworkManager.deleteFloaingIp(region, floatingIpId);
			//保存删除公网IP操作
			this.recentOperateService.saveInfo(Constant.DELETE_FLOATINGIP, StringUtils.isEmpty(floatingIpName)?Constant.NO_NAME:floatingIpName);
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/network/public/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listPublic(@RequestParam String region) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.listPublic(region));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

//	@RequestMapping(value = "/floatingip/create", method = RequestMethod.POST)
//	public @ResponseBody ResultObject createFloatingIp(
//			@RequestParam String region, @RequestParam String name,
//			@RequestParam String publicNetworkId,
//			@RequestParam Integer bandWidth,
//			@RequestParam Integer count) {
//		ResultObject result = new ResultObject();
//		try {
//			FloatingIpCreateConf floatingIpCreateConf = new FloatingIpCreateConf();
//			floatingIpCreateConf.setRegion(region);
//			floatingIpCreateConf.setName(name);
//			floatingIpCreateConf.setPublicNetworkId(publicNetworkId);
//			floatingIpCreateConf.setBandWidth(bandWidth);
//			floatingIpCreateConf.setCount(count);
//			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
//			neworkManager.createFloatingIp(floatingIpCreateConf);
//			//保存创建公网IP操作
//			this.recentOperateService.saveInfo(Constant.CREATE_FLOATINGIP, name);
//		} catch (UserOperationException e) {
//			result.addMsg(e.getUserMessage());
//			result.setResult(0);
//		} catch (OpenStackException e) {
//			throw e.matrixException();
//		}
//		return result;
//	}

	@RequestMapping(value = "/floatingip/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listFloatingIp(
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer currentPage,
			@RequestParam(required = false) Integer recordsPerPage) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.listFloatingIp(region, name, currentPage, recordsPerPage));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}
	
	@RequestMapping(value = "/floatingip/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject editFloatingIp(
			@Valid EditFloatingIpForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResultObject(bindingResult.getAllErrors());
		}
		ResultObject result = new ResultObject();
		try {
			NetworkManager neworkManager = Util.session(sessionService).getNetworkManager();
			String oldName = neworkManager.getFloatingIp(form.getRegion(), form.getFloatingIpId()).getName();
			neworkManager.editFloatingIp(form.getRegion(), form.getFloatingIpId(), form.getName());
			//保存编辑公网IP操作
			this.recentOperateService.saveInfo(Constant.EDIT_FLOATINGIP, 
					MessageFormat.format(Constant.STYLE_OPERATE_1, 
							StringUtils.isEmpty(oldName)?Constant.NO_NAME:oldName, Constant.RENAME, form.getName()));
		} catch (UserOperationException e) {
			result.addMsg(e.getUserMessage());
			result.setResult(0);
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}
	
	@RequestMapping(value = "/network/shared/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject listShared(@RequestParam String region) {
		ResultObject result = new ResultObject();
		try {
			result.setData(Util.session(sessionService).getNetworkManager()
					.listShared(region));
		} catch (OpenStackException e) {
			throw e.matrixException();
		}
		return result;
	}

	@RequestMapping(value = "/subnet/option/list", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject listSubnetOption() {
		ResultObject result = new ResultObject();
		result.setData(localSubnetOptionService.list());
		return result;
	}
}
