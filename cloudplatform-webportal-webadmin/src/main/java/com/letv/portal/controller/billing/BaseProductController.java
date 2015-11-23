package com.letv.portal.controller.billing;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.model.base.BasePrice;
import com.letv.portal.model.product.Product;

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

	/**Methods Name: list <br>
	 * Description: 产品分页列表<br>
	 * @author name: liuhao1
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@ModelAttribute Page page,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		obj.setData(page);
		return obj;
	}
	
	/**Methods Name: save <br>
	 * Description: 保存产品相关信息<br>
	 * @author name: liuhao1
	 * @param product
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject save(@ModelAttribute Product product) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**Methods Name: update <br>
	 * Description: 更新产品信息<br>
	 * @author name: liuhao1
	 * @param id
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public @ResponseBody ResultObject update(@PathVariable Long id,@ModelAttribute Product product) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**Methods Name: delete <br>
	 * Description: 删除产品信息<br>
	 * @author name: liuhao1
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody ResultObject delete(@PathVariable Long id) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**Methods Name: configElement <br>
	 * Description: 配置产品元素<br>
	 * @author name: liuhao1
	 * @param elementIds
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/element", method=RequestMethod.POST)
	public @ResponseBody ResultObject configElement(@RequestParam(value="elementIds[]") int[] elementIds,@RequestParam Long id) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**Methods Name: configPrice <br>
	 * Description: 配置产品基础价格<br>
	 * @author name: liuhao1
	 * @param prices
	 * @param hclusterId
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/price", method=RequestMethod.POST)
	public @ResponseBody ResultObject configPrice(@RequestParam(value="elementIds[]") BasePrice[] prices,@RequestParam Long hclusterId,@RequestParam Long id) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
}
