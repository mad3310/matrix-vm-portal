package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMclusterService;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/mcluster")
public class MclusterController {
	
	@Resource
	private IMclusterService mclusterService;
	@Resource
	private IContainerService containerService;
	
	private final static Logger logger = LoggerFactory.getLogger(MclusterController.class);
	
	/**Methods Name: list <br>
	 * Description: 管理员根据查询条件及分页信息获取分页数据   http://localhost:8080/mcluster/<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list/{currentPage}/{recordsPerPage}/{mclusterName}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable String mclusterName,HttpServletRequest request) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRecordsPerPage(recordsPerPage);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("mclusterName", mclusterName);
		
		ResultObject obj = new ResultObject();
		obj.setData(this.mclusterService.findPagebyParams(params, page));
		return obj;
	}	
	
	/**Methods Name: save <br>
	 * Description: 保存mcluster<br>
	 * @author name: liuhao1
	 * @param mclusterModel
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)   //http://localhost:8080/api/mcluster/save
	public String save(MclusterModel mclusterModel, HttpServletRequest request) {
		
		if(StringUtils.isNullOrEmpty(mclusterModel.getId())) {
			mclusterModel.setCreateUser((String)request.getSession().getAttribute("userId"));
			mclusterModel.setStatus("1");
			this.mclusterService.insert(mclusterModel);
		} else {
			this.mclusterService.updateBySelective(mclusterModel);
		}
		
		return "redirect:/mcluster/list";
	}
	
	/**Methods Name: detail <br>
	 * Description: 根据id查找mcluster详情，获取container详情<br>
	 * @author name: liuhao1
	 * @param clusterId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/detail/{clusterId}",method=RequestMethod.GET) //http://localhost:8080/api/mcluster/getById/{clusterId}
	public ModelAndView getInfoById(@PathVariable String clusterId,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<ContainerModel> containers = this.containerService.selectByClusterId(clusterId);
		mav.addObject("containers", containers);
		mav.setViewName("/clouddb/mgr_mcluster_info");
		return mav;
	}
	
	@RequestMapping("/list")   //http://localhost:8080/mcluster/list
	public String toMgrList(HttpServletRequest request,HttpServletResponse response) {
		return "/clouddb/mgr_mcluster_list";
	}
}
