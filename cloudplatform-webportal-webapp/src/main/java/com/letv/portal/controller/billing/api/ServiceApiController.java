package com.letv.portal.controller.billing.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.letv.common.result.ResultObject;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.billing.CheckResult;
import com.letv.lcp.openstack.model.compute.FlavorResource;
import com.letv.lcp.openstack.model.conf.OpenStackConf;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.portal.constant.Constants;
import com.letv.portal.controller.cloudvm.Util;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.pay.IPayService;
import com.letv.portal.service.product.IProductManageService;
import com.letv.portal.vo.cloudvm.form.vm.VmCreateForm;

/**
 * 服务接口(供第三方调用)
 * @author lisuxiao
 *
 */
@Controller
@RequestMapping("/api/service")
public class ServiceApiController {

	private final static Logger logger = LoggerFactory.getLogger(ServiceApiController.class);

	@Autowired
	IProductManageService productManageService;
	private IOpenStackSession openStackSession;
	@Autowired
    private IOpenStackService openStackService;
	@Autowired
    private IUserService userService;
	@Autowired
    private SessionServiceImpl sessionService;
	@Autowired
	IPayService payService;

	@RequestMapping(value="/vm",method=RequestMethod.POST)
	public @ResponseBody ResultObject createVm(@Valid VmCreateForm vmCreateForm, BindingResult validResult, 
			HttpServletRequest request, HttpServletResponse response, ResultObject obj) {
		if(validResult.hasErrors()) {
			obj = new ResultObject(validResult.getAllErrors());
			//校验错误
			response.setStatus(422);
			return obj;
		}
		
		try {
			createSession(vmCreateForm);
			
			List<FlavorResource> flavorResource = Util.session(sessionService).getVMManager().listFlavorResources(vmCreateForm.getRegion());
			for (FlavorResource flavorResource2 : flavorResource) {
				if(flavorResource2.getVcpus().intValue()==vmCreateForm.getCpu().intValue() && 
						flavorResource2.getRam().intValue()==vmCreateForm.getRam().intValue()) {
					vmCreateForm.setFlavorId(flavorResource2.getId());
					break;
				}
			}
		} catch (OpenStackException e) {
			logger.error(e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return obj;
		}
		if(StringUtils.isEmpty(vmCreateForm.getFlavorId())) {
			obj.addMsg("cpu-ram组合配置不正确");
			//校验错误
			response.setStatus(422);
			return obj;
		}
		
		
		//去服务提供方验证参数是否合法
		CheckResult validateResult = productManageService.validateParamsDataByServiceProvider(Constants.PRODUCT_VM, JSONObject.toJSONString(vmCreateForm));
		if(!validateResult.isSuccess()) {
			logger.info("虚拟机接口提供方验证失败：{}", validateResult.getFailureReason());
			obj.setResult(0);
			obj.addMsg(validateResult.getFailureReason());
			return obj;
		}
		
		if(!productManageService.buy(Constants.PRODUCT_VM, JSONObject.toJSONString(vmCreateForm), null, obj)) {
			obj.setResult(0);
			obj.addMsg("参数合法性验证失败");
			return obj;
		}
		
		String orderNumber = (String) obj.getData();
		Map<String, Object> ret = payService.approve(orderNumber);
		if(null != ret.get("alert")) {
			obj.setResult(0);
			obj.addMsg((String)ret.get("alert"));
		} else {
			obj.setData(true);
		}
		return obj;
	}
	
	private void createSession(VmCreateForm vmCreateForm) throws OpenStackException {
		OpenStackConf openStackConf = openStackService.getOpenStackConfDefault();
		openStackConf.setPublicEndpoint(vmCreateForm.getPublicEndpoint());
		openStackConf.setAdminEndpoint(vmCreateForm.getAdminEndpoint());
		
		openStackSession = openStackService.createSession(vmCreateForm.getTenantId(), openStackConf);
        openStackSession.init(null);
        Session s = new Session();
        s.setUserId(vmCreateForm.getTenantId());
        s.setOpenStackSession(openStackSession);
        sessionService.setSession(s, null);
	}

}
