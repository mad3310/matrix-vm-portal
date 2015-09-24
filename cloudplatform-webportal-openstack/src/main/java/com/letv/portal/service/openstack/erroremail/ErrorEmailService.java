package com.letv.portal.service.openstack.erroremail;

import java.util.Map;

public interface ErrorEmailService {
    void sendErrorEmail(Map<String, Object> mailMessageModel);

    void sendExceptionEmail(Exception exception, String function, long userId, String contextMessage);
}
