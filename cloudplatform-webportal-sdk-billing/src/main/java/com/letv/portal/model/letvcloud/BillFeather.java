package com.letv.portal.model.letvcloud;

import java.io.Serializable;

/**
 * Created by wanglei14 on 2015/6/18.
 */
public class BillFeather implements Serializable {
    //功能点编号
    protected String featherCode;
    //上月收费规则
    protected String feeCodePre;
    //当前计费规则
    protected String feeCodeNow;
    //下月计费规则
    protected String feeCodeNext;
    //启用状态
    private int state;
    //折扣
    private String discount;
    //功能点类型  01:CDN服务 02:基础服务 03:增值服务
    private String featherType;

    public String getFeatherCode() {
        return featherCode;
    }


    public int getState() {
        return state;
    }

    public String getDiscount() {
        return discount;
    }

    public void setFeatherCode(String featherCode) {
        this.featherCode = featherCode;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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

    public void setFeeCodePre(String feeCodePre) {
        this.feeCodePre = feeCodePre;
    }

    public void setFeeCodeNow(String feeCodeNow) {
        this.feeCodeNow = feeCodeNow;
    }

    public void setFeeCodeNext(String feeCodeNext) {
        this.feeCodeNext = feeCodeNext;
    }

    public String getFeatherType() {
        return featherType;
    }

    public void setFeatherType(String featherType) {
        this.featherType = featherType;
    }
}
