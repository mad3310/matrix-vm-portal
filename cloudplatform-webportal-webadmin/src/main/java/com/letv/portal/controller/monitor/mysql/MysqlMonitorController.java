package com.letv.portal.controller.monitor.mysql;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.service.monitor.mysql.IMysqlDbSpaceMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlHealthMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlInnoDBMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlKeyBufferMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlResourceMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlTableSpaceMonitorService;
/**
 * Program Name: MonitorController <br>
 * Description:  MySQL监控<br>
 * @author name: lisuxiao <br>
 * Written Date: 2015年7月27日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/monitor/rds/node")
public class MysqlMonitorController {
	
	@Resource
	private IMysqlHealthMonitorService mysqlHealthMonitorService;
	@Resource
	private IMysqlResourceMonitorService mysqlResourceMonitorService;
	@Resource
	private IMysqlKeyBufferMonitorService mysqlKeyBufferMonitorService;
	@Resource
	private IMysqlInnoDBMonitorService mysqlInnoDBMonitorService;
	@Resource
	private IMysqlDbSpaceMonitorService mysqlDbSpaceMonitorService;
	@Resource
	private IMysqlTableSpaceMonitorService mysqlTableSpaceMonitorService;
	
	@RequestMapping(value="/health/list",method=RequestMethod.GET)
	public @ResponseBody ResultObject healthList(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.mysqlHealthMonitorService.queryByPagination(page, params));
		return obj; 
	} 
	
	@RequestMapping(value="/resource/list",method=RequestMethod.GET)
	public @ResponseBody ResultObject resourceList(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.mysqlResourceMonitorService.queryByPagination(page, params));
		return obj; 
	} 
	
	@RequestMapping(value="/keybuffer/list",method=RequestMethod.GET)
	public @ResponseBody ResultObject keyBufferList(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.mysqlKeyBufferMonitorService.queryByPagination(page, params));
		return obj; 
	} 
	
	@RequestMapping(value="/innodb/list",method=RequestMethod.GET)
	public @ResponseBody ResultObject innodbList(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.mysqlInnoDBMonitorService.queryByPagination(page, params));
		return obj; 
	} 
	
	@RequestMapping(value="/db/space/list",method=RequestMethod.GET)
	public @ResponseBody ResultObject dbSpaceList(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.mysqlDbSpaceMonitorService.queryByPagination(page, params));
		return obj; 
	} 
	
	@RequestMapping(value="/table/space/list/{dbSpaceId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject tableSpaceList(@PathVariable Long dbSpaceId,Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("dbSpaceId", dbSpaceId);
		obj.setData(this.mysqlTableSpaceMonitorService.queryByPagination(page, params));
		return obj; 
	} 
	
}
