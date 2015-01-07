package com.letv.portal.clouddb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.result.ResultObject;

/**Program Name: SkipController <br>
 * Description:  用于页面跳转       list、detail、form、……<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月8日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
public class SkipController {
	
	@RequestMapping(value ="/dashboard",method=RequestMethod.GET)
	public ModelAndView toDashboard(ModelAndView mav,HttpServletRequest request){
		mav.setViewName("/clouddb/dashboard");
		return mav;
	}
	@RequestMapping(value ="/list/mcluster",method=RequestMethod.GET)
	public ModelAndView toMclusterList(ModelAndView mav,HttpServletRequest request){
		mav.setViewName("/clouddb/mcluster_list");
		return mav;
	}

	@RequestMapping(value="/detail/mcluster/{mclusterId}", method=RequestMethod.GET)   
	public ModelAndView toMclusterDetail(@PathVariable Long mclusterId,ModelAndView mav) {
		mav.addObject("mclusterId",mclusterId);
		mav.setViewName("/clouddb/mcluster_detail");
		return mav;
	}
	/**Methods Name: toList <br>
	 * Description: db页面跳转<br>
	 * @author name: wujun
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list/db",method=RequestMethod.GET)
	public ModelAndView toDbList(ModelAndView mav){
		mav.setViewName("/clouddb/db_list");
		return mav;
	}
	/**
	 * Methods Name: toDbDetail <br>
	 * Description: toDbDetail跳转
	 * @author name: wujun
	 * @return
	 */
	@RequestMapping(value="/detail/db/{dbId}",method=RequestMethod.GET)
	public ModelAndView toDbDetail(@PathVariable Long dbId,ModelAndView mav){
		mav.addObject("dbId",dbId);
		mav.setViewName("/clouddb/db_detail");
		return mav;
	}
	/**
	 * Methods Name: toDbUserList <br>
	 * Description: toDbUserList跳转
	 * @author name: wujun
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/list/dbUser",method=RequestMethod.GET)
	public ModelAndView toDbUserList(ModelAndView mav){
		mav.setViewName("/clouddb/db_user_list");
		return mav;
	}
	/**
	 * Methods Name: toDbAudit <br>
	 * Description: Db审批跳转
	 * @author name: wujun
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/audit/db/{dbId}",method=RequestMethod.GET)
	public ModelAndView toDbAudit(@PathVariable Long dbId, ModelAndView mav){
		mav.addObject("dbId",dbId);
		mav.setViewName("/clouddb/db_audit");
		return mav;
	}
	/**
	 * Methods Name: toHcluster <br>
	 * Description: Host集群跳转
	 * @author name: wujun
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/list/hcluster",method=RequestMethod.GET)
	public ModelAndView toHcluster(ModelAndView mav){
		mav.setViewName("/clouddb/hcluster_list");
		return mav;
	}
	/**
	 * Methods Name: toHclusterDetail <br>
	 * Description: hcluster详情
	 * @author name: wujun
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/detail/hcluster/{hclusterId}",method=RequestMethod.GET)
	public ModelAndView toHclusterDetail(@PathVariable Long hclusterId,ModelAndView mav){
		mav.addObject("hclusterId",hclusterId);
		mav.setViewName("/clouddb/hcluster_detail");
		return mav;
	}
	/**
	 * Methods Name: toMonitorCotainer <br>
	 * Description: container集群监控
	 * @author name: wujun
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/list/mcluster/monitor/{type}",method=RequestMethod.GET)
	public ModelAndView toMonitorCotainer(@PathVariable int type,ModelAndView mav){
		String viewName= "";
		if(type == 1) {
			viewName = "/clouddb/mcluster_monitor_list";
		} else if(type == 2) {
			viewName = "/clouddb/monitor_node_list";
		} else if(type == 3) {
			viewName = "/clouddb/monitor_db_list";
		}
		mav.setViewName(viewName);
		return mav;
	}

	/**
	 * Methods Name: toMonitorCotainerDetail <br>
	 * Description: container集群监控详情
	 * @author name: wujun
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/detail/mcluster/{ip}/monitor",method=RequestMethod.GET)
	public ModelAndView toMonitorCotainerDetail(@PathVariable String ip,ModelAndView mav){
		mav.addObject("ip",ip);
		mav.setViewName("/clouddb/mcluster_monitor_detail");
		return mav;
	}

	/**
	 * Methods Name: toMonitorCotainerDetail <br>
	 * Description: container集群监控视图
	 * @author name: yaokuo
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/view/mcluster/monitor",method=RequestMethod.GET)
	public ModelAndView toMonitorCotainerView(ModelAndView mav){
		mav.setViewName("/clouddb/mcluster_monitor_view");
		return mav;
	}

	@RequestMapping(value ="/jettyMonitor",method=RequestMethod.GET)
	public @ResponseBody ResultObject jettyMonitor(ResultObject obj){
		return obj;
	}
}
