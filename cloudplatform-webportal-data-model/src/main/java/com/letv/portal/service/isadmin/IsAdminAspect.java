package com.letv.portal.service.isadmin;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.common.exception.SecurityServiceException;
import com.letv.common.session.SessionServiceImpl;

@Aspect
public class IsAdminAspect { 
	
	@Autowired
	private SessionServiceImpl sessionServiceImpl;
	 
	@Pointcut("@annotation(com.letv.portal.service.isadmin.IsAdminAnnotation)") 
	//@Pointcut("execution(public * com.letv.portal.controller..*(..))")
	public void userAccess() {} 
	
	@Before(value="com.letv.portal.service.isadmin.IsAdminAspect.userAccess()&&@annotation(an)",argNames="an")
	public void beforeAdvice(IsAdminAnnotation an) {
		if(an!=null && an.isAdmin().getCode()) {
			if(!this.sessionServiceImpl.getSession().isAdmin()) {
				throw new SecurityServiceException("没有权限访问");
			}
		}
	    
	}
}
