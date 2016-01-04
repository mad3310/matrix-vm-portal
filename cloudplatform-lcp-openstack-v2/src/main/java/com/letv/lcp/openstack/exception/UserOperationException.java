package com.letv.lcp.openstack.exception;

/**
 * Created by zhouxianguang on 2015/8/17.
 */
public class UserOperationException extends OpenStackException{
    private static final long serialVersionUID = -5590266331407213595L;

    public UserOperationException(String msg, String userMessage) {
        super(msg, userMessage);
    }

    public UserOperationException(String userMessage, Throwable t) {
        super(userMessage, t);
    }

    public UserOperationException(String msg, String userMessage, Throwable t) {
        super(msg, userMessage, t);
    }
}
