package com.letv.portal.service.oauth;

import java.util.Map;

public interface IUcService {
    Map<String,Object> getUnReadMessage(Long userId);
    Map<String,Object> getUserByUserId(Long userId);
    Long getUcIdByOauthId(String oauthId);

}