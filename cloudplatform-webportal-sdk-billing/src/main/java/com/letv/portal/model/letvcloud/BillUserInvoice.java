package com.letv.portal.model.letvcloud;

import java.io.Serializable;

/**
 * Created by chenliusong on 2015/7/1.
 */
public class BillUserInvoice implements Serializable {

    private static final long serialVersionUID = -5640038142980755781L;

    //账单编号
    private String billingId;
    //订单编号
    private Long orderId;
    //邮箱
    private String email;
    //联系人
    private String contacts;
    //账单日期
    private String billingMonth;
    //账单金额
    private String billingMoney;
    //账单状态
    private String state;
    //业务编码
    private String billingContents;

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getBillingMonth() {
        return billingMonth;
    }

    public void setBillingMonth(String billingMonth) {
        this.billingMonth = billingMonth;
    }

    public String getBillingMoney() {
        return billingMoney;
    }

    public void setBillingMoney(String billingMoney) {
        this.billingMoney = billingMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBillingContents() {
        return billingContents;
    }

    public void setBillingContents(String billingContents) {
        this.billingContents = billingContents;
    }
}
