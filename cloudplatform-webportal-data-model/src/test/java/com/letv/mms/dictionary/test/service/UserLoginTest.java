package com.letv.mms.dictionary.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.mms.base.AbstractTestCase;
import com.letv.portal.model.UserModel;
import com.letv.portal.service.IUserService;


public class UserLoginTest extends AbstractTestCase {
	@Autowired
	private IUserService userService;
	
	@Test
	public void testInsertUser()
	{
		UserModel userModel = new UserModel();
		userModel.setUserName("wujun");
		for(int i=0;i<10;i++){						
			userService.insert(userModel);
		}
		
		
		
	}
}
