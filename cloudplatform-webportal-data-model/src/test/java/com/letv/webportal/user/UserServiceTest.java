/*
 * @Title: DictionaryServiceTest.java
 * @Package com.letv.mms.dictionary.test.service
 * @Description:字典service相关测试类
 * @author 陈光 
 * @date 2012-12-14 上午11:03:41
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-14                          
 */
package com.letv.webportal.user;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.util.Assert;

import com.letv.mms.base.AbstractTestCase;
import com.letv.portal.model.UserModel;
import com.letv.portal.service.IUserService;


public class UserServiceTest extends AbstractTestCase {
	@Resource
	private IUserService userService;
	
	@Test
	public void testSaveUser()
	{
		String userName = "zbz";
		UserModel userModel = new UserModel();
		userModel.setUserName(userName);
		userModel.setPassportId(userName);
		userService.insert(userModel);
		
		boolean result = userService.existUserByUserName(userName);
		Assert.isTrue(result);
	}

}
