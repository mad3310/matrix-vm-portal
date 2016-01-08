package com.letv.portal.controller.billing;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.controller.BaseController;
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
import com.letv.portal.service.letvcloud.BillUserServiceBilling;

/**Program Name: BaseProductController <br>
 * Description:  基础产品<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/userAccount")
public class UserAccountController{

	private final static Logger logger = LoggerFactory.getLogger(UserAccountController.class);

	@Autowired
	private BillUserAmountService billUserAmountService;
	@Autowired
	private BillUserServiceBilling billUserServiceBilling;
	@Autowired
	private SessionServiceImpl sessionService;

	@RequestMapping(value="/balance",method=RequestMethod.GET)
	public @ResponseBody ResultObject account(ResultObject obj) {
		Long userId = sessionService.getSession().getUserId();
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

	@RequestMapping(value="/order/un",method=RequestMethod.GET)
	public @ResponseBody ResultObject getUserBillingCount(ResultObject obj) {
		Long userId = sessionService.getSession().getUserId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	    Long count = this.billUserServiceBilling.getUserBillingCount(userId, sdf.format(new Date()));
		obj.setData(count);
		return obj;
	}

}
