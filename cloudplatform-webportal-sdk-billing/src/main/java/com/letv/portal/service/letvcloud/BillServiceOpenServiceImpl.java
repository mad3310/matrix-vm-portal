package com.letv.portal.service.letvcloud;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letv.portal.dao.letvcloud.BillServiceOrderDetailMapper;
import com.letv.portal.dao.letvcloud.BillServiceOrderMapper;
import com.letv.portal.dao.letvcloud.BillUserAmountMapper;
import com.letv.portal.letvcloud.bill.BillFeatherType;
import com.letv.portal.letvcloud.bill.vo.BillServiceOpen;
import com.letv.portal.letvcloud.bill.vo.FeatherCondition;
import com.letv.portal.model.letvcloud.BillFeather;
import com.letv.portal.model.letvcloud.BillServiceOrder;
import com.letv.portal.model.letvcloud.BillServiceOrderDetail;
import com.letv.portal.model.letvcloud.BillUserAmount;

/**
 * 业务开通
 * Created by wanglei14 on 2015/6/18.
 */

@Service
public class BillServiceOpenServiceImpl implements BillServiceOpenService {
    @Resource
    BillServiceOrderMapper billServiceOrderDao;
    @Resource
    BillServiceOrderDetailMapper billServiceOrderDetailDao;
    @Resource
    BillUserAmountMapper billUserAmountMapper;

    /**
     * 开通业务
     */
    @Override
    @Transactional
    public void openService(BillServiceOpen serviceOpen) {
        //插入订单
        BillServiceOrder order = new BillServiceOrder();
        order.setUserId(serviceOpen.getUserId());
        order.setServiceCode(serviceOpen.getServiceCode());
        billServiceOrderDao.insert(order);
        //插入订单详细
        BillServiceOrderDetail detail = new BillServiceOrderDetail();
        detail.setOrderId(order.getOrderId());
        detail.setUserId(order.getUserId());
        if (serviceOpen.getFeatures() == null) {
            return;
        }
        for (BillFeather feather : serviceOpen.getFeatures()) {
            detail.setFeatherCode(feather.getFeatherCode());
            //收费规则初始化为当月收费规则
            detail.setFeeCodeNow(feather.getFeeCodeNow());
            detail.setFeeCodeNext(feather.getFeeCodeNow());
            detail.setFeeCodePre(feather.getFeeCodeNow());
            detail.setFeatherType(feather.getFeatherType());
            billServiceOrderDetailDao.insert(detail);

        }

    }

    /**
     * 更改业务状态
     *
     * @param userId
     * @param serviceCode
     * @param state
     */
    @Override
    public void changeServiceState(long userId, String serviceCode, int state) {
        BillServiceOrder order = new BillServiceOrder();
        order.setUserId(userId);
        order.setServiceCode(serviceCode);
        order.setState(state);
        billServiceOrderDao.update(order);

    }

    /**
     * 功能点修改
     *
     * @param featherCondition
     */
    @Override
    public void changeFeather(FeatherCondition featherCondition) {
        Long orderId = billServiceOrderDao.getOrderId(featherCondition);
        BillServiceOrderDetail detail = new BillServiceOrderDetail();
        detail.setOrderId(orderId);
        detail.setFeatherCode(featherCondition.getFeatherCode());
        detail.setFeeCodeNext(featherCondition.getFeeCodeNext());
        detail.setDiscount(featherCondition.getDiscount());
        detail.setState(featherCondition.getState());
        billServiceOrderDetailDao.update(detail);
    }

    /**
     * 取得用户开通的业务及功能
     *
     * @param userId
     * @param serviceCode
     * @return
     */
    @Override
    public BillServiceOpen getServiceByCode(long userId, String serviceCode) {
        BillServiceOpen serviceOpen = new BillServiceOpen();
        BillServiceOrder order = new BillServiceOrder();
        //查询条件
        order.setUserId(userId);
        order.setServiceCode(serviceCode);
        //取出订单
        BillServiceOrder result = billServiceOrderDao.getServiceOrder(order);
        if (result == null) {
            return null;
        }
        //获取用户资金账户
        BillUserAmount billUserAmount = billUserAmountMapper.getUserAmout(userId);
        if (billUserAmount.getAvailableAmount().compareTo(new BigDecimal(0)) < 0) {
            serviceOpen.setAmountState(0);  //欠费
        } else {
            serviceOpen.setAmountState(1);
        }

        serviceOpen.setOrderId(result.getOrderId());
        serviceOpen.setUserId(result.getUserId());
        serviceOpen.setDiscount(result.getDiscount());
        serviceOpen.setServiceCode(result.getServiceCode());
        serviceOpen.setLossValue(result.getLossValue());
        serviceOpen.setP2pValue(result.getP2pValue());
        serviceOpen.setState(result.getState());
        //取出开通的功能点
        List<BillFeather> feathers = billServiceOrderDetailDao.getDetailsByOrderId(result.getOrderId());
        //cdn服务
        List<BillFeather> cdnFeathers = new ArrayList<BillFeather>();
        //基础服务
        List<BillFeather> basicFeathers = new ArrayList<BillFeather>();
        //增值服务
        List<BillFeather> valueFeathers = new ArrayList<BillFeather>();

        //设置当前及下个月计费规则
        for (BillFeather billFeather : feathers) {
            if (BillFeatherType.CND_FEATHER.equals(billFeather.getFeatherType())) {
                cdnFeathers.add(billFeather);
                serviceOpen.setFeeCodeNow(billFeather.getFeeCodeNow());
                serviceOpen.setFeeCodeNext(billFeather.getFeeCodeNext());
                serviceOpen.setFeatherCode(billFeather.getFeatherCode());
            }
            if(BillFeatherType.BASIC_FEATHER.equals(billFeather.getFeatherType())){
                basicFeathers.add(billFeather);
            }
            if(BillFeatherType.VALUEADD_FEATHER.equals(billFeather.getFeatherType())){
                valueFeathers.add(billFeather);
            }
        }
        //分类设置功能点
        Map<String,List<BillFeather>> featuresMap = new HashMap<String, List<BillFeather>>();
        featuresMap.put(BillFeatherType.CND_FEATHER,cdnFeathers);
        featuresMap.put(BillFeatherType.BASIC_FEATHER,basicFeathers);
        featuresMap.put(BillFeatherType.VALUEADD_FEATHER,valueFeathers);
        serviceOpen.setFeaturesMap(featuresMap);
        serviceOpen.setFeatures(feathers);
        return serviceOpen;
    }


    /**
     * 取得开通某项业务的所有用户
     *
     * @param serviceCode
     * @return
     */
    @Override
    public List<Long> getUsersByServiceCode(String serviceCode) {
        return billServiceOrderDao.getUserByServiceCode(serviceCode);
    }

    @Override
    public boolean isServiceOpened(long userId, String serviceCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("serviceCode", serviceCode);
        Long ret = billServiceOrderDao.isOrderExist(params);
        if (ret == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Map<String, Object> getServiceState(long userId, String serviceCode) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("serviceCode", serviceCode);
        Map<String, Object> serviceMap = billServiceOrderDao.getState(params);
        if (serviceMap == null || serviceMap.size() <= 0) {
            result.put("openState", "0");
        } else {
            Map<String, Object> featherMap = billServiceOrderDetailDao.getMainFeather((Long) serviceMap.get("orderId"), BillFeatherType.CND_FEATHER);
            result.putAll(featherMap);
            result.put("openState", "1");
            result.put("state", serviceMap.get("state"));
            result.put("discount", serviceMap.get("discount"));
            result.put("lossValue", serviceMap.get("lossValue"));
            result.put("testStartTime", serviceMap.get("testStartTime"));
            result.put("testEndTime", serviceMap.get("testEndTime"));
            result.put("orderStage", serviceMap.get("orderStage"));
            result.put("serviceCode", serviceCode);
        }
        return result;
    }

    @Override
    public List<String> getUserService(long userId) {
        return billServiceOrderDao.getUserService(userId);
    }

    @Override
    public BillFeather getUserFeather(long userId, String serviceCode, String featherCode) {
        FeatherCondition condition = new FeatherCondition();
        condition.setUserId(userId);
        condition.setServiceCode(serviceCode);
        long orderId = billServiceOrderDao.getOrderId(condition);
        BillFeather feather = billServiceOrderDetailDao.getFeather(orderId, featherCode);
        return feather;
    }

    @Override
    public List<BillServiceOpen> getServiceByUserId(long userId) {
        List <String> codes = this.getUserService(userId);
        List<BillServiceOpen> services = new LinkedList<BillServiceOpen>();
        for(String code:codes){
            BillServiceOpen serviceOpen = this.getServiceByCode(userId,code);
            services.add(serviceOpen);
        }
        return services;
    }
}
