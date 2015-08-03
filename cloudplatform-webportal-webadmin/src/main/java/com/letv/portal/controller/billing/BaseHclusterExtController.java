package com.letv.portal.controller.billing;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;

/**Program Name: BaseProductController <br>
 * Description:  地域额外配置<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/billing/hcluster")
public class BaseHclusterExtController {
	
	private final static Logger logger = LoggerFactory.getLogger(BaseHclusterExtController.class);

	/**Methods Name: list <br>
	 * Description: 地域分页列表<br>
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
	
	/**Methods Name: configPrice <br>
	 * Description: 配置地域基础价格内容<br>
	 * @author name: liuhao1
	 * @param elementIds
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/price", method=RequestMethod.POST)
	public @ResponseBody ResultObject configPrice(@RequestParam(value="priceIds[]") Long[] priceIds,@RequestParam Long id) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**Methods Name: configProduct <br>
	 * Description: 配置地域产品<br>
	 * @author name: liuhao1
	 * @param productIds
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/product", method=RequestMethod.POST)
	public @ResponseBody ResultObject configProduct(@RequestParam(value="productIds[]") Long[] productIds,@RequestParam Long id) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
}
