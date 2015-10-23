package com.letv.portal.service.openstack.erroremail.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/10/23.
 */
public class ErrorMailMessageModel {
    private String requestUrl;
    private String exceptionId;
    private String exceptionParams;
    private String exceptionMessage;
    private String exceptionContent;

    public ErrorMailMessageModel() {
    }

    public ErrorMailMessageModel requestUrl(final String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    public ErrorMailMessageModel exceptionId(final String exceptionId) {
        this.exceptionId = exceptionId;
        return this;
    }

    public ErrorMailMessageModel exceptionParams(final String exceptionParams) {
        this.exceptionParams = exceptionParams;
        return this;
    }

    public ErrorMailMessageModel exceptionMessage(final String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public ErrorMailMessageModel exceptionContent(final String exceptionContent) {
        this.exceptionContent = exceptionContent;
        return this;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestUrl", requestUrl);
        map.put("exceptionId", exceptionId);
        map.put("exceptionParams", exceptionParams);
        map.put("exceptionMessage", exceptionMessage);
        map.put("exceptionContent", exceptionContent);
        return map;
    }
}
