package com.letv.portal.letvcloud;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.common.paging.impl.Page;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.letvcloud.BillRechargeRecord;
import com.letv.portal.model.letvcloud.BillUserAmount;
import com.letv.portal.service.letvcloud.BillUserAmountService;


public class TestBillUserAmountService extends AbstractTest {

	@Autowired
	private BillUserAmountService billUserAmountService;
	/**
	 * 
	//创建默认账户
    public void createUserAmount(Long userId) throws CommonException;
    //插入充值记录
    public String recharge(long userId,BigDecimal amount,int type);
    //充值成功更余额
    public long rechargeSuccess(Long userId,String tradeNum,String orderNum,BigDecimal amount);
    //获取某产品线所有欠费用户及开始欠费时间
    public Map<Long,Date> getAllUserArrears(String serviceCode);
    //获取用户余额相关信息
    BillUserAmount getUserAmount(Long userId);
    //获取用户充值记录
    Page getUserAmountRecord(Page pageInfo,Long userId);
    //获取用户充值记录根据交易号码
    BillRechargeRecord getRechargeRecord(String tradeNum);
    //获取用户账户信息
    Map<String, Object> getUserAmountState(long userId);
	 */
	
	private final static Logger logger = LoggerFactory.getLogger(TestBillUserAmountService.class);

	private final static Long userId = 000001L;
	private final static String tradeNum = "00001000010001";
	private final static String orderId = "order001";
	
	@Test
	public void testCreateUserAmount() {
		this.billUserAmountService.createUserAmount(userId);
	}
	
	@Test
	public void testGetUserAmount() {
		BillUserAmount userAmount = this.billUserAmountService.getUserAmount(userId);
		Assert.assertNotNull(userAmount);
		Assert.assertNotNull(userAmount.getAvailableAmount());
		logger.info("userAmount amount:{}",userAmount.getAvailableAmount());
	}

	@Test
	public void testRecharge() {
		String tradeNum = this.billUserAmountService.recharge(userId, BigDecimal.valueOf(100.00), 1);
		this.billUserAmountService.rechargeSuccess(userId, tradeNum, orderId, BigDecimal.valueOf(100.00));
	}
	
	@Test
	public void testGetUserAmountRecord(){
		Page page = new Page(0,10);
		Page userAmountRecords = this.billUserAmountService.getUserAmountRecord(page, userId);
		Assert.assertNotNull(userAmountRecords);
		logger.info("userAmountRecords total:{}",userAmountRecords.getTotalRecords());
	}
	
	@Test
	public void testGetRechargeRecord(){
		BillRechargeRecord rechargeRecord = this.billUserAmountService.getRechargeRecord(tradeNum);
		Assert.assertNotNull(rechargeRecord);
		logger.info("userAmountRecord amount:{}",rechargeRecord.getAmount());
	}
	
	@Test
	public void testGetUserAmountState(){
		Map<String, Object> userAmountState = this.billUserAmountService.getUserAmountState(userId);
		Assert.assertNotNull(userAmountState);
		for (Map.Entry<String, Object> entry : userAmountState.entrySet()) {
	        logger.info("userAmountState.key{},userAmountState.value{}",entry.getKey(),entry.getValue());
        }
	}
}
