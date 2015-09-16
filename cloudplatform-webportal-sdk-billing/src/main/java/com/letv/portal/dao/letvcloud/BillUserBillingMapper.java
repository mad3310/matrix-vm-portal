package com.letv.portal.dao.letvcloud;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.letv.portal.letvcloud.bill.vo.BillMonthDetailBilling;
import com.letv.portal.model.letvcloud.BillUserBilling;

/**
 * Created by chenliusong on 2015/6/29.
 */
	public interface BillUserBillingMapper {
    List<BillUserBilling> getUserBillings(@Param(value = "userId") Long userId, @Param(value = "billingMonth") String billingMonth);
    List<BillMonthDetailBilling> getUserBillingDetails(@Param(value = "orderId")String orderId,@Param(value = "month")String month);

    List<String> getUserBillingYears(Long userId);
}
