package com.letv.portal.controller.cloudoss;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.result.ResultObject;
import com.letv.portal.service.adminoplog.ClassAoLog;

/**Program Name: SkipController <br>
 * Description:  用于页面跳转       list、detail、form、……<br>
 * @author name: gm <br>
 * Written Date: 2015年6月24日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@ClassAoLog(module="OSS管理")
@Controller("ossSkip")
public class SkipController {
	@RequestMapping(value ="/list/oss",method=RequestMethod.GET)
	public ModelAndView toOssList(ModelAndView mav,HttpServletRequest request){
		mav.setViewName("/cloudoss/oss_list");
		return mav;
	}
}
