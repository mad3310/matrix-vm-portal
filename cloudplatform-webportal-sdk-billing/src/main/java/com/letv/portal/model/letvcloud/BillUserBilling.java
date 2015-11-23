package com.letv.portal.model.letvcloud;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户账单表
 * Created by chenliusong on 2015/6/29.
 */
public class BillUserBilling implements Serializable{

    private static final long serialVersionUID = 5057925179412332374L;
    //账单ID
    private String billingId;
    //订单ID
    private Long orderId;
    //业务编码
    private String serviceCode;
    //账单日期
    private String billingMonth;
    //账单金额
    private String billingMoney;
    //用户ID
    private Long userId;
    //创建时间
    private Date createdTime;
    //产品名称-用户页面展示
    private String productName;

    public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
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

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

