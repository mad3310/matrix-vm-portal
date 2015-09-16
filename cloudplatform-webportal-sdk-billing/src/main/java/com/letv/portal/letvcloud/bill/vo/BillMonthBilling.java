package com.letv.portal.letvcloud.bill.vo;

import com.letv.portal.model.letvcloud.BillUserBilling;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenliusong on 2015/6/28.
 */
public class BillMonthBilling implements Serializable {

    private static final long serialVersionUID = -7901552728571899578L;
    //总费用
    private double totalMoney;
    //已结算费用
    private double clearMoney;
    //欠费金额
    private double oweMoney;
    //账单月份
    private String billingMonth;
    //账单详细
    private List<BillUserBilling> billUserBillingList;

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getClearMoney() {
        return clearMoney;
    }

    public void setClearMoney(double clearMoney) {
        this.clearMoney = clearMoney;
    }

    public double getOweMoney() {
        return oweMoney;
    }

    public void setOweMoney(double oweMoney) {
        this.oweMoney = oweMoney;
    }

    public String getBillingMonth() {
        return billingMonth;
    }

    public void setBillingMonth(String billingMonth) {
        this.billingMonth = billingMonth;
    }

    public List<BillUserBilling> getBillUserBillingList() {
        return billUserBillingList;
    }

    public void setBillUserBillingList(List<BillUserBilling> billUserBillingList) {
        this.billUserBillingList = billUserBillingList;
    }
}
