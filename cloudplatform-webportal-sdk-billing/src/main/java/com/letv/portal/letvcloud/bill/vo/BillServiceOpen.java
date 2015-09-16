package com.letv.portal.letvcloud.bill.vo;

import com.letv.portal.model.letvcloud.BillFeather;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglei14 on 2015/6/17.
 */
public class BillServiceOpen implements Serializable {
    //订单ID
    private long orderId;
    //业务编号
    private String serviceCode;
    //用户ID
    private long userId;
    //订单状态
    private int state;
    //账户状态
    private int amountState;
    //功能点 开通服务使用
    private List<BillFeather> features;
    //功能点 按类型分类
    private Map<String,List<BillFeather>> featuresMap;
    //折扣
    private String discount;
    //损耗
    private String lossValue;
    //p2p
    private String p2pValue;
    //CDN业务当前计费规则
    private String feeCodeNow;
    //CDN业务下月计费规则
    private String feeCodeNext;

    //CDN业务功能点编码
    private String featherCode;

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setLossValue(String lossValue) {
        this.lossValue = lossValue;
    }

    public void setP2pValue(String p2pValue) {
        this.p2pValue = p2pValue;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setState(int status) {
        this.state = state;
    }

    public void setFeatures(List<BillFeather> features) {
        this.features = features;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public long getUserId() {
        return userId;
    }

    public int getState() {
        return state;
    }

    public List<BillFeather> getFeatures() {
        return features;
    }

    public String getDiscount() {
        return discount;
    }

    public String getLossValue() {
        return lossValue;
    }

    public String getP2pValue() {
        return p2pValue;
    }

    public int getAmountState() {
        return amountState;
    }

    public void setAmountState(int amountState) {
        this.amountState = amountState;
    }

    public String getFeeCodeNow() {
        return feeCodeNow;
    }

    public void setFeeCodeNow(String feeCodeNow) {
        this.feeCodeNow = feeCodeNow;
    }

    public String getFeeCodeNext() {
        return feeCodeNext;
    }

    public void setFeeCodeNext(String feeCodeNext) {
        this.feeCodeNext = feeCodeNext;
    }

    public String getFeatherCode() {
        return featherCode;
    }

    public void setFeatherCode(String featherCode) {
        this.featherCode = featherCode;
    }

    public Map<String, List<BillFeather>> getFeaturesMap() {
        return featuresMap;
    }

    public void setFeaturesMap(Map<String, List<BillFeather>> featuresMap) {
        this.featuresMap = featuresMap;
    }
}
