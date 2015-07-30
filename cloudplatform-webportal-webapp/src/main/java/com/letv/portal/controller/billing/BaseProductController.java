package com.letv.portal.controller.billing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;

/**Program Name: BaseProductController <br>
 * Description:  基础产品<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/billing")
public class BaseProductController {
	
	private final static Logger logger = LoggerFactory.getLogger(BaseProductController.class);
	
	/**Methods Name: listHcluster <br>
	 * Description: 获取产品hcluster列表<br>
	 * @author name: liuhao1
	 * @return
	 */
	@RequestMapping(value="/hcluster",method=RequestMethod.GET)   
	public @ResponseBody ResultObject listHcluster() {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**Methods Name: listProduct <br>
	 * Description: 根据某地域及产品名称获取product列表<br>
	 * @author name: liuhao1
	 * @param hclusterId
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/product",method=RequestMethod.GET)   
	public @ResponseBody ResultObject listProduct(@RequestParam Long hclusterId,@RequestParam String name) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**Methods Name: listElement <br>
	 * Description: 产品基础元素<br>
	 * @author name: liuhao1
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/element",method=RequestMethod.GET)   
	public @ResponseBody ResultObject listElement(@RequestParam Long productId) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	
	/**Methods Name: listPrice <br>
	 * Description: 根据地域获取资源内容及基础价格列表<br>
	 * @author name: liuhao1
	 * @param hclusterId
	 * @return
	 */
	@RequestMapping(value="/price",method=RequestMethod.GET)   
	public @ResponseBody ResultObject listPrice(@RequestParam Long hclusterId) {
		ResultObject obj = new ResultObject();
		return obj;
	}
	
}
