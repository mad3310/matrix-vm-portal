package com.letv.portal.clouddb.controller;

import org.junit.Test;



import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.UserModel;
//import com.letv.portal.model.UserModel;
import com.letv.portal.service.IUserService;


public class UserLoginTest extends AbstractTest{

	@Autowired
	private IUserService userService;
	
	@Test
	public void testInsertUser()
	{
		try {  
//			String userNamePassport ="lihanlin1@letv.com";
			UserModel userModel = new UserModel();
			userModel.setUserName("wujun");
			userModel.setPassportId("22");
			userService.insert(userModel);
			
			UserModel userModel1 = new UserModel();
			userModel1.setUserName("wujun2");
			userModel1.setPassportId("22");
			userService.insert(userModel1);
			System.out.print("xx");

		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
}
