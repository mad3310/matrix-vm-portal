package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IBuildService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMclusterService;

@Controller
@RequestMapping("/mcluster")
public class MclusterController {
	
	@Autowired
	private IMclusterService mclusterService;
	@Autowired
	private IContainerService containerService;
	@Autowired
	private IBuildService buildService;
	@Autowired
	private IBuildTaskService buildTaskService;
	
	
	@Autowired
	private IMclusterProxy mclusterProxy;
	
	private final static Logger logger = LoggerFactory.getLogger(MclusterController.class);
	
	
	/**Methods Name: toList <br>
	 * Description: page to mcluster list<br>
	 * @author name: liuhao1
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String toList(HttpServletRequest request,HttpServletResponse response) {
		return "/clouddb/mgr_mcluster_list";
	}
	
	/**Methods Name: list <br>
	 * Description: get mcluster list by page and params<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{currentPage}/{recordsPerPage}/{mclusterName}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable String mclusterName,ResultObject result) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mclusterName", mclusterName);
		result.setData(this.mclusterProxy.selectPageByParams(currentPage,recordsPerPage,map));
		return result;
	}	

	/**Methods Name: getContainers <br>
	 * Description: get containers by mclusterId<br>
	 * @author name: liuhao1
	 * @param clusterId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{clusterId}",method=RequestMethod.GET)
	public ModelAndView getContainers(@PathVariable Long mclusterId,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<ContainerModel> containers = this.containerService.selectByClusterId(mclusterId);
		mav.addObject("containers", containers);
		mav.setViewName("/clouddb/mgr_mcluster_info");
		return mav;
	}
	
	/**Methods Name: detail <br>
	 * Description: get detail by id<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{mclusterId}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject detail(@PathVariable Long mclusterId,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		obj.setData(this.buildService.selectByMclusterId(mclusterId));
		return obj;
	}
	
	@RequestMapping(value = "/", method=RequestMethod.POST)   
	public String build(MclusterModel mclusterModel,HttpServletRequest request) {
		mclusterModel.setCreateUser(Long.parseLong(request.getSession().getAttribute("userId").toString()));
//		mclusterModel.setId(Long.parseLong(UUID.randomUUID().toString()));
		this.buildTaskService.buildMcluster(mclusterModel,null);
		
		return "redirect:/mcluster/list";
	}
	
	@RequestMapping(value="/validate/{mclusterName}",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> validate(@PathVariable String mclusterName,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<DbModel> list = this.mclusterService.selectByClusterName(mclusterName);
		if(list.size()>0) {
			map.put("valid", false);
		} else {
			map.put("valid", true);
		}
		return map;
	}
}
