package com.letv.portal.service.cmdb.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.letv.portal.model.cloudvm.CloudvmCluster;
import com.letv.portal.model.cloudvm.CloudvmFlavor;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.model.cloudvm.CloudvmVolumeType;
import com.letv.portal.model.common.UserModel;
import com.letv.portal.service.cloudvm.ICloudvmClusterService;
import com.letv.portal.service.cloudvm.ICloudvmFlavorService;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.cmdb.ICmdbService;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.order.IOrderService;
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
	@Autowired
	ICloudvmRegionService cloudvmRegionService;
	@Autowired
	ICloudvmClusterService cloudvmClusterService;
	
	public VMCreateConf2 collectCreateVmConf(VmCreateForm vmCreateForm, CloudvmCluster vmCluster) {
		
		//CloudvmFlavor vmFlavor = cloudvmFlavorService.selectById(Long.parseLong(vmCreateForm.getGroupId()));
		//CloudvmImage vmImage = cloudvmImageService.selectById(Long.parseLong(vmCreateForm.getImageId()));

		//if(vmRegion==null || vmFlavor==null || vmImage==null) {
		//	return null;
		//}
		
		
		Map<String, Object> vmParams = new HashMap<String, Object>();
		vmParams.put("cloudvmClusterId", vmCreateForm.getClusterId());
		vmParams.put("name", vmCreateForm.getImageName());
		List<CloudvmImage> vmImages = cloudvmImageService.selectByMap(vmParams);
		vmParams.clear();
		vmParams.put("cloudvmClusterId", vmCreateForm.getClusterId());
		vmParams.put("vcpus", vmCreateForm.getCpu());
		vmParams.put("ram", vmCreateForm.getRam());
		vmParams.put("disk", vmCreateForm.getDisk());
		List<CloudvmFlavor> vmFlavors = cloudvmFlavorService.selectByMap(vmParams);
		
		if(vmImages==null || vmImages.size()==0 || vmFlavors==null || vmFlavors.size()==0) {
			return null;
		}
		
		VMCreateConf2 conf = new VMCreateConf2();
		//工具类生产默认主机名称，规则：申请人邮箱前缀-当前年月日-序号
		String userName = vmCreateForm.getApplyUserEmail().substring(0, vmCreateForm.getApplyUserEmail().indexOf("@"));
		conf.setName(userName+CalendarUtil.getDateString(new Date(), CalendarUtil.SHORT_DATE_FORMAT_NO_DASH));
		conf.setRegion(vmCluster.getCode());
		//根据groupId获取flavorId
		conf.setFlavorId(vmFlavors.get(0).getFlavorId());
		//根据groupId获取volumeSize
		conf.setVolumeSize(0);
		
		//根据imageId获取openstack-imageId
		conf.setImageId(vmImages.get(0).getImageId());
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
	
	public void createSession(VMCreateConf2 conf, CloudvmCluster vmCluster) throws OpenStackException {
		openStackSession = openStackService.createSession(conf.getUserId(), conf.getUserEmail(), conf.getUserName(),
				vmCluster);
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

	@Override
	public List<Map<String, Object>> getFlavorsByClusterId(Long clusterId) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		List<CloudvmFlavor> flavors = this.cloudvmFlavorService.selectByClusterId(clusterId);
		for (CloudvmFlavor cloudvmFlavor : flavors) {
			Map<String, Object> flavor = new HashMap<String, Object>();
			flavor.put("id", cloudvmFlavor.getId());
			flavor.put("name", cloudvmFlavor.getName());
			flavor.put("cpu", cloudvmFlavor.getVcpus());
			flavor.put("ram", cloudvmFlavor.getRam());
			flavor.put("disk", cloudvmFlavor.getDisk());
			ret.add(flavor);
		}
		return ret;
	}

	@Override
	public List<Map<String, Object>> getImagesByClusterId(Long clusterId) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		List<CloudvmImage> images = this.cloudvmImageService.selectByClusterId(clusterId);
		for (CloudvmImage cloudvmImage : images) {
			Map<String, Object> image = new HashMap<String, Object>();
			image.put("id", cloudvmImage.getId());
			image.put("name", cloudvmImage.getName());
			ret.add(image);
		}
		return ret;
	}

	@Override
	public List<Map<String, Object>> getClusters(Map<String,Object> params) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		params.put("type", "1");
		List<CloudvmRegion> regions = this.cloudvmRegionService.selectByMap(params);
		for (CloudvmRegion cloudvmRegion : regions) {
			List<CloudvmCluster> clusters = this.cloudvmClusterService.selectByRegionId(cloudvmRegion.getId());
			for (CloudvmCluster cloudvmCluster : clusters) {
				Map<String, Object> region = new HashMap<String, Object>();
				region.put("id", cloudvmCluster.getId());
				region.put("name", cloudvmCluster.getName());
				ret.add(region);
			}
		}
		return ret;
	}

	@Override
	public List<Map<String, Object>> getFlavors() {
		List<CloudvmRegion> regions = this.cloudvmRegionService.selectByType("1");
		Set<String> judgeRepeat = new HashSet<String>();
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for (CloudvmRegion cloudvmRegion : regions) {
			List<CloudvmCluster> clusters = this.cloudvmClusterService.selectByRegionId(cloudvmRegion.getId());
			for (CloudvmCluster cloudvmCluster : clusters) {
				List<CloudvmFlavor> flavors = this.cloudvmFlavorService.selectByClusterId(cloudvmCluster.getId());
				for (CloudvmFlavor cloudvmFlavor : flavors) {
					if(!judgeRepeat.contains(cloudvmFlavor.getName())) {
						judgeRepeat.add(cloudvmFlavor.getName());
						Map<String, Object> flavor = new HashMap<String, Object>();
						flavor.put("name", cloudvmFlavor.getName());
						flavor.put("cpu", cloudvmFlavor.getVcpus());
						flavor.put("ram", cloudvmFlavor.getRam());
						flavor.put("disk", cloudvmFlavor.getDisk());
						ret.add(flavor);
					}
				}
			}
		}
		return ret;
	}

	@Override
	public List<Map<String, Object>> getImages() {
		List<CloudvmRegion> regions = this.cloudvmRegionService.selectByType("1");
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		Set<String> judgeRepeat = new HashSet<String>();
		for (CloudvmRegion cloudvmRegion : regions) {
			List<CloudvmCluster> clusters = cloudvmClusterService.selectByRegionId(cloudvmRegion.getId());
			for (CloudvmCluster cloudvmCluster : clusters) {
				List<CloudvmImage> images = this.cloudvmImageService.selectByClusterId(cloudvmCluster.getId());
				for (CloudvmImage cloudvmImage : images) {
					if(!judgeRepeat.contains(cloudvmImage.getName())) {
						judgeRepeat.add(cloudvmImage.getName());
						Map<String, Object> image = new HashMap<String, Object>();
						image.put("id", cloudvmImage.getName());
						image.put("name", cloudvmImage.getName());
						ret.add(image);
					}
				}
			}
		}
		return ret;
	}

	
}
