package com.letv.portal.controller.billing.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.letv.common.exception.MatrixException;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateForm;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.validation.IValidationService;
import com.letv.portal.constant.Constants;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.cmdb.ICmdbService;
import com.letv.portal.service.pay.IPayService;
import com.letv.portal.service.product.IProductManageService;

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
    private IValidationService validationService;
	@Autowired
	private ICloudvmRegionService cloudvmRegionService;
	@Autowired
	IPayService payService;
	@Autowired
    private IApiService apiService;
	@Autowired
	private ICmdbService cmdbService;
	@Autowired
	private IProductManageService productManageService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/vm",method=RequestMethod.POST)
	//@IsAdminValid(isAdmin=IsAdminEnum.YES)
	//@SecurityValid(paramKey="conf", signKey="sign", secretKey=SecretKeyEnum.CMDB)
	public @ResponseBody ResultObject createVm(HttpServletRequest request, HttpServletResponse response, ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		
		String groupId = UUID.randomUUID().toString();
		
		List<JSONObject> lists = JSONObject.parseObject((String)params.get("conf"), List.class);
		for(int i=0; i<lists.size(); i++) {
			JSONObject vm = lists.get(i);
			VmCreateForm vmCreateForm = JSONObject.parseObject(vm.toJSONString(), VmCreateForm.class);
			try {
				validationService.validate(vmCreateForm);
			} catch (OpenStackException e) {
				logger.error("rest api createVm params validate failure:", e);
				response.setStatus(422);
				obj.setResult(0);
				obj.addMsg(e.getMessage());
				return obj;
			}
			
			//通过clusterId获取region信息
			CloudvmRegion vmRegion = cloudvmRegionService.selectById(Long.parseLong(vmCreateForm.getClusterId()));
			
			VMCreateConf2 conf = cmdbService.getCreateVmConf(vmCreateForm, vmRegion);
			if(conf == null) {
				obj.setResult(0);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return obj;
			}
			conf.setOrderId((String)params.get("orderId"));
			
			try {
				cmdbService.createSession(conf, vmRegion);
			} catch (OpenStackException e) {
				logger.error("createSession has error:", e);
				throw new MatrixException(e.getMessage(), e);
			}
			
			cmdbService.createVmInfo(Constants.PRODUCT_PRIVATE_VM, JSONObject.toJSONString(conf), groupId, obj);
			if(obj.getResult()==0) {
				return obj;
			}
			String orderNumber = (String) obj.getData();
			Map<String, Object> ret = payService.approve(orderNumber);
			
			if(null != ret.get("alert")) {
				obj.setResult(0);
				obj.addMsg((String)ret.get("alert"));
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return obj;
			} else {
				obj.setData(true);
			}
		}
		
		return obj;
	}
	
	
	
	

}
