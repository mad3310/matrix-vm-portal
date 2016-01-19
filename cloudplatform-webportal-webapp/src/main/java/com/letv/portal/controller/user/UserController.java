package com.letv.portal.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.CookieUtil;
import com.letv.portal.controller.BaseController;
import com.letv.portal.model.common.UserModel;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.oauth.IUcService;


/**Program Name: UserController <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年9月16日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

	@Autowired
	private IUcService ucService;
	@Autowired
	private IUserService userService;
    @Autowired
    private SessionServiceImpl sessionService;

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

	/**Methods Name: userInfo <br>
	 * Description: 根据userId，获取用户信息<br>
	 * @author name: liuhao1
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody ResultObject userInfo(ResultObject obj,HttpServletResponse response) throws Exception{
        Long userId = sessionService.getSession().getUserId();
		UserModel userModel = this.userService.getUserById(userId);
		Map<String, Object> user = this.ucService.getUserByUserId(userModel.getUcId());
		if(user == null || user.isEmpty()) {
			obj.setData(userModel.getUserName());
			return obj;
		}
        CookieUtil.addCookieWithDomain(response, CookieUtil.COOKIE_KEY_HEAD_PORTRAIT, (String) user.get("userAvatar"), CookieUtil.MEMORY_MAX_AGE, CookieUtil.LCP_COOKIE_DOMAIN);
		obj.setData(user);
		return obj;
	}
	@RequestMapping(value="/message/un",method=RequestMethod.GET)
	public @ResponseBody ResultObject unReadMessage(ResultObject obj) throws Exception{
        Long userId = sessionService.getSession().getUserId();
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
