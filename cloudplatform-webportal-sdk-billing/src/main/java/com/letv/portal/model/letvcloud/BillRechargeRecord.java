package com.letv.portal.model.letvcloud;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wanglei14 on 2015/6/29.
 */
public class BillRechargeRecord implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1082098815328114238L;

	private String tradeNum;

    private long userId;

    private BigDecimal amount;

    private int rechargeType;

    private Date createdTime;

    private Date lastUpdateTime;

    private String orderNum;

    private int success;
    
    private String orderCode;//订单编号

    
    public String getOrderCode() {
		return orderCode;
	}

	public String getTradeNum() {
        return tradeNum;
    }

    public long getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getRechargeType() {
        return rechargeType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public int getSuccess() {
        return success;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setRechargeType(int rechargeType) {
        this.rechargeType = rechargeType;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
    
    public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}
