package com.letv.portal.service.openstack.util;

import com.letv.portal.service.openstack.exception.OpenStackException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public class HttpUtil {
    public static String httpEntityToString(HttpEntity httpEntity)
            throws IllegalStateException, IOException {
        String contentEncoding = "UTF-8";
        Header contentEncodingHeader = httpEntity.getContentEncoding();

        if (contentEncodingHeader != null) {
            String contentEncodingHeaderValue = contentEncodingHeader
                    .getValue();
            if (contentEncodingHeaderValue != null) {
                contentEncoding = contentEncodingHeaderValue;
            }
        }

        InputStream rcis = httpEntity.getContent();
        String str = "";
        try {
            str = IOUtils.toString(rcis, contentEncoding);
        } finally {
            rcis.close();
        }

        return str;
    }

    public static void throwExceptionOfResponse(HttpResponse resp)
            throws OpenStackException {
        StringBuilder exceptionMessageBuilder = new StringBuilder();
        exceptionMessageBuilder.append("Error OpenStack http response<br>");

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> responseMap = new HashMap<String, Object>();

            StatusLine statusLine = resp.getStatusLine();
            if (statusLine != null) {
                Map<String, Object> responseStatusLineMap = new HashMap<String, Object>();
                responseStatusLineMap.put("protocol_version", statusLine
                        .getProtocolVersion().toString());
                responseStatusLineMap.put("reason_phrase",
                        statusLine.getReasonPhrase());
                responseStatusLineMap.put("status_code",
                        statusLine.getStatusCode());
                responseMap.put("status_line", responseStatusLineMap);
            }

            Map<String, Object> responseHeaderMap = new HashMap<String, Object>();
            for (Header header : resp.getAllHeaders()) {
                responseHeaderMap.put(header.getName(), header.getValue());
            }
            responseMap.put("header", responseHeaderMap);

            if (resp.getEntity() != null) {
                responseMap.put("entity", httpEntityToString(resp.getEntity()));
            }

            String responseMapJson = objectMapper
                    .writeValueAsString(responseMap);
            exceptionMessageBuilder.append(responseMapJson);
        } catch (Exception ex) {
            exceptionMessageBuilder.append(ex.getMessage());
            exceptionMessageBuilder.append("<br>");
            exceptionMessageBuilder.append(ExceptionUtils.getStackTrace(ex)
                    .replaceAll("\\\n", "<br>"));
        }

        throw new OpenStackException(exceptionMessageBuilder.toString(),
                "后台服务异常");
    }
}
