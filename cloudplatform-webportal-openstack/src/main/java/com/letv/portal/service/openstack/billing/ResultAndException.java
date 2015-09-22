package com.letv.portal.service.openstack.billing;

/**
 * Created by zhouxianguang on 2015/9/22.
 */
public class ResultAndException<T> {
    private T result;
    private Exception exception;

    public ResultAndException() {
    }

    public ResultAndException(T result, Exception exception) {
        this.result = result;
        this.exception = exception;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
