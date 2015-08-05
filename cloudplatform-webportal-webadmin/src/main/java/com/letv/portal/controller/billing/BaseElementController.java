package com.letv.portal.controller.billing;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.BaseElement;
import com.letv.portal.service.IBaseElementService;

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

	@Autowired
	private IBaseElementService baseElementService;
	
	/**Methods Name: pageList <br>
	 * Description: 基础元素分页列表<br>
	 * @author name: liuhao1
	 * @param page
	 * @param request
	 * @return
	 */
	/*@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject pageList(@ModelAttribute Page page,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		obj.setData(this.baseElementService.selectPageByParams(page, HttpUtil.requestParam2Map(request)));
		logger.info("pageList for Element begin---");
		return obj;
	}*/
	
	/**Methods Name: save <br>
	 * Description: 保存基础元素<br>
	 * @author name: liuhao1
	 * @param element
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject save(@Valid @ModelAttribute BaseElement element,BindingResult result) {
		if(result.hasErrors())
			return new ResultObject(result.getAllErrors());
		
		ResultObject obj = new ResultObject();
		this.baseElementService.insert(element);
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
		this.baseElementService.updateBySelective(element);
		return obj;
	}
	
	/**Methods Name: delete <br>
	 * Description: 删除基础元素<br>
	 * @author name: liuhao1
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)   
	public @ResponseBody ResultObject delete(@PathVariable Long id) {
		ResultObject obj = new ResultObject();
//		this.baseElementService.delete();
		return obj;
	}
	
	@RequestMapping(value="/all",method=RequestMethod.GET)   
	public @ResponseBody ResultObject all() {
		ResultObject obj = new ResultObject();
		obj.setData(this.baseElementService.selectByMap(null));
		return obj;
	}
	
}
