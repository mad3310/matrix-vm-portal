package com.letv.portal.model.letvcloud;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 */
public class BillServiceOrder implements Serializable {
    //订单ID
    private Long orderId;
    //业务编码
    private String serviceCode;
    //用户ID
    private Long userId;
    //订单状态
    private int state;
    //折扣
    private String discount="1";
    //损耗值默认0.1
    private String lossValue="0.1";
    //P2P
    private String p2pValue="0";
    //创建时间
    private Date createdTime;
    //最后更新时间
    private Date lastUpdateTime;

    public Long getOrderId() {
        return orderId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public Long getUserId() {
        return userId;
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setLossValue(String lossValue) {
        this.lossValue = lossValue;
    }

    public void setP2pValue(String p2pValue) {
        this.p2pValue = p2pValue;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getState() {
        return state;
    }

}