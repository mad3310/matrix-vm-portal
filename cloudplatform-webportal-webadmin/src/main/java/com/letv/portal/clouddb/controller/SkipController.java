package com.letv.portal.clouddb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**Program Name: SkipController <br>
 * Description:  用于页面跳转       list、detail、form、……<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月8日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
public class SkipController {
	
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
	 * Methods Name: toHostList <br>
	 * Description: toHostList跳转
	 * @author name: wujun
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/list/db/user/{ssd}",method=RequestMethod.GET)
	public ModelAndView toHostList(ModelAndView mav){
		mav.setViewName("/clouddb/host_list");
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
	
}
