package com.letv.portal.letvcloud.bill.vo;

import java.io.Serializable;

/**
 * Created by chenliusong on 2015/6/28.
 */
public class BillMonthDetailBilling implements Serializable{
    private static final long serialVersionUID = 7162911229292331318L;

    //功能点编号
    private String featherCode;
    //使用总量
    private String totalAmount;
    //单价
    private String price;
    //折扣
    private String discount;
    //总计
    private String totalFee;

    public String getFeatherCode() {
        return featherCode;
    }

    public void setFeatherCode(String featherCode) {
        this.featherCode = featherCode;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
}
