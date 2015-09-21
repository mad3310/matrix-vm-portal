package com.letv.portal.controller.billing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.letvcloud.BillUserInvoice;
import com.letv.portal.service.letvcloud.BillUserInvoiceService;

/**Program Name: BaseProductController <br>
 * Description:  基础产品<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/userInvoice/api")
public class UserInvoiceApiController {
	
	private final static Logger logger = LoggerFactory.getLogger(UserInvoiceApiController.class);
	
	@Autowired
	private BillUserInvoiceService billUserInvoiceService;
	
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject balance(BillUserInvoice billUserInvoice, ResultObject obj) {
		this.billUserInvoiceService.createUserInvoice(billUserInvoice);
		return obj;
	}
	
}
