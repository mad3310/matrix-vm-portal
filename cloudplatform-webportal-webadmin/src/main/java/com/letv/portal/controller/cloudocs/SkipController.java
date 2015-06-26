package com.letv.portal.controller.cloudocs;

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
 * @author name: gm <br>
 * Written Date: 2015年6月24日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller("ocsSkip")
public class SkipController {
	
	@RequestMapping(value ="/list/ocs/cluster",method=RequestMethod.GET)
	public ModelAndView toMclusterList(ModelAndView mav,HttpServletRequest request){
		mav.setViewName("/cloudocs/ocs_cluster_list");
		return mav;
	}
	@RequestMapping(value ="/list/ocs/container",method=RequestMethod.GET)
	public ModelAndView toContainerList(ModelAndView mav,HttpServletRequest request){
		mav.setViewName("/cloudocs/ocs_container_list");
		return mav;
	}
	
}
