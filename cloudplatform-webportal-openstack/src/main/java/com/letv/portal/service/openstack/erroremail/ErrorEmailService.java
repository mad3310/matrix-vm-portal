package com.letv.portal.service.openstack.erroremail;

import com.letv.portal.service.openstack.erroremail.impl.ErrorMailMessageModel;

import java.util.Map;

public interface ErrorEmailService {
    void sendErrorEmail(Map<String, Object> mailMessageModel);

    void sendErrorEmail(ErrorMailMessageModel model);

    void sendExceptionEmail(Exception exception, String function, Long userId, String contextMessage);
}
