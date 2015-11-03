package com.letv.portal.service.openstack.exception;

import com.letv.common.exception.MatrixException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/11/3.
 */
public class OpenStackCompositeException extends OpenStackException {
    private static final long serialVersionUID = -3100576035408205814L;

    private Iterable<Exception> exceptions;

    public OpenStackCompositeException(Iterable<Exception> exceptions) {
        super(getExceptionMessage(exceptions), "后台错误");
        this.exceptions = exceptions;
    }

    public MatrixException matrixException() {
        return new MatrixException(getMatrixExceptionUserMessage(exceptions), this);
    }

    public void throwMatrixExceptionIfNecessary() throws MatrixException {
        if (isNecessaryThrowMatrixException()) {
            throw matrixException();
        }
    }

    public boolean isNecessaryThrowMatrixException() {
        for (Iterator<Exception> it = exceptions.iterator(); it.hasNext(); ) {
            Exception exception = it.next();
            if (!(exception instanceof UserOperationException)) {
                return true;
            }
        }
        return false;
    }

    public List<String> toMsgs() {
        List<String> msgs = new LinkedList<String>();
        for (Iterator<Exception> it = exceptions.iterator(); it.hasNext(); ) {
            Exception exception = it.next();
            if (exception instanceof UserOperationException) {
                msgs.add(((UserOperationException) exception).getUserMessage());
            } else {
                msgs.add("后台错误");
            }
        }
        return msgs;
    }

    private static String getMatrixExceptionUserMessage(Iterable<Exception> exceptions) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<Exception> it = exceptions.iterator(); it.hasNext(); ) {
            Exception exception = it.next();
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            if (exception instanceof UserOperationException) {
                stringBuilder.append(((UserOperationException) exception).getUserMessage());
            } else {
                stringBuilder.append("后台错误");
            }
        }
        return stringBuilder.toString();
    }

    private static String getExceptionMessage(Iterable<Exception> exceptions) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<Exception> it = exceptions.iterator(); it.hasNext(); ) {
            Exception exception = it.next();
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(exception.getMessage());
        }
        return stringBuilder.toString();
    }
}
