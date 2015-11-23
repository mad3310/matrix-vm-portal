package com.letv.portal.controller.clouddb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.common.util.StringUtil;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.fixedPush.IFixedPushService;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.adminoplog.AoLogType;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.service.adminoplog.AoLog;
import com.letv.portal.service.adminoplog.ClassAoLog;
import com.letv.portal.zabbixPush.IZabbixPushService;

@ClassAoLog(module="手动API")
@Controller
@RequestMapping("/manualApi")
public class ManualApiController {
	
	@Autowired
	private IMclusterProxy mclusterProxy;
	@Autowired
	private IMclusterService mclusterService;
	
	@Autowired
	private IBuildTaskService buildTaskService;
	
	@Autowired
	public IZabbixPushService zabbixPushService;
	@Autowired
	public IFixedPushService fixedPushService;
	@Autowired
	private IContainerService containerService;
	
	private final static Logger logger = LoggerFactory.getLogger(ManualApiController.class);
	
	@AoLog(desc="删除集群zabbix监控",type=AoLogType.DELETE)
	@RequestMapping(value = "/V1/zabbix/{mclusterName}", method=RequestMethod.DELETE) 
	public @ResponseBody ResultObject rmZabbix(@PathVariable String mclusterName,ResultObject result) {
		 List<MclusterModel> mclusters  = this.mclusterService.selectByName(mclusterName);
		 if(mclusters.isEmpty())
			 throw new ValidateException("集群不存在");
		 if(mclusters.size()>1) {
			 throw new ValidateException("集群名不唯一");
		 }
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("mclusterId", mclusters.get(0).getId());
	     this.zabbixPushService.deleteMutilContainerPushZabbixInfo(this.containerService.selectByMap(map));
	     result.getMsgs().add("集群监控删除成功");
	     return result;
	}
	@AoLog(desc="添加集群zabbix监控",type=AoLogType.INSERT)
	@RequestMapping(value = "/V1/zabbix", method=RequestMethod.POST) 
	public @ResponseBody ResultObject addZabbix(@RequestParam String mclusterName,ResultObject result) {
		List<MclusterModel> mclusters  = this.mclusterService.selectByName(mclusterName);
		if(mclusters.isEmpty())
			throw new ValidateException("集群不存在");
		if(mclusters.size()>1) {
			throw new ValidateException("集群名不唯一");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mclusterId", mclusters.get(0).getId());
		boolean addResult = this.zabbixPushService.createMultiContainerPushZabbixInfo(this.containerService.selectByMap(map));
		if(addResult) {
			result.getMsgs().add("集群监控添加成功");
		} else {
			result.getMsgs().add("集群监控添加失败");
		}
		return result;
	}
	
	@AoLog(desc="删除集群固资信息",type=AoLogType.DELETE)
	@RequestMapping(value = "/V1/fixed/{mclusterName}", method=RequestMethod.DELETE) 
	public @ResponseBody ResultObject rmFixed(@PathVariable String mclusterName,ResultObject result) {
		List<MclusterModel> mclusters  = this.mclusterService.selectByName(mclusterName);
		if(mclusters.isEmpty())
			throw new ValidateException("集群不存在");
		if(mclusters.size()>1) {
			throw new ValidateException("集群名不唯一");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mclusterId", mclusters.get(0).getId());
		this.fixedPushService.deleteMutilContainerPushFixedInfo(this.containerService.selectByMap(map));
		result.getMsgs().add("集群固资信息删除成功");
		return result;
	}
	@AoLog(desc="创建集群固资信息",type=AoLogType.INSERT)
	@RequestMapping(value = "/V1/fixed", method=RequestMethod.POST) 
	public @ResponseBody ResultObject addFixed(@RequestParam  String mclusterName,ResultObject result) {
		List<MclusterModel> mclusters  = this.mclusterService.selectByName(mclusterName);
		if(mclusters.isEmpty())
			throw new ValidateException("集群不存在");
		if(mclusters.size()>1) {
			throw new ValidateException("集群名不唯一");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mclusterId", mclusters.get(0).getId());
		boolean addResult = this.fixedPushService.createMutilContainerPushFixedInfo(this.containerService.selectByMap(map));
		if(addResult) {
			result.getMsgs().add("集群固资信息创建成功");
		} else {
			result.getMsgs().add("集群固资信息创建失败");
		}
		return result;
	}
	@AoLog(desc="同步集群固资信息",type=AoLogType.SYNC)
	@RequestMapping(value = "/V1/fixed/syncAll", method=RequestMethod.POST) 
	public @ResponseBody ResultObject syncAll(ResultObject result) {
		List<MclusterModel> mclusters  = this.mclusterService.selectValidMclustersByMap(null);
		int sum = 0;
		int success = 0;
		int fail = 0;
		for (MclusterModel mclusterModel : mclusters) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mclusterId", mclusterModel.getId());
			boolean isSuccess = this.fixedPushService.deleteMutilContainerPushFixedInfo(this.containerService.selectByMap(map));
			if(isSuccess) {
				success++;
			} else {
				fail ++;
			}
			sum++;
		}
		result.getMsgs().add("delete mcluster sum:" + sum);
		result.getMsgs().add("delete mcluster success:" + success);
		result.getMsgs().add("delete mcluster fail:" + fail);
		sum = 0;
		success =0;
		fail = 0;
		
		for (MclusterModel mclusterModel : mclusters) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mclusterId", mclusterModel.getId());
			boolean isSuccess = this.fixedPushService.createMutilContainerPushFixedInfo(this.containerService.selectByMap(map));
			if(isSuccess) {
				success++;
			} else {
				fail ++;
			}
			sum++;
		}
		result.getMsgs().add("add mcluster sum:" + sum);
		result.getMsgs().add("add mcluster success:" + success);
		result.getMsgs().add("add mcluster fail:" + fail);
		return result;
	}
	@AoLog(desc="同步集群zabbix监控",type=AoLogType.SYNC)
	@RequestMapping(value = "/V1/zabbix/syncAll", method=RequestMethod.POST) 
	public @ResponseBody ResultObject syncAllZabbix(ResultObject result) {
		List<MclusterModel> mclusters  = this.mclusterService.selectValidMclustersByMap(null);
		int sum = 0;
		int success = 0;
		int fail = 0;
		for (MclusterModel mclusterModel : mclusters) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mclusterId", mclusterModel.getId());
			boolean isSuccess = this.zabbixPushService.deleteMutilContainerPushZabbixInfo(this.containerService.selectByMap(map));
			if(isSuccess) {
				success++;
			} else {
				fail ++;
			}
			sum++;
		}
		result.getMsgs().add("delete mcluster sum:" + sum);
		result.getMsgs().add("delete mcluster success:" + success);
		result.getMsgs().add("delete mcluster fail:" + fail);
		sum = 0;
		success =0;
		fail = 0;
		
		for (MclusterModel mclusterModel : mclusters) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mclusterId", mclusterModel.getId());
			boolean isSuccess = this.zabbixPushService.createMultiContainerPushZabbixInfo(this.containerService.selectByMap(map));
			if(isSuccess) {
				success++;
			} else {
				fail ++;
			}
			sum++;
		}
		result.getMsgs().add("add mcluster sum:" + sum);
		result.getMsgs().add("add mcluster success:" + success);
		result.getMsgs().add("add mcluster fail:" + fail);
		return result;
	}
	
}
