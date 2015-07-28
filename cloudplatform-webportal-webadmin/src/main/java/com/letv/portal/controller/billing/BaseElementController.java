package com.letv.portal.controller.billing;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.model.BaseElement;

/**Program Name: BaseElementController <br>
 * Description:  基础元素<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/billing/element")
public class BaseElementController {
	
	private final static Logger logger = LoggerFactory.getLogger(BaseElementController.class);

	/**Methods Name: pageList <br>
	 * Description: 基础元素分页列表<br>
	 * @author name: liuhao1
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject pageList(@ModelAttribute Page page,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		obj.setData(page);
		return obj;
	}
	
	/**Methods Name: save <br>
	 * Description: 保存基础元素<br>
	 * @author name: liuhao1
	 * @param element
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject save(@ModelAttribute BaseElement element) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**Methods Name: update <br>
	 * Description: 更新基础元素<br>
	 * @author name: liuhao1
	 * @param id
	 * @param element
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST)   
	public @ResponseBody ResultObject update(@PathVariable Long id,@ModelAttribute BaseElement element) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
}
