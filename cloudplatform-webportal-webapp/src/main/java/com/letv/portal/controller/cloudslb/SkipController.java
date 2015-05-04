package com.letv.portal.controller.cloudslb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.exception.ValidateException;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.model.DbModel;
import com.letv.portal.service.IDbService;

/**Program Name: SkipController <br>
 * Description:  用于页面跳转       list、detail、form、……<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月8日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller("slbSkip")
public class SkipController {
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	@Autowired
	private IDbService dbService;
	
	/**
	 * Methods Name: slbList<br>
	 * Description: 跳转负载均衡列表
	 * @author name: yaokuo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/list/slb",method=RequestMethod.GET)
	public ModelAndView toSlbList(ModelAndView mav){
		mav.setViewName("/cloudslb/slbList");
		return mav;
	}
	
	/**Methods Name: slbDetail <br>
	 * Description: 跳转至slb详情<br>
	 * @author name: yaokuo
	 * @param slbId
	 * @param mav
	 * @return
	 */
	@RequestMapping(value ="/detail/slb/{slbId}",method=RequestMethod.GET)
	public ModelAndView dbDetail(@PathVariable Long slbId,ModelAndView mav){
		//isAuthorityDb(slbId);
		mav.addObject("slbId",slbId);
		mav.setViewName("/cloudslb/layout");
		return mav;
	}
	
	/**
	 * Methods Name: slbInfo<br>
	 * Description: 跳转基本信息页面
	 * @author name: yaokuo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/detail/slbBaseInfo/{slbId}",method=RequestMethod.GET)
	public ModelAndView tobaseInfo(@PathVariable Long slbId,ModelAndView mav){
		//isAuthorityDb(slbId);
		mav.addObject("slbId",slbId);
		mav.setViewName("/cloudslb/baseInfo");
		return mav;
	}
	
	/**
	 * Methods Name: slbConfig<br>
	 * Description: 跳转配置页面
	 * @author name: yaokuo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/detail/slbConfig/{slbId}",method=RequestMethod.GET)
	public ModelAndView toSlbConfig(@PathVariable Long slbId,ModelAndView mav){
		//isAuthorityDb(slbId);
		mav.addObject("slbId",slbId);
		mav.setViewName("/cloudslb/slbConfig");
		return mav;
	}
	/**
	 * Methods Name: backendServer<br>
	 * Description: 管理后端服务器
	 * @author name: yaokuo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/detail/backend/server/{slbId}",method=RequestMethod.GET)
	public ModelAndView tobackendServer(@PathVariable Long slbId,ModelAndView mav){
		//isAuthorityDb(slbId);
		mav.addObject("slbId",slbId);
		mav.setViewName("/cloudslb/backendServer");
		return mav;
	}
	
	/**
	 * Methods Name: slbCreate<br>
	 * Description: 跳转slb创建页面
	 * @author name: yaokuo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/detail/slbCreate",method=RequestMethod.GET)
	public ModelAndView toSlbCreate(ModelAndView mav){
		mav.setViewName("/cloudslb/slbCreate");
		return mav;
	}
	@RequestMapping(value ="/monitor/slb/cpu/{slbId}",method=RequestMethod.GET)
	public ModelAndView toCpuUsed(@PathVariable Long slbId,ModelAndView mav){
		mav.addObject("slbId",slbId);
		mav.setViewName("/cloudslb/monitor/cpu");
		return mav;
	}
	@RequestMapping(value ="/monitor/slb/network/{slbId}",method=RequestMethod.GET)
	public ModelAndView toNetwork(@PathVariable Long slbId,ModelAndView mav){
		mav.addObject("slbId",slbId);
		mav.setViewName("/cloudslb/monitor/network");
		return mav;
	}
	@RequestMapping(value ="/monitor/slb/memory/{slbId}",method=RequestMethod.GET)
	public ModelAndView toMemory(@PathVariable Long slbId,ModelAndView mav){
		mav.addObject("slbId",slbId);
		mav.setViewName("/cloudslb/monitor/memory");
		return mav;
	}
	@RequestMapping(value ="/monitor/slb/disk/{slbId}",method=RequestMethod.GET)
	public ModelAndView toDisk(@PathVariable Long slbId,ModelAndView mav){
		mav.addObject("slbId",slbId);
		mav.setViewName("/cloudslb/monitor/disk");
		return mav;
	}
	private void isAuthorityDb(Long dbId) {
		if(dbId == null)
			throw new ValidateException("参数不合法");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", dbId);
		map.put("createUser", sessionService.getSession().getUserId());
		List<DbModel> dbs = this.dbService.selectByMap(map);
		if(dbs == null || dbs.isEmpty())
			throw new ValidateException("参数不合法");
	}
}
