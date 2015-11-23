package com.letv.portal.controller.cloudvm;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;

@Controller
@RequestMapping("/ecs/region")
public class CloudvmRegionController {

	@Resource
	private ICloudvmRegionService cloudvmRegionService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody ResultObject list(Page page,
			HttpServletRequest request, ResultObject obj) {
		Map<String, Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.cloudvmRegionService.selectPageByParams(page, params));
		return obj;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public @ResponseBody ResultObject get(Long id, ResultObject obj) {
		obj.setData(this.cloudvmRegionService.selectById(id));
		return obj;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResultObject add(String code, String displayName,
			ResultObject result) {
		this.cloudvmRegionService.add(code, displayName);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResultObject delete(Long id, ResultObject result) {
		this.cloudvmRegionService.remove(id);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject edit(Long id, String code,
			String displayName, ResultObject result) {
		this.cloudvmRegionService.edit(id, code, displayName);
		return result;
	}
}
