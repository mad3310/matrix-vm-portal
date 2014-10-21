package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.service.IHclusterService;

@Controller
@RequestMapping("/hcluster")
public class HclusterController {
	@Resource
	private IHclusterService  hclusterService;
	
	private final static Logger logger = LoggerFactory.getLogger(HclusterController.class);

	/**
	 * Methods Name: list <br>
	 * Description: <br>
	 * @author name: wujun
	 * @param currentPage
	 * @param recordsPerPage
	 * @param dbName
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{currentPage}/{recordsPerPage}/{hclusterName}",method=RequestMethod.GET)  
	public @ResponseBody ResultObject list(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable String hclusterName,HttpServletRequest request) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRecordsPerPage(recordsPerPage);
	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("hclusterName", hclusterName);
		
		ResultObject obj = new ResultObject();
		obj.setData(this.hclusterService.findPagebyParams(params, page));
		return obj;
	}
	
	
	/**Methods Name: list <br>
	 * Description: 根据查询条件及分页信息获取分页数据  http://localhost:8080/host/list<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/{hclusterName}",method=RequestMethod.GET)   
	
	public @ResponseBody ResultObject list(@PathVariable String hclusterName,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("hclusterName", hclusterName);
		ResultObject obj = new ResultObject();
		obj.setData(this.hclusterService.selectByMap(map));
		return obj;
	}
	/**
	 * Methods Name: save <br>
	 * Description: 保存host信息
	 * @author name: wujun
	 * @param dav
	 * @param request
	 */
	@RequestMapping(method=RequestMethod.POST)   
	public void saveHost(HclusterModel hclusterModel,HttpServletRequest request) {
		try {
			this.hclusterService.insert(hclusterModel);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}	
	}	
   /**
    * Methods Name: delteHostByID <br>
    * Description: 删除host信息通过hostID
    * @author name: wujun
    * @param hv
    * @param request
    */
   @RequestMapping(value="/{hclusterId}",method=RequestMethod.DELETE)   
   public void delteHostByID(@PathVariable Long hclusterId,HttpServletRequest request) {
	   HclusterModel hclusterModel = new HclusterModel();
	try {
		hclusterModel.setId(hclusterId);
		this.hclusterService.delete(hclusterModel);
	} catch (Exception e) {
		logger.debug(e.getMessage());
	}	
   }	
  
  /**
   *  Methods Name: updateHost <br>
   * Description: 修改host的相关信息
   * @author name: wujun
   * @param hv
   * @param request
   */
  @RequestMapping(value="/{hostId}",method=RequestMethod.POST)   
  public void updateHost(HclusterModel hclusterModel) {

}	
	
}
