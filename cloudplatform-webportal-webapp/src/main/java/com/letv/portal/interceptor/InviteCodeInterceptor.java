package com.letv.portal.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.invitecode.IInviteCodeService;

/**
 * 邀请码拦截器
 */
@Component
public class InviteCodeInterceptor implements HandlerInterceptor{
	
	private final static Logger logger = LoggerFactory.getLogger(InviteCodeInterceptor.class);
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Autowired
	private IInviteCodeService inviteCodeService;
	
	public String[] allowUrls;
	
	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		if(allowUrl(request)) {
			return true;
		}
		boolean ret = inviteCodeService.isInviteCodeUser(this.sessionService.getSession().getUserId());
		if(!ret) {
			return toVerify(request, response);
		}
		return true;
	}
	
	/**
	  * @Title: toVerify
	  * @Description: 用户验证是否是邀请码用户
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception boolean   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月26日 下午2:00:52
	  */
	private boolean toVerify(HttpServletRequest request, HttpServletResponse response) throws Exception{
		boolean isAjaxRequest = (request.getHeader("x-requested-with") != null)? true:false;
		if (isAjaxRequest) {
			responseJson(request,response,"未验证邀请码！");
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/invite/inviteCode");
			rd.forward(request, response);
		}
		return false;
	}
	
	private boolean allowUrl(HttpServletRequest request){
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");  
		//特殊url过滤
		if(null != allowUrls && allowUrls.length>=1) {
			for(String url : allowUrls) {  
				if(requestUrl.contains(url)) {  
					return true;  
				}  
			}
		}
		if("/".equals(requestUrl)) {
			return true; 
		}
		return false;
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}
	
	private void responseJson(HttpServletRequest req, HttpServletResponse res, String message) {
    	PrintWriter out = null;
		try {
			res.setContentType("text/html;charset=UTF-8");
			out = res.getWriter();
		} catch (IOException e1) {
			logger.error("PrintWriter.getWriter() had error : ", e1);
		}
		ResultObject resultObject = new ResultObject(2);
		resultObject.addMsg(message);
		out.append(JSON.toJSONString(resultObject, SerializerFeature.WriteMapNullValue));
		out.flush();
	}

}