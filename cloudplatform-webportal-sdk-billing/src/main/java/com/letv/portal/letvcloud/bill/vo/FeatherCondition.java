package com.letv.portal.letvcloud.bill.vo;

import com.letv.portal.model.letvcloud.BillFeather;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wanglei14 on 2015/6/18.
 */
public class FeatherCondition extends BillFeather implements Serializable {

    private long userId;

    private String serviceCode;

    public long getUserId() {
        return userId;
    }


    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

}
