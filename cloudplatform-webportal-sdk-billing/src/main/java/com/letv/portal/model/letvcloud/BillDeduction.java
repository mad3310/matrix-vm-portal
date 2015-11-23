package com.letv.portal.model.letvcloud;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BillDeduction implements Serializable {

    private String deductionDate;           //扣费的时间，比如2015061323，20150613，201506

    private String featherCode;             //功能点编号

    private Long userId;                  //用户ID

    private String serviceCode;               //y业务编码

    private Long orderId;                   //订单ID

    private BigDecimal fee;                 //扣费金额

    private String discount;                //折扣

    private String lossValue;               //损耗值

    private long useAmount;                 //使用量

    private Date createdTime;                //该任务的创建时间

    private String feeCode;                 //计费规则编号

    private String feeMode;                  //计费方法名称

    private String price;                    //单价

    public String getDeductionDate() {
        return deductionDate;
    }

    public void setDeductionDate(String deductionDate) {
        this.deductionDate = deductionDate;
    }

    public String getFeatherCode() {
        return featherCode;
    }

    public void setFeatherCode(String featherCode) {
        this.featherCode = featherCode == null ? null : featherCode.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId == null ? null : userId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public long getUseAmount() {
        return useAmount;
    }

    public void setUseAmount(long useAmount) {
        this.useAmount = useAmount;
    }

    public String getFeeMode() {
        return feeMode;
    }

    public void setFeeMode(String feeMode) {
        this.feeMode = feeMode;
    }

    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getLossValue() {
        return lossValue;
    }

    public void setLossValue(String lossValue) {
        this.lossValue = lossValue;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}