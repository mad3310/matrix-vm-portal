package com.letv.portal.service.openstack.util;

import com.letv.common.exception.MatrixException;
import com.letv.common.exception.ValidateException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public class ExceptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    public static void throwException(Exception ex) throws OpenStackException {
        if (ex instanceof ValidateException) {
            throw (ValidateException) ex;
        } else if (ex instanceof MatrixException) {
            throw (MatrixException) ex;
        } else if (ex instanceof OpenStackException) {
            throw (OpenStackException) ex;
        }
        throw new OpenStackException("后台错误", ex);
    }

    public static void throwMatrixException(Exception ex) throws MatrixException {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else if (ex instanceof OpenStackException) {
            throw ((OpenStackException) ex).matrixException();
        } else {
            throw new MatrixException("后台错误", ex);
        }
    }

    public static String getUserMessage(Exception e) {
        if (e instanceof OpenStackException) {
            return ((OpenStackException) e).getUserMessage();
        } else {
            return "后台错误";
        }
    }

    public static void processBillingException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        OpenStackServiceImpl.getOpenStackServiceGroup().getErrorEmailService().sendExceptionEmail(ex, "计费系统", null, "");
    }

    public static void logAndEmail(Exception e){
        logger.error(e.getMessage(), e);
        OpenStackServiceImpl.getOpenStackServiceGroup().getErrorEmailService().sendExceptionEmail(e, "", null, "");
    }
}
