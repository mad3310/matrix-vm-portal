package com.letv.portal.controller.cloudswift;

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

@Controller("ossSkip")
public class SkipController {

	@Autowired(required = false)
	private SessionServiceImpl sessionService;
//	@Autowired
//	private ICbaseBucketService cbaseBucketService;

	@RequestMapping(value = "/buy/oss", method = RequestMethod.GET)
	public ModelAndView toContainerCreate(ModelAndView mav) {
		mav.setViewName("/cloudswift/containerCreate");
		return mav;
	}

	@RequestMapping(value = "/list/oss", method = RequestMethod.GET)
	public ModelAndView toContainerList(ModelAndView mav) {
		mav.setViewName("/cloudswift/containerList");
		return mav;
	}

	@RequestMapping(value = "/detail/oss/{swiftId}", method = RequestMethod.GET)
	public ModelAndView containerDetail(@PathVariable Long swiftId, ModelAndView mav) {
		//isAuthorityCache(containerId);
		mav.addObject("swiftId", swiftId);
		mav.setViewName("/cloudswift/layout");
		return mav;
	}

	@RequestMapping(value = "/detail/ossBaseInfo/{swiftId}", method = RequestMethod.GET)
	public ModelAndView containerBaseInfo(@PathVariable Long swiftId,
			ModelAndView mav) {
		//isAuthorityCache(containerId);
		mav.addObject("swiftId", swiftId);
		mav.setViewName("/cloudswift/baseInfo");
		return mav;
	}
	@RequestMapping(value = "/detail/authorityInfo/{swiftId}", method = RequestMethod.GET)
	public ModelAndView containerauthInfo(@PathVariable Long swiftId,
			ModelAndView mav) {
		//isAuthorityCache(containerId);
		mav.addObject("swiftId", swiftId);
		mav.setViewName("/cloudswift/authorityInfo");
		return mav;
	}
	@RequestMapping(value = "/monitor/swift/{swiftId}", method = RequestMethod.GET)
	public ModelAndView toContainerMonitor(@PathVariable Long swiftId,
			ModelAndView mav) {
		//isAuthorityCache(containerId);
		mav.addObject("swiftId", swiftId);
		mav.setViewName("/cloudswift/monitor/containerMonitor");
		return mav;
	}

	@RequestMapping(value = "/detail/file/{swiftId}", method = RequestMethod.GET)//文件管理
	public ModelAndView toFileManage(@PathVariable Long swiftId,
			ModelAndView mav) {
		//isAuthorityCache(containerId);
		mav.addObject("swiftId", swiftId);
		mav.setViewName("/cloudswift/fileManage");
		return mav;
	}
	@RequestMapping(value ="/monitor/throughput/{swiftId}",method=RequestMethod.GET)
	public ModelAndView toThroughput(@PathVariable Long swiftId,ModelAndView mav){
		mav.addObject("swiftId",swiftId);
		mav.setViewName("/cloudswift/monitor/throughput");
		return mav;
	}
	@RequestMapping(value ="/monitor/reqfreq/{swiftId}",method=RequestMethod.GET)
	public ModelAndView toReqfreq(@PathVariable Long swiftId,ModelAndView mav){
		mav.addObject("swiftId",swiftId);
		mav.setViewName("/cloudswift/monitor/reqfreq");
		return mav;
	}

//	private void isAuthorityCache(Long containerId) {
//		if (containerId == null)
//			throw new ValidateException("参数不合法");
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("id", containerId);
//		map.put("createUser", sessionService.getSession().getUserId());
//		List<CbaseBucketModel> cbases = this.cbaseBucketService
//				.selectByMap(map);
//		if (cbases == null || cbases.isEmpty())
//			throw new ValidateException("参数不合法");
//	}

}
