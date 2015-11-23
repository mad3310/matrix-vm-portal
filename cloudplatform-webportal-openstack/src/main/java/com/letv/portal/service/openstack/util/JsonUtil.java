package com.letv.portal.service.openstack.util;

import com.letv.portal.service.openstack.exception.OpenStackException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public class JsonUtil {
    public static <T> T fromJson(String json,
                                 @SuppressWarnings("rawtypes") TypeReference typeReference, boolean compatibleWithUnknownProperties)
            throws OpenStackException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (compatibleWithUnknownProperties) {
                objectMapper.getDeserializationConfig().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            }
            return objectMapper.readValue(json, typeReference);
        } catch (JsonParseException e) {
            throw new OpenStackException("请求数据格式错误", e);
        } catch (JsonMappingException e) {
            throw new OpenStackException("请求数据格式错误", e);
        } catch (IOException e) {
            throw new OpenStackException("后台服务错误", e);
        }
    }

    public static <T> T fromJson(String json,
                                 @SuppressWarnings("rawtypes") TypeReference typeReference) throws OpenStackException {
        return fromJson(json, typeReference, false);
    }
}
