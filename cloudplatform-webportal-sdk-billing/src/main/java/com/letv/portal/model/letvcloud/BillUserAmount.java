package com.letv.portal.model.letvcloud;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wanglei14 on 2015/6/24.
 */
public class BillUserAmount implements Serializable{
    
	private static final long serialVersionUID = 6400632656440960897L;
	//用户ID
    private long userId;
    //用户可用余额
    private BigDecimal availableAmount;
    //冻结资金
    private BigDecimal freezeAmount;
    //最后更新时间
    private Date lastUpdateTime;
    //开始欠费时间
    private Date arrearageTime;
    //要充值或者扣除的费用
    public BigDecimal fee;
    
    public long getUserId() {
        return userId;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public BigDecimal getFreezeAmount() {
        return freezeAmount;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public void setFreezeAmount(BigDecimal freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Date getArrearageTime() {
        return arrearageTime;
    }

    public void setArrearageTime(Date arrearageTime) {
        this.arrearageTime = arrearageTime;
    }
}
