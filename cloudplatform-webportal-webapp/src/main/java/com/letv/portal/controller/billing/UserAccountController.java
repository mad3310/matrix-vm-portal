package com.letv.portal.controller.billing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.letvcloud.BillUserAmount;
import com.letv.portal.service.letvcloud.BillUserAmountService;

/**Program Name: BaseProductController <br>
 * Description:  基础产品<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/userAccount")
public class UserAccountController {
	
	private final static Logger logger = LoggerFactory.getLogger(UserAccountController.class);
	
	@Autowired
	private BillUserAmountService billUserAmountService;
	
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject account(@PathVariable Long userId, ResultObject obj) {
		BillUserAmount userAmount = this.billUserAmountService.getUserAmount(userId);
		obj.setData(userAmount);
		return obj;
	}
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject create(@PathVariable Long userId, ResultObject obj) {
		this.billUserAmountService.createUserAmount(userId);
		return obj;
	}
	
}
