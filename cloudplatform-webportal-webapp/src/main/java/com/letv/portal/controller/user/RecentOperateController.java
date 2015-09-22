package com.letv.portal.controller.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.RecentOperate;


/**Program Name: UserController <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年9月16日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/operate")
public class RecentOperateController {
	
	private final static Logger logger = LoggerFactory.getLogger(RecentOperateController.class);
	
	/**Methods Name: userInfo <br>
	 * Description: 根据当前session获取用户信息<br>
	 * @author name: liuhao1
	 * @param request
	 * @param response
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject recentOperate(ResultObject obj) throws Exception{
		List<RecentOperate> ros = new ArrayList<RecentOperate>();
		RecentOperate ro = new RecentOperate();
		ro.setAction("创建云主机");
		ro.setContent("TestECS");
		ro.setCreateTime(new Timestamp(System.currentTimeMillis()));
		ro.setCreateUser(0000L);
		for (int i = 0; i < 100; i++) {
			ros.add(ro);
		}
		obj.setData(ros);
		return obj;
	}
	
}
