package com.letv.portal.controller.cloudslb;

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

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.slb.SlbConfig;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.service.slb.ISlbConfigService;
import com.letv.portal.service.slb.ISlbServerService;

@Controller
@RequestMapping("/slbConfig")
public class SlbConfigController {
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Autowired
	private ISlbConfigService slbConfigService;
	@Autowired
	private ISlbServerService slbServerService;
	
	private final static Logger logger = LoggerFactory.getLogger(SlbConfigController.class);
	
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("createUser", sessionService.getSession().getUserId());
		obj.setData(this.slbConfigService.selectPageByParams(page, params));
		return obj;
	}
	
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject save(SlbConfig slbConfig,ResultObject obj) {
		if(slbConfig == null || slbConfig.getSlbId() == null)
			throw new ValidateException("参数不合法");
		isAuthoritySlb(slbConfig.getSlbId());
		slbConfig.setCreateUser(this.sessionService.getSession().getUserId());
		this.slbConfigService.insert(slbConfig);
		return obj;
	}
	
	
	private void isAuthoritySlb(Long id) {
		if(id == null)
			throw new ValidateException("参数不合法");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("createUser", sessionService.getSession().getUserId());
		List<SlbServer> slbs = this.slbServerService.selectByMap(map);
		if(slbs == null || slbs.isEmpty())
			throw new ValidateException("参数不合法");
	}
}
