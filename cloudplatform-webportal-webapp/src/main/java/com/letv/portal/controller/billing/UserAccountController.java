package com.letv.portal.controller.billing;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.letvcloud.BillUserAmount;
import com.letv.portal.service.letvcloud.BillUserAmountService;
import com.letv.portal.service.order.IOrderService;

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

	@RequestMapping(value="/balance/{userId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject account(@PathVariable Long userId, ResultObject obj) {
		BillUserAmount billUserAmount = this.billUserAmountService.getUserAmount(userId);
		DecimalFormat formatter = new DecimalFormat("0.00");
		if(null == billUserAmount) {
			obj.setData("0.00");
			return obj;
		}
		String userAmount = formatter.format(billUserAmount.getAvailableAmount().doubleValue());
		obj.setData(userAmount);
		return obj;
	}

	@RequestMapping(value="/order/un/{userId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject unReadOrder(@PathVariable Long userId, ResultObject obj) {
	   /* Map<String, Object> params = new HashMap<String,Object>();
	    params.put("userId", userId);
	    params.put("status", 0); //unRead
	    Integer count = this.orderService.selectByMapCount(params);*/
		obj.setData(0);
		return obj;
	}

}
