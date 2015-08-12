package com.letv.portal.controller.cloudocs;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.service.adminoplog.ClassAoLog;
import com.letv.portal.service.cbase.ICbaseBucketService;

@ClassAoLog(module="OCS管理")
@Controller
@RequestMapping("/ocs")
public class OcsController {
	
	@Autowired
	private ICbaseBucketService cbaseBucketService;
	
	private final static Logger logger = LoggerFactory.getLogger(OcsController.class);

	/**
	  * @Title: list
	  * @Description: 获取指定页数指定条数的ocsServer列表
	  * @param page
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月26日 下午1:47:31
	  */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.cbaseBucketService.selectPageByParams(page, params));
		return obj;
	}
	
}
