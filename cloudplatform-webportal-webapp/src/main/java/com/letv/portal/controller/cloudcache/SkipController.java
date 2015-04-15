package com.letv.portal.controller.cloudcache;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value = "/detail/cacheCreate", method = RequestMethod.GET)
	public ModelAndView toCacheCreate(ModelAndView mav) {
		mav.setViewName("/cloudcache/cacheCreate");
		return mav;
	}

	@RequestMapping(value = "/list/cache", method = RequestMethod.GET)
	public ModelAndView toCacheList(ModelAndView mav) {
		mav.setViewName("/cloudcache/cacheList");
		return mav;
	}

	@RequestMapping(value = "/detail/cache/{cacheId}", method = RequestMethod.GET)
	public ModelAndView cacheDetail(@PathVariable Long cacheId, ModelAndView mav) {
		mav.addObject("cacheId", cacheId);
		mav.setViewName("/cloudcache/layout");
		return mav;
	}

	@RequestMapping(value = "/detail/cacheBaseInfo/{cacheId}", method = RequestMethod.GET)
	public ModelAndView cacheBaseInfo(@PathVariable Long cacheId,
			ModelAndView mav) {
		mav.addObject("cacheId", cacheId);
		mav.setViewName("/cloudcache/baseInfo");
		return mav;
	}

	@RequestMapping(value = "/monitor/cache/{cacheId}", method = RequestMethod.GET)
	public ModelAndView toCacheMonitor(@PathVariable Long cacheId,
			ModelAndView mav) {
		mav.addObject("cacheId", cacheId);
		mav.setViewName("/cloudcache/monitor/cacheMonitor");
		return mav;
	}

	@RequestMapping(value = "/detail/data/{cacheId}", method = RequestMethod.GET)
	public ModelAndView toDataManage(@PathVariable Long cacheId, ModelAndView mav) {
		mav.addObject("cacheId", cacheId);
		mav.setViewName("/cloudcache/dataManage");
		return mav;
	}

}
