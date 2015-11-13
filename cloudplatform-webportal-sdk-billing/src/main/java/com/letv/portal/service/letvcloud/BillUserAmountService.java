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
    //插入充值记录
    public String recharge(long userId,BigDecimal amount,String tradeNum,int type);
    //充值成功更余额
    public long rechargeSuccess(Long userId,String tradeNum,String orderNum,BigDecimal amount);
    //充值成功更余额
    public long rechargeSuccessByOrderCode(Long userId,String orderCode,String orderNum,BigDecimal amount);
    //充值成功不更余额
    public long rechargeSuccess(Long userId,String tradeNum,String orderNum,BigDecimal amount,boolean isUpdateAmount);
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
    //把用户可用余额部分或全部金额转为冻结金额
    boolean updateUserAmountFromAvailableToFreeze(long userId, BigDecimal price);
    //扣除冻结余额
    boolean reduceFreezeAmount(long userId, BigDecimal price, String productName, String productType);
    //续费扣除可用余额
    boolean reduceAvailableAmount(long userId, BigDecimal price, String productName, String productType, String date);
    //把用户冻结金额转为可用余额
    boolean updateUserAmountFromFreezeToAvailable(long userId, BigDecimal price, String productName, String productType);
    //处理冻结金额
    boolean dealFreezeAmount(long userId, BigDecimal succPrice, BigDecimal failPrice, String productName, String productType);
}
