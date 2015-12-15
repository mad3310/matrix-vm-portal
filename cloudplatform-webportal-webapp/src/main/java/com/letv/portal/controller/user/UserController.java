package com.letv.portal.controller.user;

import com.letv.common.result.ResultObject;
import com.letv.common.session.Session;
import com.letv.common.util.CookieUtil;
import com.letv.common.util.SessionUtil;
import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.factory.CacheFactory;
import com.letv.portal.model.UserModel;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.oauth.IUcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**Program Name: UserController <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年9月16日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUcService ucService;
	@Autowired
	private IUserService userService;

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

	/**Methods Name: userInfo <br>
	 * Description: 根据userId，获取用户信息<br>
	 * @author name: liuhao1
	 * @param userId
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject userInfo(@PathVariable Long userId,ResultObject obj,HttpServletResponse response) throws Exception{
		if(null ==userId) {
			obj.setResult(0);
			return obj;
		}
		UserModel userModel = this.userService.getUserById(userId);
		Map<String, Object> user = this.ucService.getUserByUserId(userModel.getUcId());
		if(user == null || user.isEmpty()) {
			obj.setData(userModel.getUserName());
			return obj;
		}
        CookieUtil.addCookieWithDomain(response, CookieUtil.COOKIE_KEY_HEAD_PORTRAIT, (String) user.get("userAvatar"), CookieUtil.USER_LOGIN_MAX_AGE, CookieUtil.LCP_COOKIE_DOMAIN);
		obj.setData(user);
		return obj;
	}
	@RequestMapping(value="/message/un/{userId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject unReadMessage(@PathVariable Long userId,ResultObject obj) throws Exception{
		if(null ==userId) {
			obj.setData(0);
			return obj;
		}
		Long ucId = this.userService.getUcIdByUserId(userId);
		Map<String, Object> unReadMessage = this.ucService.getUnReadMessage(ucId);
		if(unReadMessage == null || unReadMessage.isEmpty()) {
			obj.setData(0);
			return obj;
		}
		obj.setData(unReadMessage.get("totalCount"));
		return obj;
	}
}
