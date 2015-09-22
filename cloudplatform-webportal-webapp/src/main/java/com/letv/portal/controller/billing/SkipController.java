package com.letv.portal.controller.billing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.session.SessionServiceImpl;

/**Program Name: SkipController <br>
 * Description:  用于页面跳转       payment支付页<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月8日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller("paySkip")
public class SkipController {
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	/**
	 * Methods Name: payment<br>
	 * Description: 跳转至支付页
	 * @author name: yaokuo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/payment/{orderNum}",method=RequestMethod.GET)
	public ModelAndView topayMent(@PathVariable String orderNum,ModelAndView mav){
		mav.addObject("orderNum",orderNum);
		mav.setViewName("/payment/payment");
		return mav;
	}
	
}
