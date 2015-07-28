package com.letv.portal.controller.billing;

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
import com.letv.portal.model.BaseProduct;

/**Program Name: BaseProductController <br>
 * Description:  基础产品<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/billing/product")
public class BaseProductController {
	
	private final static Logger logger = LoggerFactory.getLogger(BaseProductController.class);

	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject save(BaseProduct product) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public @ResponseBody ResultObject update(@PathVariable Long id,BaseProduct product) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
}
