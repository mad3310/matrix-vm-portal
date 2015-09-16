package com.letv.portal.service.letvcloud;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.letv.common.exception.CommonException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.model.letvcloud.BillRechargeRecord;
import com.letv.portal.model.letvcloud.BillUserAmount;

/**
 * Created by wanglei14 on 2015/6/28.
 */
public interface BillUserAmountService {
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

    /**
     * 获取用户账户信息
     *
     * @param userId
     * @return
     */
    Map<String, Object> getUserAmountState(long userId);
}
