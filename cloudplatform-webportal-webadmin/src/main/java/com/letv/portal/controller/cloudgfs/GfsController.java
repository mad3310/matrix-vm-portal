package com.letv.portal.controller.cloudgfs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.adminoplog.AoLogType;
import com.letv.portal.proxy.IGfsProxy;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.adminoplog.AoLog;
import com.letv.portal.service.adminoplog.ClassAoLog;
import com.letv.portal.service.gce.IGceImageService;
import com.mysql.jdbc.StringUtils;

@ClassAoLog(module="GFS管理")
@Controller
@RequestMapping("/gfs")
public class GfsController {
	
	@Autowired
	private IGfsProxy gfsProxy;
	private final static String IP = "10.160.140.25";
	
	private final static Logger logger = LoggerFactory.getLogger(GfsController.class);

	@RequestMapping(value="/peer",method=RequestMethod.GET)   
	public @ResponseBody ResultObject peerList(ResultObject obj) {
		Map<String,Object> data =  transResult(gfsProxy.getGfsPeers(IP));
		obj.setData(data);
		return obj;
	}
	
	@RequestMapping(value="/volume",method=RequestMethod.GET)   
	public @ResponseBody ResultObject volumeList(ResultObject obj) {
		Map<String,Object> data =  transResult(gfsProxy.getGfsVolumes(IP));
		obj.setData(data);
		return obj;
	}
	
	@RequestMapping(value="/volume/process/{name}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject getVolProcessInfo(@PathVariable String name,ResultObject obj) {
		Map<String,Object> data =  transResult(gfsProxy.getVolProcessByName(IP, name));
		obj.setData(data);
		return obj;
	}
	
	@RequestMapping(value="/volume/capacity/{name}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject getVolCapacityInfo(@PathVariable String name,ResultObject obj) {
		Map<String,Object> data =  transResult(gfsProxy.getVolCapacityByName(IP, name));
		obj.setData(data);
		return obj;
	}
	
	@RequestMapping(value="/volume/config/{name}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject getVolConfigInfo(@PathVariable String name,ResultObject obj) {
		Map<String,Object> data =  transResult(gfsProxy.getVolConfigByName(IP, name));
		obj.setData(data);
		return obj;
	}
	
	@RequestMapping(value="/volume/log/{name}/splitbrain",method=RequestMethod.GET)   
	public @ResponseBody ResultObject getVolSplitbrainInfo(@PathVariable String name,ResultObject obj) {
		Map<String,Object> data =  transResult(gfsProxy.getVolSplitbrainByName(IP, name));
		obj.setData(data);
		return obj;
	}
	
	@RequestMapping(value="/volume/info/{name}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject getVolHealInfo(@PathVariable String name,ResultObject obj) {
		Map<String,Object> data =  transResult(gfsProxy.getVolHealInfoByName(IP, name));
		obj.setData(data);
		return obj;
	}

	@AoLog(desc="启动volume",type=AoLogType.START)
	@RequestMapping(value="/volume/start",method=RequestMethod.POST)   
	public @ResponseBody ResultObject volStart(String name,ResultObject obj) {
		Map<String,Object> data =  transResult(gfsProxy.volStartByName(IP, name));
		obj.setData(data);
		return obj;
	}
	
	@AoLog(desc="停止volume",type=AoLogType.STOP)
	@RequestMapping(value="/volume/stop",method=RequestMethod.POST)   
	public @ResponseBody ResultObject volStop(String name,ResultObject obj) {
		Map<String,Object> data =  transResult(gfsProxy.volStopByName(IP, name));
		obj.setData(data);
		return obj;
	}
	
	private Map<String,Object> transResult(String result){
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		if(StringUtils.isNullOrEmpty(result))
			return jsonResult;
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
}
