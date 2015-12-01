package com.letv.portal.controller.billing.api;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.letv.portal.model.UserModel;
import com.letv.portal.service.IUserService;
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
import com.letv.portal.model.letvcloud.BillRechargeRecord;
import com.letv.portal.model.letvcloud.BillUserAmount;
import com.letv.portal.service.letvcloud.BillUserAmountService;
import com.letv.portal.service.letvcloud.BillUserServiceBilling;

/**Program Name: BaseProductController <br>
 * Description:  基础产品<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/api/userAccount")
public class UserAccountApiController {

	private final static Logger logger = LoggerFactory.getLogger(UserAccountApiController.class);

	@Autowired
	private BillUserAmountService billUserAmountService;
	@Autowired
	private IUserService userService;
	@Autowired
	private BillUserServiceBilling billUserServiceBilling;

	@RequestMapping(value="/balance/{ucId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject balance(@PathVariable Long ucId, ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		BillUserAmount billUserAmount = this.billUserAmountService.getUserAmount(userId);
		DecimalFormat formatter = new DecimalFormat("0.00");
		String userAmount = formatter.format(billUserAmount.getAvailableAmount().doubleValue());
		obj.setData(userAmount);
		return obj;
	}
	@RequestMapping(value="/{ucId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject account(@PathVariable Long ucId, ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		BillUserAmount billUserAmount = this.billUserAmountService.getUserAmount(userId);
		obj.setData(billUserAmount);
		return obj;
	}
	@RequestMapping(value="/createUserAmount/{ucId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject create(@PathVariable Long ucId, ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		this.billUserAmountService.createUserAmount(userId);
		return obj;
	}

	@RequestMapping(value="/recharge",method=RequestMethod.POST)
	public @ResponseBody ResultObject recharge(@RequestParam(required=true) Long ucId,@RequestParam(required=true) String chargeMoney,@RequestParam(required=true) int type, ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		String tradeNum = this.billUserAmountService.recharge(userId, new BigDecimal(chargeMoney), type);
		obj.setData(tradeNum);
		return obj;
	}
	@RequestMapping(value="/rechargeSuccess",method=RequestMethod.POST)
	public @ResponseBody ResultObject recharge(@RequestParam(required=true) Long ucId,@RequestParam(required=true) String tradeNum,@RequestParam(required=true) String orderId,@RequestParam(required=true) String chargeMoney, ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		long rechargeSuccess = this.billUserAmountService.rechargeSuccess(userId, tradeNum, orderId, new BigDecimal(chargeMoney));
		obj.setData(rechargeSuccess);
		return obj;
	}
	@RequestMapping(value="/recharge/{tradeNum}",method=RequestMethod.GET)
	public @ResponseBody ResultObject rechargeRecord(@PathVariable String tradeNum, ResultObject obj) {
		BillRechargeRecord rechargeRecord = this.billUserAmountService.getRechargeRecord(tradeNum);
		obj.setData(rechargeRecord);
		return obj;
	}
	@RequestMapping(value="/recharge/{ucId}/{currentPage}/{recordsPerPage}",method=RequestMethod.GET)
	public @ResponseBody ResultObject rechargeRecord(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable Long ucId, ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRecordsPerPage(recordsPerPage);
		page = this.billUserAmountService.getUserAmountRecord(page, userId);
		obj.setData(page);
		return obj;
	}

	@RequestMapping(value="/bill/{ucId}/{month}",method=RequestMethod.GET)
	public @ResponseBody ResultObject monthBill(@PathVariable Long ucId, @PathVariable String month,ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		obj.setData(this.billUserServiceBilling.queryUserServiceBilling(userId, month));
		return obj;
	}
	@RequestMapping(value="/billMonths/{year}",method=RequestMethod.GET)
	public @ResponseBody ResultObject billMonths(@PathVariable String year,ResultObject obj) {
		obj.setData(this.billUserServiceBilling.getUserBillingMonths(year));
		return obj;
	}
	@RequestMapping(value="/billYear/{ucId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject billYear(@PathVariable Long ucId,ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		obj.setData(this.billUserServiceBilling.getUserBillingYears(userId));
		return obj;
	}

}
