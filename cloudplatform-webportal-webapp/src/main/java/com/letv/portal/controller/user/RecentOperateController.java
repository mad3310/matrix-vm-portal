package com.letv.portal.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.operate.RecentOperate;
import com.letv.portal.service.operate.IRecentOperateService;


/**Program Name: RecentOperateController <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年9月22日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/operate")
public class RecentOperateController {
	
	@Autowired
	private IRecentOperateService recentOperateService;
	@Autowired
	private SessionServiceImpl sessionService;
	
	private final static Logger logger = LoggerFactory.getLogger(RecentOperateController.class);
	
	/**Methods Name: recentOperate <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject recentOperate(ResultObject obj, HttpServletRequest request){
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("createUser", this.sessionService.getSession().getUserId());
		List<RecentOperate> recentOperates = this.recentOperateService.selectRecentOperate(params);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("operate", recentOperates);
		ret.put("date", new Date());
		obj.setData(ret);
		return obj;
	}
	
}
