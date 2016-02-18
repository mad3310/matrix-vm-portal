package com.letv.portal.controller.billing.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.letv.common.result.ResultObject;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.CalendarUtil;
import com.letv.common.util.HttpUtil;
import com.letv.common.util.PasswordRandom;
import com.letv.lcp.cloudvm.constants.ValidationRegex;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.billing.CheckResult;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.lcp.openstack.service.validation.IValidationService;
import com.letv.portal.constant.Constants;
import com.letv.portal.model.cloudvm.CloudvmFlavor;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.model.cloudvm.CloudvmVolumeType;
import com.letv.portal.model.common.UserModel;
import com.letv.portal.service.cloudvm.ICloudvmFlavorService;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
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
@RequestMapping("/rest/service")
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
	@Autowired
    private IValidationService validationService;
	@Autowired
	private ICloudvmRegionService cloudvmRegionService;
	@Autowired
	private ICloudvmFlavorService cloudvmFlavorService;
	@Autowired
	private ICloudvmImageService cloudvmImageService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/vm",method=RequestMethod.POST)
	//@IsAdminAnnotation(isAdmin=IsAdminEnum.YES)
	public @ResponseBody ResultObject createVm(HttpServletRequest request, HttpServletResponse response, ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		
		List<JSONObject> lists = JSONObject.parseObject((String)params.get("conf"), List.class);
		for (JSONObject vm : lists) {
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
			
			VMCreateConf2 conf = getCreateVmConf(vmCreateForm, vmRegion);
			if(conf == null) {
				obj.setResult(0);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return obj;
			}
			
			//保存申请人id
			String userName = vmCreateForm.getApplyUserEmail().substring(0, vmCreateForm.getApplyUserEmail().indexOf("@"));
			Long userId = insertIfNoUserByEmail(vmCreateForm.getApplyUserEmail(), userName);
			conf.setUserId(userId);
			conf.setUserName(userName);
			//保存操作者id
			String[] operatorEmails = vmCreateForm.getOperateUserEmail().split(",");
			StringBuffer buffer = new StringBuffer();
			for (String email : operatorEmails) {
				buffer.append(insertIfNoUserByEmail(email, email.substring(0, email.indexOf("@")))).append(",");
			}
			conf.setOperatorIds(buffer.toString());
			conf.setCallbackId(vmCreateForm.getCallbackId());
			conf.setOrderTime(vmCreateForm.getOrderTime());
			
			try {
				createSession(conf, vmRegion);
			} catch (OpenStackException e) {
				logger.error("createSession has error:", e);
				return null;
			}
			
			
			
			//去服务提供方验证参数是否合法
			CheckResult validateResult = productManageService.validateParamsDataByServiceProvider(Constants.PRODUCT_VM, JSONObject.toJSONString(conf));
			if(!validateResult.isSuccess()) {
				logger.info("虚拟机接口提供方验证失败：{}", validateResult.getFailureReason());
				response.setStatus(422);
				obj.setResult(0);
				obj.addMsg(validateResult.getFailureReason());
				return obj;
			}
			
			if(!productManageService.buy(Constants.PRODUCT_VM, JSONObject.toJSONString(conf), null, obj)) {
				response.setStatus(422);
				obj.setResult(0);
				obj.addMsg("参数合法性验证失败");
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
	
	private VMCreateConf2 getCreateVmConf(VmCreateForm vmCreateForm, CloudvmRegion vmRegion) {
		
		CloudvmFlavor vmFlavor = cloudvmFlavorService.selectById(Long.parseLong(vmCreateForm.getGroupId()));
		CloudvmImage vmImage = cloudvmImageService.selectById(Long.parseLong(vmCreateForm.getImageId()));
		if(vmRegion==null || vmFlavor==null || vmImage==null) {
			return null;
		}
		
		VMCreateConf2 conf = new VMCreateConf2();
		//工具类生产默认主机名称，规则：申请人邮箱前缀-当前年月日-序号
		String userName = vmCreateForm.getApplyUserEmail().substring(0, vmCreateForm.getApplyUserEmail().indexOf("@"));
		conf.setName(userName+CalendarUtil.getDateString(new Date(), CalendarUtil.SHORT_DATE_FORMAT_NO_DASH));
		conf.setRegion(vmRegion.getCode());
		//根据groupId获取flavorId
		conf.setFlavorId(vmFlavor.getFlavorId());
		//根据groupId获取volumeSize
		conf.setVolumeSize(vmFlavor.getStorage());
		
		//根据imageId获取openstack-imageId
		conf.setImageId(vmImage.getImageId());
		//目前只有这一种硬盘类型可用
		conf.setVolumeTypeId(CloudvmVolumeType.SATA.getVolumeTypeId());
		conf.setBindFloatingIp(false);
		//工具类生产密码
		conf.setAdminPass(PasswordRandom.genStrByPattern(8, ValidationRegex.password));
		conf.setCount(vmCreateForm.getCount());
		
		return conf;
	}
	
	//根据邮箱查询用户不存在就新增该用户
	private Long insertIfNoUserByEmail(String userEmail, String userName) {
		UserModel user = this.userService.getUserByNameAndEmail(userName, userEmail);
		if(user==null) {
			user = new UserModel();
			user.setUserName(userName);
			user.setEmail(userEmail);
			this.userService.insert(user);
			logger.debug("create user :" +userEmail);
		}
		return user.getId();
	}
	
	private void createSession(VMCreateConf2 conf, CloudvmRegion vmRegion) throws OpenStackException {
		openStackSession = openStackService.createSession(conf.getUserId(), conf.getUserName(),
				vmRegion);
        openStackSession.init(null);
        Session s = new Session();
        s.setUserId(conf.getUserId());
        s.setOpenStackSession(openStackSession);
        sessionService.setSession(s, null);
	}

}
