package com.letv.portal.controller.billing;

import javax.servlet.http.HttpServletRequest;
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

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.enumeration.BillingType;
import com.letv.portal.model.base.BasePrice;
import com.letv.portal.service.base.IBasePriceService;


/**Program Name: BasePriceController <br>
 * Description:  基础价格<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/billing/price")
public class BasePriceController {
	
	private final static Logger logger = LoggerFactory.getLogger(BasePriceController.class);
	
	@Autowired
	private IBasePriceService basePriceService;

	/**Methods Name: list <br>
	 * Description: 基础价格分页列表，使用?key:value传入参数<br>
	 * @author name: liuhao1
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@ModelAttribute Page page,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		obj.setData(this.basePriceService.selectPageByParams(page, HttpUtil.requestParam2Map(request)));
		return obj;
	}
	
	/**Methods Name: save <br>
	 * Description: 保存基础价格<br>
	 * @author name: liuhao1
	 * @param price
	 * @return
	 */
	@RequestMapping(value = "/1/1",method=RequestMethod.GET)   
	public @ResponseBody ResultObject save(@ModelAttribute BasePrice price,BindingResult result) {
		if(result.hasErrors())
			return new ResultObject(result.getAllErrors());
		price.setBasePrice(1.243F);
		price.setBillingType(BillingType.BYTIME);
		price.setByTime("1h");
		this.basePriceService.insert(price);
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**Methods Name: update <br>
	 * Description: 更新基础价格<br>
	 * @author name: liuhao1
	 * @param id
	 * @param price
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public @ResponseBody ResultObject update(@PathVariable Long id,@Valid @ModelAttribute BasePrice price,BindingResult result) {
		if(result.hasErrors())
			return new ResultObject(result.getAllErrors());
		ResultObject obj = new ResultObject();
		this.basePriceService.updateBySelective(price);
		return obj;
	}
	
	/**Methods Name: delete <br>
	 * Description: 删除基础价格<br>
	 * @author name: liuhao1
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody ResultObject delete(@PathVariable Long id) {
		ResultObject obj = new ResultObject();
		this.basePriceService.delete(new BasePrice(id));
		return obj;
	}
	
	/**Methods Name: list <br>
	 * Description: 列表，使用?key:value传入参数<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		obj.setData(this.basePriceService.selectByMap(HttpUtil.requestParam2Map(request)));
		return obj;
	}
	
}
