package com.letv.portal.controller.cloudgce;

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
import com.letv.portal.proxy.IGceProxy;
import com.letv.portal.service.gce.IGceClusterService;

@Controller
@RequestMapping("/gce/cluster")
public class GceClusterController {
	
	@Autowired
	private IGceProxy gceClusterProxy;
	@Autowired
	private IGceClusterService gceClusterService;
	
	
	private final static Logger logger = LoggerFactory.getLogger(GceClusterController.class);
	

	/**
	  * @Title: list
	  * @Description: 获取gce集群列表
	  * @param page
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月25日 上午11:31:44
	  */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.gceClusterService.selectPageByParams(page, params));
		return obj;
	}
	
	
}
