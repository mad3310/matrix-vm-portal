package com.letv.portal.letvcloud;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.letvcloud.bill.vo.BillMonthBilling;
import com.letv.portal.letvcloud.bill.vo.BillMonthDetailBilling;
import com.letv.portal.service.letvcloud.BillUserServiceBilling;


public class TestBillUserServiceBilling extends AbstractTest {

	@Autowired
	private BillUserServiceBilling billUserServiceBilling;
	
	private final static Logger logger = LoggerFactory.getLogger(TestBillUserServiceBilling.class);
	
	private final static Long userId = 000001L;
	private final static String orderId = "1";
	private final static String month = "201509";
	private final static String year = "2015";
	
    /**
     *  
     * 根据用户Id、月份查询用户账单
    BillMonthBilling queryUserServiceBilling(Long userId,String month);
     * 根据订单Id、月份查询用户账单详细信息
    List<BillMonthDetailBilling> queryUserServiceDetailBilling(String orderId,String month);
     * 查询用户的账单年份
    List<String> getUserBillingYears(Long userId);
     * 根据年份查询用户账单月
    List<String> getUserBillingMonths(String year);
    
     */
	@Test
	public void testQueryUserServiceBilling() {
		BillMonthBilling billMonthBilling = this.billUserServiceBilling.queryUserServiceBilling(userId, month);
		Assert.assertNotNull(billMonthBilling);
		logger.info("billMothBilling detail:{},{}",billMonthBilling.getBillingMonth(),billMonthBilling.getTotalMoney());
	}
	
	/*@Test  暂不提供此方法
	public void testQueryUserServiceDetailBilling() {
		List<BillMonthDetailBilling> details = this.billUserServiceBilling.queryUserServiceDetailBilling(orderId, month);
		Assert.assertNotNull(details);
		logger.info("detail count:{}",details.size());
	}*/
	
	@Test
	public void testGetUserBillingYears() {
		List<String> userBillingYears = this.billUserServiceBilling.getUserBillingYears(userId);
		Assert.assertNotNull(userBillingYears);
		logger.info("detail count:{}",userBillingYears.size());
	}
	
	@Test
	public void testGetUserBillingMonths() {
		List<String> userBillingMonths = this.billUserServiceBilling.getUserBillingMonths(year);
		Assert.assertNotNull(userBillingMonths);
		logger.info("detail count:{}",userBillingMonths.size());
	}
  
}
