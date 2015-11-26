package com.letv.portal.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.session.SessionServiceImpl;

/**
 * 邀请码验证跳转
 * @author lisuxiao
 *
 */
@Controller("inviteCodeSkip")
public class SkipController {
	
	
	/**
	  * @Title: toVerify
	  * @Description: 验证邀请码
	  * @param mav
	  * @return ModelAndView   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月26日 下午2:42:48
	  */
	@RequestMapping(value ="/toVerify",method=RequestMethod.GET)
	public ModelAndView toVerify(ModelAndView mav){
		mav.setViewName("/index");
		return mav;
	}
	
}
