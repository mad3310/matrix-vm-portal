package com.letv.portal.clouddb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.exception.ValidateException;
import com.letv.common.result.ResultObject;
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
@Controller
public class SkipController {
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	@Autowired
	private IDbService dbService;
	
	/**
	 * Methods Name: dbInfo<br>
	 * Description: 跳转基本信息页面
	 * @author name: 姚阔
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/detail/baseInfo",method=RequestMethod.GET)
	public ModelAndView tobaseInfo(ModelAndView mav){
		mav.setViewName("/clouddb/baseInfo");
		return mav;
	}

	/**
	 * Methods Name: dbInfo<br>
	 * Description: 跳转数据库列表
	 * @author name: 姚阔
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="/list/db",method=RequestMethod.GET)
	public ModelAndView toDbList(ModelAndView mav){
		mav.setViewName("/clouddb/dbList");
		return mav;
	}
	
	@RequestMapping(value ="/jettyMonitor",method=RequestMethod.GET)
	public @ResponseBody ResultObject jettyMonitor(ResultObject obj){
		return obj;
	}
}
