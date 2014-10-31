package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.List;
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
import com.letv.portal.model.HostModel;
import com.letv.portal.proxy.IHostProxy;
import com.letv.portal.service.IHostService;

@Controller
@RequestMapping("/host")
public class HostController {
	
	@Resource
	private IHostService hostService;
	@Resource
	private IHostProxy hostProxy;
	
	private final static Logger logger = LoggerFactory.getLogger(HostController.class);

	/**
	 * Methods Name: list <br>
	 * Description: 分页<br>
	 * @author name: wujun
	 * @param currentPage
	 * @param recordsPerPage
	 * @param hostName
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{currentPage}/{recordsPerPage}/{hostName}",method=RequestMethod.GET)  
	public @ResponseBody ResultObject list(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable String hostName,HttpServletRequest request) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRecordsPerPage(recordsPerPage);
	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("hostName", hostName);
		
		ResultObject obj = new ResultObject();
		obj.setData(this.hostService.findPagebyParams(params, page));
		return obj;
	}
	
	/**
	 * Methods Name: validate <br>
	 * Description: 校验hostName是否存在
	 * @author name: wujun
	 * @param dbUserModel
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/hostName/validate",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> validateName(String hostName,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		HostModel hostModel = new HostModel();
		hostModel.setHostName(hostName);
		List<HostModel> list = this.hostService.selectByIpOrHostName(hostModel);
		map.put("valid", list.size()>0?false:true);
		return map;
	}
	/**
	 * Methods Name: validate <br>
	 * Description: 校验hostIp是否存在
	 * @author name: wujun
	 * @param dbUserModel
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/hostIp/validate",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> validateIp(String hostIp,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		HostModel hostModel = new HostModel();
		hostModel.setHostIp(hostIp);
		List<HostModel> list = this.hostService.selectByIpOrHostName(hostModel);
		map.put("valid", list.size()>0?false:true);
		return map;
	}
	/**
	 * Methods Name: save <br>
	 * Description: 保存host信息
	 * @author name: wujun
	 * @param dav
	 * @param request   descn
	 */
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject saveHost(HostModel hostModel,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		try {		
			this.hostProxy.insertAndPhyhonApi(hostModel);			
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}	
		return obj;
	}	
	/**
	 * Methods Name: list <br>
	 * Description: <br>
	 * @author name: wujun
	 * @param hclusterId
	 * @return
	 */
	@RequestMapping(value="/{hclusterId}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@PathVariable Long hclusterId) {
		ResultObject obj = new ResultObject();
		obj.setData(this.hostService.selectByHclusterId(hclusterId));
		return obj;
	}
   /**
    * Methods Name: delteHostByID <br>
    * Description: 删除host信息通过hostID
    * @author name: wujun
    * @param hv
    * @param request
    */
   @RequestMapping(value="/{hostId}",method=RequestMethod.DELETE)   
   public @ResponseBody ResultObject delteHostByID(@PathVariable Long hostId,HttpServletRequest request) {
	ResultObject obj = new ResultObject();
	HostModel hostModel = new HostModel();
	try {
		hostModel.setId(hostId);
		this.hostService.delete(hostModel);
	} catch (Exception e) {
		logger.debug(e.getMessage());
	}	
	return obj;
   }	
  
  /**
   *  Methods Name: updateHost <br>
   * Description: 修改host的相关信息
   * @author name: wujun
   * @param hv
   * @param request
   */
  @RequestMapping(value="/{hostId}",method=RequestMethod.POST)   
  public @ResponseBody ResultObject updateHost(HostModel hostModel) {
	ResultObject obj = new ResultObject();
	try {
		this.hostService.update(hostModel);
	} catch (Exception e) {
		logger.debug(e.getMessage());
	}	
	return obj;
}	
  /**
   * Methods Name: validateIp <br>
   * Description: 判断host上是否container存在<br>
   * @author name: wujun
   * @param hostIp
   * @param request
   * @return
   */
	@RequestMapping(value="/isExistContainerOnHost/validate",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> validateExistContainer(Long id,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		HostModel hostModel = new HostModel();
		hostModel.setId(id);
		List<HostModel> list = this.hostService.isExistContainerOnHost(hostModel);
		map.put("valid", list.size()>0?false:true);
		return map;
	}
}
