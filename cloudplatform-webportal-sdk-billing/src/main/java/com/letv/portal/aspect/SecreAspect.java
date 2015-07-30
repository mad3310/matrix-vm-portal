package com.letv.portal.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.common.exception.MatrixException;
import com.letv.common.exception.ValidateException;
import com.letv.common.model.BaseModel;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.annotation.SecreValid;

@Aspect
@Component
public class SecreAspect {
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;

	
	@Before("@annotation(sec)")
	public void isValid(JoinPoint jp,SecreValid sec) {
		Object[] args = jp.getArgs();
		System.out.println("----before---------------------------------------");
	}
	
	@Around("@annotation(sec)")
	public Object isValid(ProceedingJoinPoint pjp,SecreValid sec) {
		System.out.println("----around---------------------------------------");
		
		/*Object[] args = pjp.getArgs();
		BaseModel baseModel = null;
		for (Object object : args) {
			if(object instanceof BaseModel) {
				baseModel = (BaseModel) object;
				break;
			}
		}
		
		Long currentUser = this.sessionService.getSession().getUserId();
		if(currentUser ==null || !currentUser.equals(baseModel.getCreateUser())) {
			throw new MatrixException("当前用户操作不合法", new ValidateException("model's createUser not equals current login User"));
		}*/
		
		try {
			return pjp.proceed();
		} catch (Throwable e) {
			throw new MatrixException("当前用户操作不合法", (Exception) e);
		}
	}
}
