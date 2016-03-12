package com.letv.portal.service.cmdb.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.result.ResultObject;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.CalendarUtil;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateForm;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.billing.CheckResult;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.portal.model.cloudvm.CloudvmFlavor;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.model.cloudvm.CloudvmVolumeType;
import com.letv.portal.model.common.UserModel;
import com.letv.portal.model.order.Order;
import com.letv.portal.service.cloudvm.ICloudvmFlavorService;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.cmdb.ICmdbService;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.order.IOrderService;
import com.letv.portal.service.pay.IPayService;
import com.letv.portal.service.product.IProductManageService;

@Service("cmdbService")
public class CmdbServiceImpl implements ICmdbService {
	
	private final static Logger logger = LoggerFactory.getLogger(CmdbServiceImpl.class);
	
	@Autowired
	private ICloudvmFlavorService cloudvmFlavorService;
	@Autowired
	private ICloudvmImageService cloudvmImageService;
	private IOpenStackSession openStackSession;
	@Autowired
    private IOpenStackService openStackService;
	@Autowired
    private IUserService userService;
	@Autowired
    private SessionServiceImpl sessionService;
	@Autowired
	IProductManageService productManageService;
	@Autowired
	IOrderService orderService;
	
	public VMCreateConf2 getCreateVmConf(VmCreateForm vmCreateForm, CloudvmRegion vmRegion) {
		
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
		conf.setVolumeSize(0);
		
		//根据imageId获取openstack-imageId
		conf.setImageId(vmImage.getImageId());
		//目前只有这一种硬盘类型可用(私有云没有云硬盘)
		conf.setVolumeTypeId(CloudvmVolumeType.SATA.getVolumeTypeId());
		conf.setBindFloatingIp(false);
		//工具类生产密码
		//conf.setAdminPass(PasswordRandom.genStrByPattern(8, ValidationRegex.password));
		conf.setAdminPass("B7ldV60X");//该密码对于私有云主机没有作用
		conf.setCount(vmCreateForm.getCount());
		
		//保存申请人id
		Long userId = insertIfNoUserByEmail(vmCreateForm.getApplyUserEmail(), userName);
		conf.setUserId(userId);
		conf.setUserName(userName);
		conf.setUserEmail(vmCreateForm.getApplyUserEmail());
		//保存操作者id
		String[] operatorEmails = vmCreateForm.getOperateUserEmail().split(",");
		StringBuffer buffer = new StringBuffer();
		for (String email : operatorEmails) {
			buffer.append(insertIfNoUserByEmail(email, email.substring(0, email.indexOf("@")))).append(",");
		}
		conf.setOperatorIds(buffer.toString());
		conf.setCallbackId(vmCreateForm.getCallbackId());
		conf.setOrderTime(vmCreateForm.getOrderTime());
		
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
	
	public void createSession(VMCreateConf2 conf, CloudvmRegion vmRegion) throws OpenStackException {
		openStackSession = openStackService.createSession(conf.getUserId(), conf.getUserEmail(), conf.getUserName(),
				vmRegion);
        openStackSession.init(null);
        Session s = new Session();
        s.setUserId(conf.getUserId());
        s.setOpenStackSession(openStackSession);
        sessionService.setSession(s, null);
	}

	@Override
	public void createVmInfo(Long id, String paramsData, String groupId, ResultObject obj) {
		//去服务提供方验证参数是否合法
		CheckResult validateResult = productManageService.validateParamsDataByServiceProvider(id, paramsData, true);
		if(!validateResult.isSuccess()) {
			logger.info("虚拟机接口提供方验证失败：{}", validateResult.getFailureReason());
			obj.setResult(0);
			obj.addMsg(validateResult.getFailureReason());
			return;
		}
		
		if(!productManageService.buy(id, paramsData, null, groupId, obj)) {
			obj.setResult(0);
			obj.addMsg("参数合法性验证失败");
			return;
		}
		
	}
	
}
