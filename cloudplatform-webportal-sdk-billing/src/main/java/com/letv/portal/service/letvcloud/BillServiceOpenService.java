package com.letv.portal.service.letvcloud;

import com.letv.portal.letvcloud.bill.vo.BillServiceOpen;
import com.letv.portal.letvcloud.bill.vo.FeatherCondition;
import com.letv.portal.model.letvcloud.BillFeather;

import java.util.List;
import java.util.Map;

/**
 * Created by wanglei14 on 2015/6/18.
 */
public interface BillServiceOpenService {
    //开通服务
    public void openService(BillServiceOpen serviceOrder);
    // 更改服务状态
    public void changeServiceState(long userId,String serviceCode,int state);
    //获取产品线状态
    public Map<String, Object> getServiceState(long userId, String serviceCode);
    //更改功能点
    public void changeFeather(FeatherCondition featherCondition);
    //取得用户开通的服务及功能点
    public BillServiceOpen getServiceByCode(long userId,String serviceCode);
    //根据serviceCode取得所有userID
    List<Long> getUsersByServiceCode(String serviceCode);
    //判断用户有没有开通
    public boolean isServiceOpened(long userId,String ServiceCode);
    //查询用户开通的服务
    List<String> getUserService(long userId);

    BillFeather getUserFeather(long userId,String serviceCode,String featherCode);
    List<BillServiceOpen> getServiceByUserId(long userId);
}
