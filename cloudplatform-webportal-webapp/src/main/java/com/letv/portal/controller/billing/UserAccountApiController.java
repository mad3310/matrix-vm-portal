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

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.model.letvcloud.BillUserAmount;
import com.letv.portal.service.letvcloud.BillUserAmountService;
import com.letv.portal.service.letvcloud.BillUserServiceBilling;
import com.letv.portal.service.order.IOrderService;

/**Program Name: BaseProductController <br>
 * Description:  基础产品<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/userAccount/api")
public class UserAccountApiController {
	
	private final static Logger logger = LoggerFactory.getLogger(UserAccountApiController.class);
	
	@Autowired
	private BillUserAmountService billUserAmountService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private BillUserServiceBilling billUserServiceBilling;
	
	@RequestMapping(value="/balance/{userId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject balance(@PathVariable Long userId, ResultObject obj) {
		BillUserAmount billUserAmount = this.billUserAmountService.getUserAmount(userId);
		DecimalFormat formatter = new DecimalFormat("0.000");// billUserAmount.getAvailableAmount().doubleValue()
	    String userAmount = formatter.format(billUserAmount.getAvailableAmount().doubleValue());
		obj.setData(userAmount);
		return obj;
	}
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject account(@PathVariable Long userId, ResultObject obj) {
		BillUserAmount billUserAmount = this.billUserAmountService.getUserAmount(userId);
		obj.setData(billUserAmount);
		return obj;
	}
	@RequestMapping(value="/recharge/record/{userId}/{currentPage}/{recordsPerPage}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject rechargeRecord(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable Long userId, ResultObject obj) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRecordsPerPage(recordsPerPage);
		obj.setData(this.billUserAmountService.getUserAmountRecord(page, userId));
		return obj;
	}
	
	@RequestMapping(value="/bill/{userId}/{month}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject monthBill(@PathVariable Long userId, @PathVariable String month,ResultObject obj) {
		obj.setData(this.billUserServiceBilling.queryUserServiceBilling(userId, month));
		return obj;
	}
	@RequestMapping(value="/billMonths/{year}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject billMonths(@PathVariable String year,ResultObject obj) {
		obj.setData(this.billUserServiceBilling.getUserBillingMonths(year));
		return obj;
	}
	@RequestMapping(value="/billYear/{userId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject billYear(@PathVariable Long userId,ResultObject obj) {
		obj.setData(this.billUserServiceBilling.getUserBillingYears(userId));
		return obj;
	}

}
