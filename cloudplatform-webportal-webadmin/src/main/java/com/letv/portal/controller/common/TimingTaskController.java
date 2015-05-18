package com.letv.portal.controller.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.letv.common.util.StringUtil;
import com.letv.portal.enumeration.TimingTaskExecType;
import com.letv.portal.model.common.ZookeeperInfo;
import com.letv.portal.model.gce.GceImage;
import com.letv.portal.model.timing.task.BaseTimingTask;
import com.letv.portal.service.common.IZookeeperInfoService;
import com.letv.portal.timing.task.IBaseTimingTaskService;
@Controller
@RequestMapping("/timingTask")
public class TimingTaskController {
	
	@Autowired
	private IBaseTimingTaskService baseTimingTaskService;
	@Autowired
	private IBaseTimingTaskService pythonTimingTaskService;
	
	private final static Logger logger = LoggerFactory.getLogger(TimingTaskController.class);

	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.baseTimingTaskService.selectPageByParams(page, params));
		return obj;
	}
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject addTimingTask(BaseTimingTask baseTimingTask,HttpServletRequest request) {
		if(StringUtils.isEmpty(baseTimingTask.getUrl()) || null == baseTimingTask.getType()|| StringUtils.isEmpty(baseTimingTask.getTimeInterval()))
			throw new ValidateException("参数不合法");
			
		ResultObject obj = new ResultObject();
		this.pythonTimingTaskService.insert(baseTimingTask);
		return obj;
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)   
	public @ResponseBody ResultObject delTimingTask(@PathVariable Long id,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		if(id == null)
			throw new ValidateException("参数不合法");
		BaseTimingTask baseTimingTask = this.pythonTimingTaskService.selectById(id);
		if(baseTimingTask == null)
			throw new ValidateException("参数不合法");
		this.pythonTimingTaskService.delete(baseTimingTask);
		return obj;
	}
}
