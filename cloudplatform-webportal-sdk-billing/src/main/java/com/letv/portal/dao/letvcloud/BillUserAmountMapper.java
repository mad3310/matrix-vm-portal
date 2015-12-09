package com.letv.portal.dao.letvcloud;

import java.util.Date;
import java.util.Map;

import com.letv.portal.model.letvcloud.BillUserAmount;

/**
 * Created by wanglei14 on 2015/6/24.
 */
public interface BillUserAmountMapper {
    //取得用户余额信息
    BillUserAmount getUserAmout(long userId);
    //创建默认账户
    int insertUserAmountDefault(long userId);
    //扣费
    int reduceAmount(BillUserAmount userAmount);
    //充值
    int addAmount(BillUserAmount userAmount);
    //设置欠费时间为空
    int updateArrearageTime(Long userId);
    //判断用户是否欠费
    BillUserAmount getUserArrears(long userId);

    Date getLastUpdateTime(long userId);
    //账户余额转为冻结余额
    int updateUserAmountFromAvailableToFreeze(BillUserAmount userAmount);
    //处理冻结金额
    int dealFreezeAmount(Map<String, Object> params);
    //扣除冻结余额
    int reduceFreezeAmount(BillUserAmount userAmount);
    //扣除可用余额
    int reduceAvailableAmount(BillUserAmount userAmount);
}
