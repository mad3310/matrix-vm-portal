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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.letv.portal.model.cloudvm.CloudvmCluster;
import com.letv.portal.service.cloudvm.ICloudvmClusterService;
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
	private ICloudvmClusterService cloudvmClusterService;
	@Autowired
	IPayService payService;
	@Autowired
    private IApiService apiService;
	@Autowired
	private ICmdbService cmdbService;
	@Autowired
	private IProductManageService productManageService;

	/**
	  * @Title: createVm
	  * @Description: 创建云主机
	  * @param request
	  * @param response
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年3月21日 下午3:53:45
	  */
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
			//CloudvmRegion vmRegion = cloudvmRegionService.selectById(Long.parseLong(vmCreateForm.getClusterId()));
			//通过clusterId获取cluster信息
			CloudvmCluster vmCluster = cloudvmClusterService.selectById(Long.parseLong(vmCreateForm.getClusterId()));
			
			VMCreateConf2 conf = cmdbService.getCreateVmConf(vmCreateForm, vmCluster);
			if(conf == null) {
				obj.setResult(0);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return obj;
			}
			conf.setOrderId((String)params.get("orderId"));
			
			try {
				//创建云主机session
				cmdbService.createSession(conf, vmCluster);
			} catch (OpenStackException e) {
				logger.error("createSession has error:", e);
				throw new MatrixException(e.getMessage(), e);
			}
			
			//创建订单订阅
			cmdbService.createVmInfo(Constants.PRODUCT_PRIVATE_VM, JSONObject.toJSONString(conf), groupId, obj);
			if(obj.getResult()==0) {
				return obj;
			}
			String orderNumber = (String) obj.getData();
			//审批通过并调用创建云主机
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
	
	
	/**
	  * @Title: getFlavorsByRegionId
	  * @Description: 根据集群获取云主机配置信息
	  * @param clusterId
	  * @param response
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年3月21日 下午3:54:02
	  */
	@RequestMapping(value="/{clusterId}/flavors",method=RequestMethod.GET)
	//@SecurityValid(paramKey="conf", signKey="sign", secretKey=SecretKeyEnum.CMDB)
	public @ResponseBody ResultObject getFlavorsByClusterId(@PathVariable Long clusterId, HttpServletResponse response, ResultObject obj) {
		obj.setData(this.cmdbService.getFlavorsByClusterId(clusterId));
		return obj;
	}
	
	@RequestMapping(value="/flavors",method=RequestMethod.GET)
	//@SecurityValid(paramKey="conf", signKey="sign", secretKey=SecretKeyEnum.CMDB)
	public @ResponseBody ResultObject getFlavors(HttpServletResponse response, ResultObject obj) {
		obj.setData(this.cmdbService.getFlavors());
		return obj;
	}
	
	@RequestMapping(value="/clusters",method=RequestMethod.GET)
	//@SecurityValid(paramKey="conf", signKey="sign", secretKey=SecretKeyEnum.CMDB)
	public @ResponseBody ResultObject getClusters(HttpServletRequest request, HttpServletResponse response, ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.cmdbService.getClusters(params));
		return obj;
	}
	
	@RequestMapping(value="/{clusterId}/images",method=RequestMethod.GET)
	//@SecurityValid(paramKey="conf", signKey="sign", secretKey=SecretKeyEnum.CMDB)
	public @ResponseBody ResultObject getImagesByRegionId(@PathVariable Long clusterId, HttpServletResponse response, ResultObject obj) {
		obj.setData(this.cmdbService.getImagesByClusterId(clusterId));
		return obj;
	}
	
	@RequestMapping(value="/images",method=RequestMethod.GET)
	//@SecurityValid(paramKey="conf", signKey="sign", secretKey=SecretKeyEnum.CMDB)
	public @ResponseBody ResultObject getImages(HttpServletResponse response, ResultObject obj) {
		obj.setData(this.cmdbService.getImages());
		return obj;
	}
	
	

}
