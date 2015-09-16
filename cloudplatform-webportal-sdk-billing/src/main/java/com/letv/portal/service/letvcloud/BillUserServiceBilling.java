package com.letv.portal.service.letvcloud;

import com.letv.portal.letvcloud.bill.vo.BillMonthBilling;
import com.letv.portal.letvcloud.bill.vo.BillMonthDetailBilling;

import java.util.List;

/**
 * Created by chenliusong on 2015/6/28.
 * 用户账单接口
 */
public interface BillUserServiceBilling {
    /**
     * 根据用户Id、月份查询用户账单
     * @param userId
     * @param month
     * @return
     */
    BillMonthBilling queryUserServiceBilling(Long userId,String month);

    /**
     * 根据订单Id、月份查询用户账单详细信息
     * @param orderId
     * @param month
     * @return
     */
    List<BillMonthDetailBilling> queryUserServiceDetailBilling(String orderId,String month);

    /**
     * 查询用户的账单年份
     *
     * @param userId
     * @return
     */
    List<String> getUserBillingYears(Long userId);

    /**
     * 根据年份查询用户账单月
     *
     * @param year
     * @return
     */
    List<String> getUserBillingMonths(String year);
}
