package com.letv.portal.clouddb.controller;

import java.util.HashMap;
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

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.enumeration.HclusterStatus;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.task.TemplateTask;
import com.letv.portal.model.task.TemplateTaskDetail;
import com.letv.portal.model.task.service.ITemplateTaskChainService;
import com.letv.portal.model.task.service.ITemplateTaskDetailService;
import com.letv.portal.model.task.service.ITemplateTaskService;

/**Program Name: JobController <br>
 * Description:  任务的相关操作<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年9月9日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/task")
public class TaskController {
	@Autowired
	private ITemplateTaskService templateTaskService;
	@Autowired
	private ITemplateTaskDetailService templateTaskDetailService;
	@Autowired
	private ITemplateTaskChainService templateTaskChainService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskController.class);

	/**Methods Name: taskList <br>
	 * Description: 查询任务流列表<br>
	 * @author name: yaokuo
	 * @param 
	 * @param request
	 */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject taskList(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.templateTaskService.selectPageByParams(page, params));
		return obj;
	}
	/**Methods Name: taskUnitList <br>
	 * Description: 查询任务单元列表<br>
	 * @author name: yaokuo
	 * @param 
	 * @param request
	 */
	@RequestMapping(value ="/unit",method=RequestMethod.GET)   
	public @ResponseBody ResultObject taskUnitList(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.templateTaskDetailService.selectPageByParams(page, params));
		return obj;
	}
	/**Methods Name: taskDetail <br>
	 * Description: 查询任务流详情<br>
	 * @author name: yaokuo
	 * @param id
	 * @param request
	 */
	@RequestMapping(value ="/detail/{id}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject taskDetail(@PathVariable Long id,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.templateTaskChainService.selectByTemplateTaskId(id));
		return obj;
	}
	
	/**
     * Methods Name: templateTask <br>
     * Description: 保存templateTask信息<br>
     * @author name: yaokuo 
     * @param TemplateTask
     * @param request
     * @return
     */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResultObject saveTask(TemplateTask templateTaskModal, HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		this.templateTaskService.insert(templateTaskModal);
		return obj;
	}
	/**
	 * Methods Name: templateTask <br>
	 * Description: 保存templateTaskUnit信息<br>
	 * @author name: yaokuo 
	 * @param TemplateTask
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/unit",method = RequestMethod.POST)
	public @ResponseBody ResultObject saveTaskUnit(TemplateTaskDetail templateTaskDetailModal, HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		this.templateTaskDetailService.insert(templateTaskDetailModal);
		return obj;
	}
}
