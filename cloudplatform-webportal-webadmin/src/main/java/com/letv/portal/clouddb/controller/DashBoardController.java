package com.letv.portal.clouddb.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.proxy.IDashBoardProxy;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {
	
	@Resource
	private IDashBoardProxy dashBoardProxy;

	
	private final static Logger logger = LoggerFactory.getLogger(DashBoardController.class);
	
	/**Methods Name: list <br>
	 * Description: 根据mclusterId获取相关container列表<br>
	 * @author name: liuhao1
	 * @param mclusterId
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/statistics",method=RequestMethod.GET)
	public @ResponseBody ResultObject list(ResultObject result) {
		result.setData(this.dashBoardProxy.selectManagerResource());
		return result;
	}
}
