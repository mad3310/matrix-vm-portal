package com.letv.portal.controller.billing;

import javax.servlet.http.HttpServletRequest;

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
	@RequestMapping(value ="/payment/{orderNum}/{redirect}",method=RequestMethod.GET)
	public ModelAndView topayMent(@PathVariable String orderNum,@PathVariable String redirect,ModelAndView mav){
		mav.addObject("orderNum",orderNum);
		mav.addObject("redirect",redirect);
		mav.setViewName("/payment/payment");
		return mav;
	}
	@RequestMapping(value ="/payment/success/{orderNum}",method=RequestMethod.GET)
	public ModelAndView paySuccess(@PathVariable String orderNum,ModelAndView mav){
		mav.addObject("orderNum",orderNum);
		mav.setViewName("/payment/paycomplete");
		return mav;
	}
	
	@RequestMapping(value ="/payment/wxpay",method=RequestMethod.GET)
	public ModelAndView wxPay(HttpServletRequest request,ModelAndView mav){
		mav.addObject("orderNum",request.getParameter("orderNum"));
		mav.addObject("money",request.getParameter("money"));
		mav.addObject("accountMoney",request.getParameter("accountMoney"));
		mav.setViewName("/payment/wxpay");
		return mav;
	}
	
}
