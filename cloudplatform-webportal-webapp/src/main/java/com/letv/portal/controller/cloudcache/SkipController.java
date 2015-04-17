package com.letv.portal.controller.cloudcache;

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
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.service.cbase.ICbaseBucketService;

@Controller("cacheSkip")
public class SkipController {

	@Autowired(required = false)
	private SessionServiceImpl sessionService;
	@Autowired
	private ICbaseBucketService cbaseBucketService;

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
		isAuthorityCache(cacheId);
		mav.addObject("cacheId", cacheId);
		mav.setViewName("/cloudcache/layout");
		return mav;
	}

	@RequestMapping(value = "/detail/cacheBaseInfo/{cacheId}", method = RequestMethod.GET)
	public ModelAndView cacheBaseInfo(@PathVariable Long cacheId,
			ModelAndView mav) {
		isAuthorityCache(cacheId);
		mav.addObject("cacheId", cacheId);
		mav.setViewName("/cloudcache/baseInfo");
		return mav;
	}

	@RequestMapping(value = "/monitor/cache/{cacheId}", method = RequestMethod.GET)
	public ModelAndView toCacheMonitor(@PathVariable Long cacheId,
			ModelAndView mav) {
		isAuthorityCache(cacheId);
		mav.addObject("cacheId", cacheId);
		mav.setViewName("/cloudcache/monitor/cacheMonitor");
		return mav;
	}

	@RequestMapping(value = "/detail/data/{cacheId}", method = RequestMethod.GET)
	public ModelAndView toDataManage(@PathVariable Long cacheId,
			ModelAndView mav) {
		isAuthorityCache(cacheId);
		mav.addObject("cacheId", cacheId);
		mav.setViewName("/cloudcache/dataManage");
		return mav;
	}

	private void isAuthorityCache(Long cacheId) {
		if (cacheId == null)
			throw new ValidateException("参数不合法");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", cacheId);
		map.put("createUser", sessionService.getSession().getUserId());
		List<CbaseBucketModel> cbases = this.cbaseBucketService
				.selectByMap(map);
		if (cbases == null || cbases.isEmpty())
			throw new ValidateException("参数不合法");
	}

}
