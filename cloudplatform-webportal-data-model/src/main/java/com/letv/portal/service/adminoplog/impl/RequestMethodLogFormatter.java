package com.letv.portal.service.adminoplog.impl;

import com.letv.portal.service.adminoplog.MethodInvocation;
import com.letv.portal.service.adminoplog.MethodLogFormatter;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestMethodLogFormatter implements MethodLogFormatter {
    private Integer eventMaxLength;

    public void setEventMaxLength(Integer eventMaxLength) {
        this.eventMaxLength = eventMaxLength;
    }

    public Integer getEventMaxLength() {
        return eventMaxLength;
    }

    @Deprecated
    public String formatToUrl(MethodInvocation invocation) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        StringBuilder builder = new StringBuilder();
        String method = request.getMethod();
        if (!"GET".equals(method)) {
            method = "POST";
        }
        builder.append(method);
        builder.append(" ");
        builder.append(request.getRequestURI());
        builder.append(" ");

        boolean isFirst = true;
        @SuppressWarnings("unchecked")
        Map<String, String[]> paraMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : paraMap.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                builder.append("&");
            }
            builder.append(entry.getKey());
            builder.append("=");
            String[] paraValues = entry.getValue();
            builder.append(paraValues[0]);
            if (paraValues.length > 1) {
                for (int paraValuesIndex = 1; paraValuesIndex < paraValues.length; paraValuesIndex++) {
                    builder.append("&");
                    builder.append(entry.getKey());
                    builder.append("=");
                    builder.append(paraValues[paraValuesIndex]);
                }
            }
        }

        String str = builder.toString();
        if (str.length() > eventMaxLength) {
            str = str.substring(0, eventMaxLength);
        }
        return str;
    }

    @Override
    public String format(MethodInvocation invocation) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();

        String method = request.getMethod();
        if (!"GET".equals(method)) {
            method = "POST";
        }
        jsonMap.put("method", method);

        jsonMap.put("uri", request.getRequestURI());

        jsonMap.put("paras", request.getParameterMap());

        String str = new ObjectMapper().writeValueAsString(jsonMap);
        if (str.length() > eventMaxLength) {
            str = str.substring(0, eventMaxLength);
        }
        return str;
    }
}
