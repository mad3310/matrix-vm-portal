package com.letv.lcp.openstack.service.erroremail;

import java.util.Map;

import com.letv.lcp.openstack.model.erroremail.ErrorMailMessageModel;

public interface IErrorEmailService {
    void sendErrorEmail(Map<String, Object> mailMessageModel);

    void sendErrorEmail(ErrorMailMessageModel model);

    void sendExceptionEmail(Exception exception, String function, Long userId, String contextMessage);
}
