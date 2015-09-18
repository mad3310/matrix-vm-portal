package com.letv.portal.letvcloud;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
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

	@Test
	public void testCreateUserAmount() {
		Long userId = 000000L;
		this.billUserAmountService.createUserAmount(userId);
	}
	
  
}
