package com.letv.portal.service.letvcloud;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.factory.CacheFactory;
import com.letv.portal.constant.Constants;
import com.letv.portal.dao.letvcloud.BillUserAmountMapper;
import com.letv.portal.dao.letvcloud.BillUserBillingMapper;
import com.letv.portal.letvcloud.bill.vo.BillMonthBilling;
import com.letv.portal.model.letvcloud.BillUserAmount;
import com.letv.portal.model.letvcloud.BillUserBilling;

/**
 * Created by chenliusong on 2015/6/29.
 */
@Service
public class BillUserServiceBillingImpl implements BillUserServiceBilling {
    Logger logger = LoggerFactory.getLogger(BillUserServiceBillingImpl.class);

    @Resource
    private BillUserBillingMapper billUserBillingDao;
    @Resource
    private BillUserAmountMapper billUserAmountMapper;
    private ICacheService<?> cacheService = CacheFactory.getCache();

    @Override
    public BillMonthBilling queryUserServiceBilling(Long userId, String month) {
        BillMonthBilling billMonthBilling = new BillMonthBilling();
        billMonthBilling.setBillingMonth(month);
        //获取用户订单
        BigDecimal totalMoney = new BigDecimal(0);
        List<BillUserBilling> billUserBillingList = billUserBillingDao.getUserBillings(userId, month);
        @SuppressWarnings("unchecked")
		Map<Long, String> productInfo = (Map<Long, String>) this.cacheService.get(Constants.PRODUCT_INFO_ID_NAME, null);
        for (BillUserBilling billUserBilling : billUserBillingList) {
        	billUserBilling.setProductName(productInfo.get(Long.parseLong(billUserBilling.getServiceCode())));
            totalMoney = totalMoney.add(new BigDecimal(billUserBilling.getBillingMoney()));
        }
        billMonthBilling.setTotalMoney(totalMoney.doubleValue()); //账单总额

        BillUserAmount billUserAmount = billUserAmountMapper.getUserAmout(userId);
        BigDecimal userMoney = billUserAmount.getAvailableAmount(); //资金账号金额
        if (userMoney.compareTo(new BigDecimal(0)) >= 0) { //已经结算金额
            billMonthBilling.setOweMoney(new BigDecimal(0).doubleValue()); //欠费金额
            billMonthBilling.setClearMoney(totalMoney.doubleValue());
        } else {
            billMonthBilling.setOweMoney(userMoney.doubleValue()); //欠费金额
            billMonthBilling.setClearMoney(totalMoney.add(userMoney).doubleValue());
        }
        billMonthBilling.setBillUserBillingList(billUserBillingList);
        return billMonthBilling;
    }


    /*@Override
    public List<BillMonthDetailBilling> queryUserServiceDetailBilling(String orderId, String month) {
        List<BillMonthDetailBilling> billMonthDetailBillingList = billUserBillingDao.getUserBillingDetails(orderId, month);
        return billMonthDetailBillingList;
    }*/

    @Override
    public List<String> getUserBillingYears(Long userId) {
        return billUserBillingDao.getUserBillingYears(userId);
    }

    @Override
    public List<String> getUserBillingMonths(String year) {
        List<String> monthList = new ArrayList<String>();
        DateTime dateTime = new DateTime();
        DateTime firstMonth = dateTime.withDayOfMonth(1).withMonthOfYear(1);

        DateTime lastMonth = dateTime.monthOfYear().getDateTime();
        if (!dateTime.toString("yyyy").equals(year)) {
            int difYear = new Integer(dateTime.toString("yyyy")) - new Integer(year);
            firstMonth = dateTime.withDayOfMonth(1).withMonthOfYear(1).minusYears(difYear);
            lastMonth = firstMonth.plusYears(1);
        }
        while (firstMonth.isBefore(lastMonth)) {
            monthList.add(firstMonth.toString("yyyyMM"));
            firstMonth = firstMonth.plusMonths(1);
        }
        Collections.reverse(monthList);
        return monthList;
    }


	@Override
	public void add(Long userId,String serviceCode,Long orderId,String billingMonth,String billingMoney) {
		BillUserBilling billUserBilling = new BillUserBilling();
		billUserBilling.setUserId(userId);
		billUserBilling.setServiceCode(serviceCode);
		billUserBilling.setOrderId(orderId);
		billUserBilling.setBillingMonth(billingMonth);
		billUserBilling.setBillingMoney(billingMoney);
		billUserBilling.setBillingId(UUID.randomUUID().toString());
		this.billUserBillingDao.insert(billUserBilling);
		
	}
}
