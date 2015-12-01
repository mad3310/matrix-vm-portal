package com.letv.portal.service.oauth;

import java.util.Map;

public interface IUcService {
    Map<String,Object> getUnReadMessage(Long ucId);
    Map<String,Object> getUserByUserId(Long ucId);
    Long getUcIdByOauthId(String oauthId);

}