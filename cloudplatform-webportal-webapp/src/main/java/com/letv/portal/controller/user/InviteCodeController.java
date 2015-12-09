package com.letv.portal.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.service.invitecode.IInviteCodeService;

/**
 * 邀请码
 * @author lisuxiao
 *
 */
@Controller
@RequestMapping("/inviteCode")
public class InviteCodeController {
	
	@Autowired
	private IInviteCodeService inviteCodeService;
	
	private final static Logger logger = LoggerFactory.getLogger(InviteCodeController.class);
	
	
	@RequestMapping(value="/verify",method=RequestMethod.POST)   
	public @ResponseBody ResultObject verify(ResultObject obj, HttpServletRequest request){
		String inviteCode = (String) request.getParameter("inviteCode");
		String kaptcha = (String) request.getParameter("kaptcha");
		int ret = this.inviteCodeService.verify(inviteCode, kaptcha);
		obj.setData(ret);
		if(ret==0) {
			obj.addMsg("邀请码错误，请重新输入");
		} else if(ret==1) {
			obj.addMsg("邀请码验证通过");
		} else if(ret==2) {
			obj.addMsg("邀请码已使用，请重新输入");
		}
		return obj;
	}
	
	@RequestMapping(value="/create/{amount}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject save(@PathVariable Integer amount, ResultObject obj){
		this.inviteCodeService.create(amount);
		return obj;
	}
	
	
}
