package com.letv.portal.controller.cloudcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.IDbService;

/**
 * Program Name: SkipController <br>
 * Description: 用于页面跳转 list、detail、form、……<br>
 * 
 * @author name: caiyinxiang <br>
 *         Written Date: 2014年10月8日 <br>
 *         Modified By: <br>
 *         Modified Date: <br>
 */
@Controller("cacheSkip")
public class SkipController {
	@Autowired(required = false)
	private SessionServiceImpl sessionService;
	@Autowired
	private IDbService dbService;

	/**
	 * Methods Name: cacheList<br>
	 * Description: 跳转cache列表
	 * 
	 * @author name: caiyinxiang
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list/cache", method = RequestMethod.GET)
	public ModelAndView toCacheList(ModelAndView mav) {
		mav.setViewName("/cloudcache/cacheList");
		return mav;
	}

	/**
	 * Methods Name: cacheDetail <br>
	 * Description: 跳转至cache详情<br>
	 * 
	 * @author name: caiyinxiang
	 * @param cacheId
	 * @param mav
	 * @return
	 */
	@RequestMapping(value ="/detail/baseInfo/{cacheId}",method=RequestMethod.GET)
	public ModelAndView tobaseInfo(@PathVariable Long cacheId,ModelAndView mav){
		mav.addObject("cacheId",cacheId);
		mav.setViewName("/cloudcache/baseInfo");
		return mav;
	}
	@RequestMapping(value = "/detail/cache/{cacheId}", method = RequestMethod.GET)
	public ModelAndView cacheDetail(@PathVariable Long cacheId, ModelAndView mav) {
		mav.addObject("cacheId", cacheId);
		mav.setViewName("/cloudcache/layout");
		return mav;
	}
//	监控页
	@RequestMapping(value ="/monitor/cacheLink/{cacheId}",method=RequestMethod.GET)
	public ModelAndView toMonitor(@PathVariable Long cacheId,ModelAndView mav){
		mav.addObject("cacheId",cacheId);
		mav.setViewName("/cloudcache/monitor/cacheMonitor");
		return mav;
	}
	
	@RequestMapping(value ="/monitor/InnoDB/buffer/{cacheId}",method=RequestMethod.GET)
	public ModelAndView toMonitorInnoDB(@PathVariable Long cacheId,ModelAndView mav){
		mav.addObject("cacheId",cacheId);
		mav.setViewName("/cloudcache/monitor/cacheMonitor");
		return mav;
	}
	
	@RequestMapping(value ="/monitor/QPS/TPS/{cacheId}",method=RequestMethod.GET)
	public ModelAndView toMonitorQPS(@PathVariable Long cacheId,ModelAndView mav){
		mav.addObject("cacheId",cacheId);
		mav.setViewName("/cloudcache/monitor/cacheMonitor");
		return mav;
	}
	@RequestMapping(value ="/list/cachemanage/{cacheId}",method=RequestMethod.GET)
	public ModelAndView toDbBackup(@PathVariable Long cacheId,ModelAndView mav){
		mav.addObject("cacheId",cacheId);
		mav.setViewName("/cloudcache/dataManage");
		return mav;
	}
	/**
	 * Methods Name: cacheCreate<br>
	 * Description: 跳转cache创建页面
	 * 
	 * @author name: caiyinxiang
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/detail/cacheCreate", method = RequestMethod.GET)
	public ModelAndView toCacheCreate(ModelAndView mav) {
		mav.setViewName("/cloudcache/cacheCreate");
		return mav;
	}

}
