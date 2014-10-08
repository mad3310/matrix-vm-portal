package com.letv.portal.clouddb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**Program Name: SkipController <br>
 * Description:  用于页面跳转<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月8日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
public class SkipController {
	
	@RequestMapping(value ="/list/mcluster",method=RequestMethod.GET)
	public String toList(HttpServletRequest request){
		return "/clouddb/db_list";
	}

	@RequestMapping(value="/detail/mcluster/{mclusterId}", method=RequestMethod.GET)   
	public ModelAndView detail(@PathVariable Long mclusterId,ModelAndView mav) {
		mav.addObject("mclusterId",mclusterId);
		mav.setViewName("/clouddb/mcluster_detail");
		return mav;
	}
}
