package com.letv.portal.service.openstack.exception;

import com.letv.portal.service.openstack.util.Timeout;

import java.text.MessageFormat;

/**
 * Created by zhouxianguang on 2015/11/6.
 */
public class TimeoutException extends OpenStackException {
    private static final long serialVersionUID = 5898298993101818153L;

    public TimeoutException(Timeout timeout, long timeInterval) {
        super(MessageFormat.format("timeout.time={0}, timeout.unit={1}, timeInterval={2}", timeout.time(), timeout.unit(), timeInterval), "后台错误");
    }
}
