package com.letv.portal.model.letvcloud;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wanglei14 on 2015/6/18.
 */
public class BillServiceOrderDetail implements Serializable{
    //订单详细ID
    private Long orderDetailId;
    //userID
    private Long userId;
    //订单ID
    private Long orderId;
    //功能点编号
    private String featherCode;
    //上月收费规则
    private String feeCodePre;
    //当前收费规则
    private String feeCodeNow;
    //下月收费规则
    private String feeCodeNext;
    //功能点类型  01:CDN服务 02:基础服务 03:增值服务
    private String featherType;
    //折扣
    private String discount="1";
    //创建时间
    private Date createdTime;
    //创建用户
    private String createdUser;
    //最后更新时间
    private Date lastUpdateTime;
    //最后更新用户
    private String lastUpdateUser;
    //订单详细状态 0:停用，1:启用
    private int state;

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


    public String getFeatherCode() {
        return featherCode;
    }

    public void setFeatherCode(String featherCode) {
        this.featherCode = featherCode == null ? null : featherCode.trim();
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount == null ? null : discount.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser == null ? null : lastUpdateUser.trim();
    }
    public int getState() {

        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    public void setFeeCodePre(String feeCodePre) {
        this.feeCodePre = feeCodePre;
    }

    public void setFeeCodeNow(String feeCodeNow) {
        this.feeCodeNow = feeCodeNow;
    }

    public void setFeeCodeNext(String feeCodeNext) {
        this.feeCodeNext = feeCodeNext;
    }

    public String getFeeCodePre() {

        return feeCodePre;
    }

    public String getFeeCodeNow() {
        return feeCodeNow;
    }

    public String getFeeCodeNext() {
        return feeCodeNext;
    }

    public String getFeatherType() {
        return featherType;
    }

    public void setFeatherType(String featherType) {
        this.featherType = featherType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
