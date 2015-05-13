package com.letv.portal.controller.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.common.ZookeeperInfo;
import com.letv.portal.service.common.IZookeeperInfoService;
import com.letv.portal.timing.task.IBaseTimingTaskService;
@Controller
@RequestMapping("/timingTask")
public class TimingTaskController {
	
	@Autowired
	private IBaseTimingTaskService baseTimingTaskService;
	
	private final static Logger logger = LoggerFactory.getLogger(TimingTaskController.class);

	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.baseTimingTaskService.selectPageByParams(page, params));
		return obj;
	}
	
}
